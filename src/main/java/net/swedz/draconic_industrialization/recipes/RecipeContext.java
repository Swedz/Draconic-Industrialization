package net.swedz.draconic_industrialization.recipes;

import net.minecraft.data.recipes.RecipeBuilder;
import net.swedz.draconic_industrialization.items.DIItem;

import java.util.HashMap;

public record RecipeContext(DIItem item, RecipeMap recipeMap)
{
	public static final class RecipeMap extends HashMap<String, RecipeBuilder>
	{
	}
	
	public void add(String id, RecipeBuilder recipe)
	{
		recipeMap.put(id, recipe);
	}
}