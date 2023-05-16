package net.swedz.draconic_industrialization.datagen.server;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;

import java.util.Set;

public final class DIDatagenServer
{
	private static final Set<String> TAG_CACHE = Sets.newHashSet();
	
	public static void trackTag(String tag)
	{
		TAG_CACHE.add(tag);
	}
	
	public static boolean hasTag(TagKey<Item> tag)
	{
		return TAG_CACHE.contains(tag.location().toString());
	}
	
	public static void configure(FabricDataGenerator dataGenerator)
	{
		TAG_CACHE.clear();
		
		dataGenerator.addProvider(ServerDatagenProvider::new);
		dataGenerator.addProvider(RecipesDatagenProvider::new);
	}
	
	public static void createRecipe(DatagenProvider provider, CachedOutput output, Set<ResourceLocation> generatedRecipes, FinishedRecipe finishedRecipe)
	{
		ResourceLocation identifier = getRecipeIdentifier(provider, finishedRecipe.getId());
		
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
							.json(getRecipeIdentifier(provider, finishedRecipe.getAdvancementId()))
			);
		}
	}
	
	private static ResourceLocation getRecipeIdentifier(DatagenProvider provider, ResourceLocation identifier)
	{
		return new ResourceLocation(provider.modId(), identifier.getPath());
	}
}
