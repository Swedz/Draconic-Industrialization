package net.swedz.draconic_industrialization.dracomenu;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.dummy.level.DummyClientPlayerEntity;
import net.swedz.draconic_industrialization.module.DracoItem;

import java.util.Optional;

public final class DracoDummyPlayer extends DummyClientPlayerEntity
{
	private static final EquipmentSlot[] DEFAULT_SLOT_PRIORITY_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
	
	public DracoDummyPlayer(LocalPlayer fromPlayer)
	{
		super(fromPlayer);
		this.resetEquipmentSlots();
	}
	
	public void resetEquipmentSlots()
	{
		for(EquipmentSlot slot : EquipmentSlot.values())
		{
			final ItemStack itemStack = parent.getItemBySlot(slot);
			this.setEquipment(slot, itemStack.getItem() instanceof DracoItem ? itemStack : ItemStack.EMPTY);
		}
	}
	
	public void updateDueToSelectedItemChange(DracoItemStack selectedItem)
	{
		if(selectedItem.isEmpty())
		{
			this.resetEquipmentSlots();
		}
		else
		{
			final ItemStack stack = selectedItem.stack();
			if(stack.getItem() instanceof DracoItem)
			{
				this.setEquipment(Mob.getEquipmentSlotForItem(stack), stack);
			}
		}
	}
	
	public Optional<DracoItemStack> pickDefaultSelectedItem(DracoMenu menu)
	{
		for(EquipmentSlot slot : DEFAULT_SLOT_PRIORITY_ORDER)
		{
			final ItemStack itemStack = parent.getItemBySlot(slot);
			if(itemStack.getItem() instanceof DracoItem item)
			{
				return Optional.of(new DracoItemStack(item, itemStack, menu.getSlotByEquipmentSlot(slot)));
			}
		}
		return Optional.empty();
	}
}
