package net.swedz.draconic_industrialization.api;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class EquipmentSlotHelper
{
	public static boolean isItemInCorrectSlot(LivingEntity entity, ItemStack stack)
	{
		EquipmentSlot[] slots = getValidSlotsForItem(stack);
		for(EquipmentSlot slot : slots)
		{
			if(entity.getItemBySlot(slot) == stack)
			{
				return true;
			}
		}
		return false;
	}
	
	public static EquipmentSlot[] getValidSlotsForItem(ItemStack stack)
	{
		Item item = stack.getItem();
		if(item instanceof ArmorItem armorItem)
		{
			return new EquipmentSlot[]{armorItem.getSlot()};
		}
		else
		{
			return new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
		}
	}
}
