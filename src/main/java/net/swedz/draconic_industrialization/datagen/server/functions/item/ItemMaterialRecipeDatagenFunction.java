package net.swedz.draconic_industrialization.datagen.server.functions.item;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.recipes.RecipeContext;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;

import java.util.Set;
import java.util.function.Consumer;

public final class ItemMaterialRecipeDatagenFunction implements DatagenFunction<DIItem>
{
	private final Set<Consumer<RecipeContext.RecipeMap>> recipeActions = Sets.newHashSet();
	
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
		recipeActions.clear();
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item)
	{
		final DIItemSettings settings = item.settings();
		if(!settings.isMaterial())
		{
			throw new IllegalArgumentException("Provided non-material item to ItemMaterialRecipeDatagenFunction '%s'".formatted(item.id(true)));
		}
		
		for(RecipeGenerator generator : settings.materialPart().recipeActions())
		{
			recipeActions.add((recipeMap) -> generator.accept(new RecipeContext(item, recipeMap)));
		}
	}
	
	@Override
	public void globalAfter(DatagenProvider provider, CachedOutput output)
	{
		final RecipeContext.RecipeMap recipeMap = new RecipeContext.RecipeMap();
		
		recipeActions.forEach((action) -> action.accept(recipeMap));
		
		Set<ResourceLocation> generatedRecipes = Sets.newHashSet();
		recipeMap.forEach((recipeId, recipeSupplier) -> recipeSupplier.get().save(
				(finishedRecipe) -> this.createRecipe(provider, output, generatedRecipes, (FinishedRecipe) finishedRecipe),
				recipeId
		));
	}
	
	private void createRecipe(DatagenProvider provider, CachedOutput output, Set<ResourceLocation> generatedRecipes, FinishedRecipe finishedRecipe)
	{
		ResourceLocation identifier = this.getRecipeIdentifier(provider, finishedRecipe.getId());
		
		DraconicIndustrialization.LOGGER.info("Creating recipe with id {}", identifier);
		
		if(!generatedRecipes.add(identifier))
		{
			throw new IllegalStateException("Duplicate recipe " + identifier);
		}
		
		JsonObject recipeJson = finishedRecipe.serializeRecipe();
		ConditionJsonProvider[] conditions = FabricDataGenHelper.consumeConditions(finishedRecipe);
		ConditionJsonProvider.write(recipeJson, conditions);
		
		RecipeProvider.saveRecipe(output, recipeJson,
				provider.dataGenerator().createPathProvider(DataGenerator.Target.DATA_PACK, "recipes")
						.json(identifier)
		);
		JsonObject advancementJson = finishedRecipe.serializeAdvancement();
		
		if(advancementJson != null)
		{
			ConditionJsonProvider.write(advancementJson, conditions);
			RecipeProvider.saveAdvancement(output, advancementJson,
					provider.dataGenerator().createPathProvider(DataGenerator.Target.DATA_PACK, "advancements")
							.json(this.getRecipeIdentifier(provider, finishedRecipe.getAdvancementId()))
			);
		}
	}
	
	private ResourceLocation getRecipeIdentifier(DatagenProvider provider, ResourceLocation identifier)
	{
		return new ResourceLocation(provider.modId(), identifier.getPath());
	}
}
