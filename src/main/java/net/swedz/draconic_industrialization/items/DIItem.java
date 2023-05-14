package net.swedz.draconic_industrialization.items;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public record DIItem(Item item, DIItemSettings settings)
{
	public ResourceLocation location()
	{
		return Registry.ITEM.getKey(item);
	}
	
	public String modId()
	{
		return this.location().getNamespace();
	}
	
	public String id(boolean modid)
	{
		return modid ? this.location().toString() : this.location().getPath();
	}
	
	public boolean isBlock()
	{
		return item instanceof BlockItem;
	}
}
