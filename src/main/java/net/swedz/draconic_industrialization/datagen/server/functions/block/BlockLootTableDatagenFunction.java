package net.swedz.draconic_industrialization.datagen.server.functions.block;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.api.LootTableDatagenFunction;

import java.io.IOException;

public final class BlockLootTableDatagenFunction extends LootTableDatagenFunction<DIBlock, Block>
{
	@Override
	protected Registry<Block> registry()
	{
		return Registry.BLOCK;
	}
	
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.BLOCK_SERVER;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIBlock block) throws IOException
	{
		final LootTable table = block.properties().lootTable(block);
		if(table != null)
		{
			this.add(provider, output, block.id(true), table);
		}
	}
}
