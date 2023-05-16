package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import com.google.common.collect.Maps;
import net.minecraft.data.recipes.RecipeBuilder;
import net.swedz.draconic_industrialization.recipes.wrapper.MIJsonRecipeBuilderWrapper;
import net.swedz.draconic_industrialization.recipes.wrapper.RecipeBuilderWrapper;
import net.swedz.draconic_industrialization.recipes.wrapper.VanillaRecipeBuilderWrapper;

import java.util.Map;
import java.util.function.Supplier;

public record RecipeMapWrapper(Map<String, Supplier<RecipeBuilderWrapper>> map)
{
	public RecipeMapWrapper()
	{
		this(Maps.newHashMap());
	}
	
	public void addVanilla(String id, Supplier<RecipeBuilder> recipeSupplier)
	{
		map.put(id, () -> new VanillaRecipeBuilderWrapper(recipeSupplier.get()));
	}
	
	public void addMI(String id, Supplier<MIRecipeJson> recipeSupplier)
	{
		map.put(id, () -> new MIJsonRecipeBuilderWrapper(recipeSupplier.get()));
	}
}
