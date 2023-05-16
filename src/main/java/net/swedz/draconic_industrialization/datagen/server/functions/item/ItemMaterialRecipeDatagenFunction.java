package net.swedz.draconic_industrialization.datagen.server.functions.item;

import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.server.DIDatagenServer;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.recipes.RecipeContext;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;
import net.swedz.draconic_industrialization.recipes.RecipeMapWrapper;

import java.util.Set;

public final class ItemMaterialRecipeDatagenFunction implements DatagenFunction<DIItem>
{
	private final Set<DIItem> collectedItems = Sets.newHashSet();
	
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_SERVER;
	}
	
	@Override
	public int priority()
	{
		return 1;
	}
	
	@Override
	public void globalInit(DatagenProvider provider, CachedOutput output)
	{
		collectedItems.clear();
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item)
	{
		// We do things in the globalAfter() instead because then it'll be guaranteed each item has its tags registered by then
		collectedItems.add(item);
	}
	
	@Override
	public void globalAfter(DatagenProvider provider, CachedOutput output)
	{
		final RecipeMapWrapper recipeMap = new RecipeMapWrapper();
		
		for(DIItem item : collectedItems)
		{
			final DIItemSettings settings = item.settings();
			if(!settings.isMaterial())
			{
				throw new IllegalArgumentException("Provided non-material item to ItemMaterialRecipeDatagenFunction '%s'".formatted(item.id(true)));
			}
			
			for(RecipeGenerator generator : settings.materialPart().recipeActions())
			{
				generator.accept(new RecipeContext(item, recipeMap));
			}
		}
		
		Set<ResourceLocation> generatedRecipes = Sets.newHashSet();
		recipeMap.map().forEach((recipeId, recipeSupplier) -> recipeSupplier.get().save(
				(finishedRecipe) -> DIDatagenServer.createRecipe(provider, output, generatedRecipes, (FinishedRecipe) finishedRecipe),
				recipeId
		));
	}
}
