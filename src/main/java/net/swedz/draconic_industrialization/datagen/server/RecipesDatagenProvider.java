package net.swedz.draconic_industrialization.datagen.server;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.recipes.RecipeDatagen;
import net.swedz.draconic_industrialization.recipes.RecipeMapWrapper;

import java.io.IOException;
import java.util.Set;

public class RecipesDatagenProvider extends DatagenProvider
{
	RecipesDatagenProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator, "Recipes");
	}
	
	@Override
	public void run(CachedOutput output) throws IOException
	{
		final RecipeMapWrapper recipeMap = new RecipeMapWrapper();
		
		RecipeDatagen.create(recipeMap);
		
		Set<ResourceLocation> generatedRecipes = Sets.newHashSet();
		recipeMap.map().forEach((recipeId, recipeSupplier) -> recipeSupplier.get().save(
				(finishedRecipe) -> DIDatagenServer.createRecipe(this, output, generatedRecipes, (FinishedRecipe) finishedRecipe),
				recipeId
		));
	}
}