package net.swedz.draconic_industrialization.datagen.server.functions.block;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.level.block.Block;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.api.TagDatagenFunction;

public final class VariableBlockTagDatagenFunction extends TagDatagenFunction<DIBlock, Block>
{
	@Override
	protected Registry registry()
	{
		return Registry.BLOCK;
	}
	
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.BLOCK_SERVER;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIBlock block)
	{
		block.properties().tags().forEach((tag) ->
		{
			DraconicIndustrialization.LOGGER.info("Adding tag {} to block {}", tag.location(), block.id(true));
			this.tag(tag).add(block.block());
		});
	}
}
