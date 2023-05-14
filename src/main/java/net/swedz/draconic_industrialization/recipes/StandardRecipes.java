package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.PartKeyProvider;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;

public final class StandardRecipes
{
	public static void apply(RecipeContext context)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		// Ingot <-> Block
		unpacking(context, MIParts.INGOT, "block");
		packing(context, MIParts.BLOCK, "ingot");
		
		// Tiny dust <-> Dust
		unpacking(context, MIParts.TINY_DUST, "dust");
		packing(context, MIParts.DUST, "tiny_dust");
		
		// Plate from ingot
		context.ifExists(MIParts.PLATE, DIDatagenClient.tagMaterialTarget(material, "ingot"), (tag) ->
				context.addMI("draconic_industrialization:materials/compressor/%s_from_%s".formatted(item.id(false), "ingot"), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.COMPRESSOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 1)
								.addItemInput(tag, 1)));
		
		// Curved plate from plate
		context.ifExists(MIParts.CURVED_PLATE, DIDatagenClient.tagMaterialTarget(material, "plate"), (tag) ->
				context.addMI("draconic_industrialization:materials/compressor/%s_from_%s".formatted(item.id(false), "plate"), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.COMPRESSOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 1)
								.addItemInput(tag, 1)));
		
		// Wire from plate
		context.ifExists(MIParts.WIRE, DIDatagenClient.tagMaterialTarget(material, "plate"), (tag) ->
				context.addMI("draconic_industrialization:materials/wiremill/%s_from_%s".formatted(item.id(false), "plate"), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.WIREMILL, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 2)
								.addItemInput(tag, 1)));
		
		// Dust(s) from various items
		maceratorRecycling(context, "ingot", 9);
		maceratorRecycling(context, "plate", 9);
		maceratorRecycling(context, "curved_plate", 9);
		maceratorRecycling(context, "wire", 4);
	}
	
	private static void unpacking(RecipeContext context, PartKeyProvider smallType, String bigType)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(smallType, DIDatagenClient.tagMaterialTarget(material, bigType), (tag) ->
		{
			context.addVanilla("draconic_industrialization:materials/%s_from_%s".formatted(item.id(false), bigType), () ->
					ShapelessRecipeBuilder
							.shapeless(item.item(), 9)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag))
							.requires(tag));
			context.addMI("draconic_industrialization:materials/unpacker/%s_from_%s".formatted(item.id(false), bigType), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.UNPACKER, 2, machineRecipeDuration)
							.addItemOutput(item.item(), 9)
							.addItemInput(tag, 1));
		});
	}
	
	private static void packing(RecipeContext context, PartKeyProvider bigType, String smallType)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(bigType, DIDatagenClient.tagMaterialTarget(material, smallType), (tag) ->
		{
			context.addVanilla("draconic_industrialization:materials/%s_from_%s".formatted(item.id(false), smallType), () ->
					ShapelessRecipeBuilder
							.shapeless(item.item())
							.requires(Ingredient.of(tag), 9)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag)));
			context.addMI("draconic_industrialization:materials/packer/%s_from_%s".formatted(item.id(false), smallType), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.PACKER, 2, machineRecipeDuration)
							.addItemOutput(item.item(), 1)
							.addItemInput(tag, 9)
							.addItemInput(MIItem.PACKER_BLOCK_TEMPLATE, 1, 0));
		});
	}
	
	private static void maceratorRecycling(RecipeContext context, String inputType, int tinyDustOutput)
	{
		final boolean resultsInTinyDust = tinyDustOutput % 9 != 0;
		
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(resultsInTinyDust ? MIParts.TINY_DUST : MIParts.DUST, DIDatagenClient.tagMaterialTarget(material, inputType), (tag) ->
				context.addMI("draconic_industrialization:materials/macerator/recycling/%s_from_%s".formatted(item.id(false), inputType), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.MACERATOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), resultsInTinyDust ? tinyDustOutput : tinyDustOutput / 9)
								.addItemInput(tag, 1)));
	}
}
