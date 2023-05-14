package net.swedz.draconic_industrialization.blocks;

import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.PartTemplate;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.items.DIMaterial;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Set;

public final class DIBlocks
{
	private static final Set<DIBlock> BLOCKS = Sets.newHashSet();
	
	public static final Block DRACONIUM_BLOCK          = materialPart(DIItems.DRACONIUM, DIBlockProperties.draconium().alwaysDropsSelf(), StandardRecipes::apply);
	public static final Block AWAKENED_DRACONIUM_BLOCK = materialPart(DIItems.AWAKENED_DRACONIUM, DIBlockProperties.awakenedDraconium().alwaysDropsSelf(), StandardRecipes::apply);
	
	public static Set<DIBlock> all()
	{
		return Set.copyOf(BLOCKS);
	}
	
	public static Block register(Block block, Item blockItem, DIBlockProperties properties)
	{
		BLOCKS.add(new DIBlock(block, blockItem, properties));
		return block;
	}
	
	public static Block generic(String id, String englishName, DIBlockProperties properties, boolean createItem)
	{
		properties.datagenFunction(DatagenFunctions.Client.Block.BASIC_MODEL);
		Block block = Registry.register(
				Registry.BLOCK,
				DraconicIndustrialization.id(id),
				new Block(properties)
		);
		Item blockItem = null;
		if(createItem)
		{
			blockItem = DIItems.blockItem(id, englishName, block, (s) ->
			{
			});
		}
		return register(block, blockItem, properties);
	}
	
	public static Block materialPart(DIMaterial material, DIBlockProperties properties, RecipeGenerator... recipeActions)
	{
		final PartTemplate partTemplate = MIParts.BLOCK.of(null);
		final String id = material.fullId(partTemplate);
		properties.datagenFunction(DatagenFunctions.Client.Block.BASIC_MODEL);
		Block block = Registry.register(
				Registry.BLOCK,
				DraconicIndustrialization.id(id),
				new Block(properties)
		);
		Item blockItem = DIItems.blockItem(id, material.fullEnglishName(partTemplate), block, (s) -> s
				.materialPart(material.id(), partTemplate, material.materialSet(), recipeActions)
				.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_RECIPE)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_TAG));
		return register(block, blockItem, properties);
	}
	
	public static void init()
	{
		// Load the class
	}
}
