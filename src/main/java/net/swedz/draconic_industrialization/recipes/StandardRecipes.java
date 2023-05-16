package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.MIFluids;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.material.DIMaterialPart;

public final class StandardRecipes
{
	public static void apply(RecipeContext context)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		// Ingot <-> Block
		unpacking(context, DIMaterialPart.INGOT, DIMaterialPart.BLOCK);
		packing(context, DIMaterialPart.BLOCK, DIMaterialPart.INGOT);
		
		// Tiny dust <-> Dust
		unpacking(context, DIMaterialPart.TINY_DUST, DIMaterialPart.DUST);
		packing(context, DIMaterialPart.DUST, DIMaterialPart.TINY_DUST);
		
		// Crushed dust from ore
		context.ifExists(DIMaterialPart.CRUSHED_DUST, DIMaterialPart.ORE, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/macerator/%s_from_%s", DIMaterialPart.ORE), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.MACERATOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 3)
								.addItemInput(tag, 1)));
		
		// Dust from crushed dust
		context.ifExists(DIMaterialPart.DUST, DIMaterialPart.CRUSHED_DUST, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/macerator/%s_from_%s", DIMaterialPart.CRUSHED_DUST), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.MACERATOR, 2, (int) (100 * materialPart.material().hardness()))
								.addItemOutput(item.item(), 1)
								.addItemOutput(item.item(), 1, 0.5)
								.addItemInput(tag, 1)));
		
		// Plate from ingot
		context.ifExists(DIMaterialPart.PLATE, DIMaterialPart.INGOT, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/compressor/%s_from_%s", DIMaterialPart.INGOT), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.COMPRESSOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 1)
								.addItemInput(tag, 1)));
		
		// Curved plate from plate
		context.ifExists(DIMaterialPart.CURVED_PLATE, DIMaterialPart.PLATE, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/compressor/%s_from_%s", DIMaterialPart.PLATE), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.COMPRESSOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 1)
								.addItemInput(tag, 1)));
		
		// Wire from plate
		context.ifExists(DIMaterialPart.WIRE, DIMaterialPart.PLATE, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/wiremill/%s_from_%s", DIMaterialPart.PLATE), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.WIREMILL, 2, machineRecipeDuration)
								.addItemOutput(item.item(), 2)
								.addItemInput(tag, 1)));
		
		// Dust(s) from various items
		maceratorRecycling(context, DIMaterialPart.INGOT, 9);
		maceratorRecycling(context, DIMaterialPart.PLATE, 9);
		maceratorRecycling(context, DIMaterialPart.CURVED_PLATE, 9);
		maceratorRecycling(context, DIMaterialPart.WIRE, 4);
		
		// Ingot from hot ingot
		context.ifExists(DIMaterialPart.INGOT, DIMaterialPart.HOT_INGOT, (tag) ->
		{
			context.addMI(context.recipeId("draconic_industrialization:materials/heat_exchanger/%s_from_%s", DIMaterialPart.HOT_INGOT), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.HEAT_EXCHANGER, 8, 10)
							.addItemOutput(item.item(), 1)
							.addFluidOutput(MIFluids.ARGON, 65)
							.addFluidOutput(MIFluids.HELIUM, 25)
							.addItemInput(tag, 1)
							.addFluidInput(MIFluids.CRYOFLUID, 100));
			context.addMI(context.recipeId("draconic_industrialization:materials/vacuum_freezer/%s_from_%s", DIMaterialPart.HOT_INGOT), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.VACUUM_FREEZER, 32, 250)
							.addItemOutput(item.item(), 1)
							.addItemInput(tag, 1));
		});
		
		// Cable from wire
		context.ifExists(DIMaterialPart.CABLE, DIMaterialPart.WIRE, (tag) ->
		{
			context.addVanilla(context.recipeId("draconic_industrialization:materials/%s"), () ->
					ShapedRecipeBuilder
							.shaped(item.item(), 3)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag))
							.pattern("RRR")
							.pattern("WWW")
							.pattern("RRR")
							.define('R', MIItem.RUBBER_SHEET)
							.define('W', tag));
			context.addMI(context.recipeId("draconic_industrialization:materials/packer/%s"), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.PACKER, 2, 5 * 20)
							.addItemOutput(item.item(), 3)
							.addItemInput(MIItem.RUBBER_SHEET, 6)
							.addItemInput(tag, 3));
			context.addMI(context.recipeId("draconic_industrialization:materials/assembler/%s_styrene_rubber"), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.ASSEMBLER, 2, 5 * 20)
							.addItemOutput(item.item(), 3)
							.addItemInput(tag, 3)
							.addFluidInput(MIFluids.STYRENE_BUTADIENE_RUBBER, 6));
			context.addMI(context.recipeId("draconic_industrialization:materials/assembler/%s_synthetic_rubber"), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.ASSEMBLER, 2, 5 * 20)
							.addItemOutput(item.item(), 3)
							.addItemInput(tag, 3)
							.addFluidInput(MIFluids.SYNTHETIC_RUBBER, 30));
		});
		
		// Coil from cable
		context.ifExists(DIMaterialPart.COIL, DIMaterialPart.CABLE, (tag) ->
		{
			context.addVanilla(context.recipeId("draconic_industrialization:materials/%s"), () ->
					ShapedRecipeBuilder
							.shaped(item.item(), 1)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag))
							.pattern("WWW")
							.pattern("W W")
							.pattern("WWW")
							.define('W', tag));
			context.addMI(context.recipeId("draconic_industrialization:materials/assembler/%s"), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.ASSEMBLER, 8, 10 * 20)
							.addItemOutput(item.item(), 1)
							.addItemInput(tag, 8));
		});
	}
	
	private static void unpacking(RecipeContext context, DIMaterialPart smallType, DIMaterialPart bigType)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(smallType, bigType, (tag) ->
		{
			context.addVanilla(context.recipeId("draconic_industrialization:materials/%s_from_%s", bigType), () ->
					ShapelessRecipeBuilder
							.shapeless(item.item(), 9)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag))
							.requires(tag));
			context.addMI(context.recipeId("draconic_industrialization:materials/unpacker/%s_from_%s", bigType), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.UNPACKER, 2, machineRecipeDuration)
							.addItemOutput(item.item(), 9)
							.addItemInput(tag, 1));
		});
	}
	
	private static void packing(RecipeContext context, DIMaterialPart bigType, DIMaterialPart smallType)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(bigType, smallType, (tag) ->
		{
			context.addVanilla(context.recipeId("draconic_industrialization:materials/%s_from_%s", smallType), () ->
					ShapelessRecipeBuilder
							.shapeless(item.item())
							.requires(Ingredient.of(tag), 9)
							.unlockedBy("has_%s_tag".formatted(tag.location().getPath()), RecipeProvider.has(tag)));
			context.addMI(context.recipeId("draconic_industrialization:materials/packer/%s_from_%s", smallType), () ->
					MIRecipeJson.create(MIMachineRecipeTypes.PACKER, 2, machineRecipeDuration)
							.addItemOutput(item.item(), 1)
							.addItemInput(tag, 9)
							.addItemInput(MIItem.PACKER_BLOCK_TEMPLATE, 1, 0));
		});
	}
	
	private static void maceratorRecycling(RecipeContext context, DIMaterialPart inputType, int tinyDustOutput)
	{
		final boolean resultsInTinyDust = tinyDustOutput % 9 != 0;
		
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart materialPart = item.settings().materialPart();
		final String material = materialPart.name();
		final int machineRecipeDuration = materialPart.material().recipeDuration();
		
		context.ifExists(resultsInTinyDust ? DIMaterialPart.TINY_DUST : DIMaterialPart.DUST, inputType, (tag) ->
				context.addMI(context.recipeId("draconic_industrialization:materials/macerator/recycling/%s_from_%s", inputType), () ->
						MIRecipeJson.create(MIMachineRecipeTypes.MACERATOR, 2, machineRecipeDuration)
								.addItemOutput(item.item(), resultsInTinyDust ? tinyDustOutput : tinyDustOutput / 9)
								.addItemInput(tag, 1)));
	}
}
