package net.swedz.draconic_industrialization.recipes.wrapper;

import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public final class MIJsonRecipeBuilderWrapper extends RecipeBuilderWrapper<MIRecipeJson>
{
	public MIJsonRecipeBuilderWrapper(MIRecipeJson wrapped)
	{
		super(wrapped);
	}
	
	@Override
	public void save(Consumer<FinishedRecipe> exporter, String recipeId)
	{
		wrapped.offerTo(exporter, recipeId);
	}
}
