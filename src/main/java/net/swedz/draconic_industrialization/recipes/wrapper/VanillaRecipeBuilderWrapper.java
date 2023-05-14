package net.swedz.draconic_industrialization.recipes.wrapper;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;

import java.util.function.Consumer;

public final class VanillaRecipeBuilderWrapper extends RecipeBuilderWrapper<RecipeBuilder>
{
	public VanillaRecipeBuilderWrapper(RecipeBuilder wrapped)
	{
		super(wrapped);
	}
	
	@Override
	public void save(Consumer<FinishedRecipe> exporter, String recipeId)
	{
		wrapped.save(exporter, recipeId);
	}
}
