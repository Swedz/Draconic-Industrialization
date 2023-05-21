package net.swedz.draconic_industrialization.dracomenu.menu;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.dracomenu.DracoDummyPlayerUpdater;
import net.swedz.draconic_industrialization.dracomenu.DracoItemStack;
import net.swedz.draconic_industrialization.dracomenu.slot.DracoPlayerInventoryArmorSlot;
import net.swedz.draconic_industrialization.dracomenu.slot.DracoPlayerInventorySlot;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.Map;

public abstract class DracoMenu extends AbstractContainerMenu
{
	public static final EquipmentSlot[] DEFAULT_SLOT_PRIORITY_ORDER = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
	
	protected final Player    player;
	protected final Inventory inventory;
	
	private final Map<EquipmentSlot, Slot> slotsByEquipment;
	
	private DracoItemStack selectedItem;
	
	private DracoDummyPlayerUpdater dummyPlayerUpdater = DracoDummyPlayerUpdater.NOOP;
	
	public DracoMenu(MenuType<?> menuType, int syncId, Inventory inventory, Player player)
	{
		super(menuType, syncId);
		
		this.player = player;
		this.inventory = inventory;
		
		this.slotsByEquipment = Maps.newHashMap();
		this.initPlayerInventorySlots();
	}
	
	private void initPlayerInventorySlots()
	{
		// Armor slots
		{
			final int startingX = 8;
			final int startingY = 124;
			for(int index = 0; index < 4; index++)
			{
				final EquipmentSlot equipmentSlot = InventoryMenu.SLOT_IDS[index];
				final Slot slot = this.addSlot(new DracoPlayerInventoryArmorSlot(player, inventory, 39 - index, startingX, startingY + index * 18, equipmentSlot));
				slotsByEquipment.put(equipmentSlot, slot);
			}
		}
		// Offhand slot
		{
			final Slot slot = this.addSlot(new DracoPlayerInventorySlot(player, inventory, 40, 8, 211)
			{
				public Pair<ResourceLocation, ResourceLocation> getNoItemIcon()
				{
					return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
				}
			});
			slotsByEquipment.put(EquipmentSlot.OFFHAND, slot);
		}
		// Inventory slots
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
		// Hotbar slots
		{
			final int startingX = 41;
			final int startingY = 182;
			for(int column = 0; column < 9; column++)
			{
				final Slot slot = this.addSlot(new DracoPlayerInventorySlot(player, inventory, column, startingX + column * 18, startingY));
				if(inventory.selected == column)
				{
					slotsByEquipment.put(EquipmentSlot.MAINHAND, slot);
				}
			}
		}
	}
	
	public Slot getSlotByEquipmentSlot(EquipmentSlot equipmentSlot)
	{
		return slotsByEquipment.get(equipmentSlot);
	}
	
	public DracoItemStack pickDefaultItem()
	{
		for(EquipmentSlot slot : DEFAULT_SLOT_PRIORITY_ORDER)
		{
			final ItemStack itemStack = player.getItemBySlot(slot);
			if(itemStack.getItem() instanceof DracoItem item)
			{
				return new DracoItemStack(item, this.getSlotByEquipmentSlot(slot));
			}
		}
		return DracoItemStack.EMPTY;
	}
	
	public boolean hasSelectedItem()
	{
		return !selectedItem.isEmpty();
	}
	
	public DracoItemStack getSelectedItem()
	{
		return selectedItem;
	}
	
	public DracoItemConfiguration getSelectedItemConfiguration()
	{
		return selectedItem.item().dracoConfiguration(selectedItem.stack());
	}
	
	public void setSelectedItem(DracoItemStack itemStack)
	{
		final DracoItemStack previous = selectedItem;
		selectedItem = itemStack;
		dummyPlayerUpdater.update(previous, itemStack);
	}
	
	public void select(Slot slot)
	{
		final ItemStack itemStack = slot.getItem();
		final Item item = itemStack.getItem();
		if(slot instanceof DracoPlayerInventorySlot && item instanceof DracoItem dracoItem)
		{
			this.setSelectedItem(selectedItem.matches(slot) ?
					DracoItemStack.EMPTY :
					new DracoItemStack(dracoItem, slot));
		}
	}
	
	public DracoColor getDisplayColor()
	{
		if(this.hasSelectedItem())
		{
			return this.getSelectedItemConfiguration().getModuleOrCreate(DracoModules.COLORIZER).color;
		}
		DraconicIndustrialization.LOGGER.warn("Failed to get display color for the draco menu");
		return DracoColor.from(DracoTier.DRACONIC);
	}
	
	public void setDummyPlayerUpdater(DracoDummyPlayerUpdater updater)
	{
		this.dummyPlayerUpdater = updater;
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
