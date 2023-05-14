package net.swedz.draconic_industrialization.datagen.server.functions.item;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.api.TagDatagenFunction;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;

public final class ItemMaterialTagDatagenFunction extends TagDatagenFunction<DIItem, Item>
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
	public void run(DatagenProvider provider, CachedOutput output, DIItem item)
	{
		final DIItemSettings settings = item.settings();
		if(!settings.isMaterial())
		{
			throw new IllegalArgumentException("Provided non-material item to ItemMaterialTagDatagenFunction '%s'".formatted(item.id(true)));
		}
		final String itemId = item.id(false);
		final DIItemSettings.MaterialPart materialPart = settings.materialPart();
		final String partId = materialPart.partId();
		
		this.tag("c:%s_%ss".formatted(itemId, partId)).add(item.item());
	}
}
