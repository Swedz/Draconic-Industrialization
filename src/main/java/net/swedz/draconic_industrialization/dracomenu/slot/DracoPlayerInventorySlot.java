package net.swedz.draconic_industrialization.dracomenu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.module.DracoItem;

public class DracoPlayerInventorySlot extends Slot
{
	protected final Player player;
	
	public DracoPlayerInventorySlot(Player player, Container container, int slot, int x, int y)
	{
		super(container, slot, x, y);
		this.player = player;
	}
	
	@Override
	public boolean mayPickup(Player playerx)
	{
		ItemStack itemStack = this.getItem();
		return !(itemStack.getItem() instanceof DracoItem) && super.mayPickup(playerx);
	}
}
