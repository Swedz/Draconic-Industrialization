package net.swedz.draconic_industrialization.items;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record DIItem(Item item, DIItemSettings settings)
{
	public ResourceLocation location()
	{
		return Registry.ITEM.getKey(item);
	}
}
