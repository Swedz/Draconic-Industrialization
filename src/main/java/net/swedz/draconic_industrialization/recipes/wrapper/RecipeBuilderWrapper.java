package net.swedz.draconic_industrialization.recipes.wrapper;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public abstract class RecipeBuilderWrapper<C>
{
	protected final C wrapped;
	
	public RecipeBuilderWrapper(C wrapped)
	{
		this.wrapped = wrapped;
	}
	
	public abstract void save(Consumer<FinishedRecipe> exporter, String recipeId);
}
