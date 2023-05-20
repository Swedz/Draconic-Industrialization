package net.swedz.draconic_industrialization.dracomenu;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.module.DracoItem;

public record DracoItemStack(DracoItem item, Slot slot)
{
	public static final DracoItemStack EMPTY = new DracoItemStack(null, null);
	
	public ItemStack stack()
	{
		return slot.getItem();
	}
	
	public boolean isEmpty()
	{
		return this == EMPTY;
	}
	
	public boolean matches(Slot other)
	{
		return other != null && other.equals(slot);
	}
}
