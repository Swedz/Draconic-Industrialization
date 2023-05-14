package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.materials.part.MIParts;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.datagen.server.DIDatagenServer;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;

public final class StandardRecipes
{
	public static void apply(RecipeContext context)
	{
		final DIItem item = context.item();
		final DIItemSettings.MaterialPart material = item.settings().materialPart();
		
		DraconicIndustrialization.LOGGER.info("Running StandardRecipes for {}", item.id(true));
		
		// Ingot from block
		if(material.isPart(MIParts.INGOT))
		{
			TagKey<Item> tag = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(DIDatagenClient.tagMaterialTarget(material.name(), "block")));
			if(DIDatagenServer.hasTag(tag))
			{
				context.add("draconic_industrialization:%s_from_%s".formatted(item.id(false), "block"), ShapelessRecipeBuilder
						.shapeless(item.item())
						.requires(tag));
			}
		}
	}
}
