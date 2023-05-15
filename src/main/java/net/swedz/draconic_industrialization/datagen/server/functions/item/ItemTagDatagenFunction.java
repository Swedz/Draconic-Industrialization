package net.swedz.draconic_industrialization.datagen.server.functions.item;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.api.TagDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.DIDatagenServer;
import net.swedz.draconic_industrialization.items.DIItem;

public final class ItemTagDatagenFunction extends TagDatagenFunction<DIItem, Item>
{
	@Override
	protected Registry registry()
	{
		return Registry.ITEM;
	}
	
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_SERVER;
	}
	
	@Override
	public int priority()
	{
		return -1;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item)
	{
		item.settings().tags().forEach((tag) ->
		{
			DraconicIndustrialization.LOGGER.info("Adding tag {} to item {}", tag.location(), item.id(true));
			this.tag(tag).add(item.item());
			DIDatagenServer.trackTag(tag.location().toString());
		});
	}
}
