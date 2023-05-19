package net.swedz.draconic_industrialization.dracomenu;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.module.DracoItem;

public record DracoItemStack(DracoItem item, ItemStack stack, Slot slot)
{
	public static final DracoItemStack EMPTY = new DracoItemStack(null, ItemStack.EMPTY, null);
	
	public boolean isEmpty()
	{
		return this == EMPTY;
	}
	
	public boolean matches(Slot other)
	{
		return other != null && other.equals(slot);
	}
}
