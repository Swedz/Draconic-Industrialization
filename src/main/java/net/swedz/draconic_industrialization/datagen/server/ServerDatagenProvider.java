package net.swedz.draconic_industrialization.datagen.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItems;

import java.io.IOException;

public final class ServerDatagenProvider extends DatagenProvider
{
	ServerDatagenProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator, "Server");
	}
	
	@Override
	public void run(CachedOutput output) throws IOException
	{
		DraconicIndustrialization.LOGGER.info("Start of ITEM");
		for(DIItem item : DIItems.all())
		{
			DraconicIndustrialization.LOGGER.info("Running functions for item {}", item.id(true));
			item.settings().datagenFunctions().executeAll(DatagenFunctionCategory.ITEM_SERVER, this, output, item);
		}
		DraconicIndustrialization.LOGGER.info("End of ITEM");
		
		DraconicIndustrialization.LOGGER.info("Start of BLOCK");
		for(DIBlock block : DIBlocks.all())
		{
			DraconicIndustrialization.LOGGER.info("Running functions for block {}", block.id(true));
			block.properties().datagenFunctions().executeAll(DatagenFunctionCategory.BLOCK_SERVER, this, output, block);
		}
		DraconicIndustrialization.LOGGER.info("End of BLOCK");
	}
}
