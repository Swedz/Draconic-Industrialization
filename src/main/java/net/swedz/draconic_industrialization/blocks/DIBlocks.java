package net.swedz.draconic_industrialization.blocks;

import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.DIItems;

import java.util.Set;

public final class DIBlocks
{
	private static final Set<DIBlock> BLOCKS = Sets.newHashSet();
	
	public static final Block DRACONIUM_BLOCK          = generic("draconium_block", "Draconium Block", DIBlockProperties.draconium().alwaysDropsSelf(), true);
	public static final Block AWAKENED_DRACONIUM_BLOCK = generic("awakened_draconium_block", "Awakened Draconium Block", DIBlockProperties.awakenedDraconium().alwaysDropsSelf(), true);
	
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
	
	public static void init()
	{
		// Load the class
	}
}
