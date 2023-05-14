package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.materials.part.PartKeyProvider;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.datagen.server.DIDatagenServer;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.recipes.wrapper.MIJsonRecipeBuilderWrapper;
import net.swedz.draconic_industrialization.recipes.wrapper.RecipeBuilderWrapper;
import net.swedz.draconic_industrialization.recipes.wrapper.VanillaRecipeBuilderWrapper;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public record RecipeContext(DIItem item, RecipeMap recipeMap)
{
	public static final class RecipeMap extends HashMap<String, Supplier<RecipeBuilderWrapper>>
	{
	}
	
	public void addVanilla(String id, Supplier<RecipeBuilder> recipeSupplier)
	{
		recipeMap.put(id, () -> new VanillaRecipeBuilderWrapper(recipeSupplier.get()));
	}
	
	public void addMI(String id, Supplier<MIRecipeJson> recipeSupplier)
	{
		recipeMap.put(id, () -> new MIJsonRecipeBuilderWrapper(recipeSupplier.get()));
	}
	
	public void ifExists(PartKeyProvider isPart, String hasTag, Consumer<TagKey> action)
	{
		if(item.settings().materialPart().isPart(isPart))
		{
			TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(hasTag));
			if(DIDatagenServer.hasTag(tagKey))
			{
				action.accept(tagKey);
			}
		}
	}
}