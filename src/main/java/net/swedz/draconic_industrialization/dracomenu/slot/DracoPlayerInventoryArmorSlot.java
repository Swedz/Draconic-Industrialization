package net.swedz.draconic_industrialization.dracomenu.slot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public class DracoPlayerInventoryArmorSlot extends DracoPlayerInventorySlot
{
	protected final EquipmentSlot equipmentSlot;
	
	public DracoPlayerInventoryArmorSlot(Player player, Container container, int slot, int x, int y, EquipmentSlot equipmentSlot)
	{
		super(player, container, slot, x, y);
		this.equipmentSlot = equipmentSlot;
	}
	
	@Override
	public void set(ItemStack stack)
	{
		ItemStack itemStack = this.getItem();
		super.set(stack);
		player.onEquipItem(equipmentSlot, itemStack, stack);
	}
	
	@Override
	public int getMaxStackSize()
	{
		return 1;
	}
	
	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return equipmentSlot == Mob.getEquipmentSlotForItem(stack);
	}
	
	@Override
	public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
	{
		return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[equipmentSlot.getIndex()]);
	}
}
