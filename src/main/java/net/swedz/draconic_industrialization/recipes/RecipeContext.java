package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.datagen.server.DIDatagenServer;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.material.DIMaterialPart;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record RecipeContext(DIItem item, RecipeMapWrapper recipeMap)
{
	public void addVanilla(String id, Supplier<RecipeBuilder> recipeSupplier)
	{
		recipeMap.addVanilla(id, recipeSupplier);
	}
	
	public void addMI(String id, Supplier<MIRecipeJson> recipeSupplier)
	{
		recipeMap.addMI(id, recipeSupplier);
	}
	
	public void ifExists(DIMaterialPart isPart, String hasTag, Consumer<TagKey> action)
	{
		if(item.settings().materialPart().part().equals(isPart))
		{
			TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(hasTag));
			if(DIDatagenServer.hasTag(tagKey))
			{
				action.accept(tagKey);
			}
		}
	}
	
	public void ifExists(DIMaterialPart isPart, DIMaterialPart hasTag, Consumer<TagKey> action)
	{
		this.ifExists(isPart, hasTag.tag(item.settings().materialPart().material().id()), action);
	}
	
	public String recipeId(String format, DIMaterialPart otherPart)
	{
		return format.formatted(item.id(false), otherPart.id());
	}
	
	public String recipeId(String format)
	{
		return format.formatted(item.id(false));
	}
}