package net.swedz.draconic_industrialization.dracomenu;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.dracomenu.slot.DracoPlayerInventoryArmorSlot;
import net.swedz.draconic_industrialization.dracomenu.slot.DracoPlayerInventorySlot;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.Map;
import java.util.function.BiConsumer;

public final class DracoMenu extends AbstractContainerMenu
{
	public static final MenuType<DracoMenu> TYPE = Registry.register(Registry.MENU, DraconicIndustrialization.id("draco_menu"), new MenuType<>(DracoMenu::new));
	
	static BiConsumer<DracoItemStack, DracoItemStack> SELECTED_ITEM_CHANGED_SCREEN_CALLBACK = (ignored1, ignored2) -> {};
	
	private final Map<EquipmentSlot, Slot> slotsByEquipment;
	
	private DracoItemStack selectedItem = DracoItemStack.EMPTY;
	
	public DracoMenu(int syncId, Inventory inventory)
	{
		this(syncId, inventory, inventory.player);
	}
	
	public DracoMenu(int syncId, Inventory inventory, Player player)
	{
		super(TYPE, syncId);
		
		//region Create player inventory slots
		final Map<EquipmentSlot, Slot> equipmentSlots = Maps.newHashMap();
		
		{
			final int startingX = 8;
			final int startingY = 124;
			for(int index = 0; index < 4; index++)
			{
				final EquipmentSlot equipmentSlot = InventoryMenu.SLOT_IDS[index];
				final Slot slot = this.addSlot(new DracoPlayerInventoryArmorSlot(player, inventory, 39 - index, startingX, startingY + index * 18, equipmentSlot));
				equipmentSlots.put(equipmentSlot, slot);
			}
		}
		
		{
			final Slot slot = this.addSlot(new DracoPlayerInventorySlot(player, inventory, 40, 8, 211)
			{
				public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
				{
					return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
				}
			});
			equipmentSlots.put(EquipmentSlot.OFFHAND, slot);
		}
		
		{
			final int startingX = 41;
			final int startingY = 124;
			for(int row = 0; row < 3; row++)
			{
				for(int column = 0; column < 9; column++)
				{
					this.addSlot(new DracoPlayerInventorySlot(player, inventory, column + (row + 1) * 9, startingX + column * 18, startingY + row * 18));
				}
			}
		}
		
		{
			final int startingX = 41;
			final int startingY = 182;
			for(int column = 0; column < 9; column++)
			{
				final Slot slot = this.addSlot(new DracoPlayerInventorySlot(player, inventory, column, startingX + column * 18, startingY));
				if(inventory.selected == column)
				{
					equipmentSlots.put(EquipmentSlot.MAINHAND, slot);
				}
			}
		}
		
		slotsByEquipment = Map.copyOf(equipmentSlots);
		//endregion
	}
	
	public Slot getSlotByEquipmentSlot(EquipmentSlot equipmentSlot)
	{
		return slotsByEquipment.get(equipmentSlot);
	}
	
	void setSelectedItem(DracoItemStack itemStack)
	{
		final DracoItemStack previous = selectedItem;
		selectedItem = itemStack;
		SELECTED_ITEM_CHANGED_SCREEN_CALLBACK.accept(previous, itemStack);
	}
	
	void setSelectedItem(DracoItem item, ItemStack stack, Slot slot)
	{
		this.setSelectedItem(new DracoItemStack(item, stack, slot));
	}
	
	public boolean hasSelectedItem()
	{
		return !selectedItem.isEmpty();
	}
	
	public DracoItemStack getSelectedItem()
	{
		return selectedItem;
	}
	
	public DracoColor getDisplayColor()
	{
		if(this.hasSelectedItem())
		{
			final DracoItemConfiguration itemConfiguration = selectedItem.item().dracoConfiguration(selectedItem.stack());
			return itemConfiguration.getModuleOrCreate(DracoModules.COLORIZER).color;
		}
		DraconicIndustrialization.LOGGER.warn("Failed to get display color for the draco menu");
		return DracoColor.from(DracoTier.DRACONIC);
	}
	
	private void slotClicked(Slot slot)
	{
		final ItemStack itemStack = slot.getItem();
		final Item item = itemStack.getItem();
		if(slot instanceof DracoPlayerInventorySlot && item instanceof DracoItem dracoItem)
		{
			this.setSelectedItem(selectedItem.matches(slot) ?
					DracoItemStack.EMPTY :
					new DracoItemStack(dracoItem, itemStack, slot));
		}
	}
	
	@Override
	public void clicked(int slotId, int button, ClickType clickType, Player player)
	{
		if(slotId >= 0)
		{
			Slot slot = this.getSlot(slotId);
			this.slotClicked(slot);
		}
		super.clicked(slotId, button, clickType, player);
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index)
	{
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean stillValid(Player player)
	{
		return true;
	}
}
