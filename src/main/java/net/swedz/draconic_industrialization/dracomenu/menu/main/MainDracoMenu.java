package net.swedz.draconic_industrialization.dracomenu.menu.main;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.DracoItemStack;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;

public final class MainDracoMenu extends DracoMenu
{
	public static final MenuType<MainDracoMenu> TYPE = Registry.register(Registry.MENU, DraconicIndustrialization.id("draco_menu.main"), new MenuType<>(MainDracoMenu::new));
	
	public static void init()
	{
		// Load the class
	}
	
	public MainDracoMenu(int syncId, Inventory inventory)
	{
		this(syncId, inventory, inventory.player);
	}
	
	public MainDracoMenu(int syncId, Inventory inventory, Player player)
	{
		super(TYPE, syncId, inventory, player);
		
		this.setSelectedItem(this.pickDefaultItem());
	}
	
	public MainDracoMenu(int syncId, Inventory inventory, Player player, DracoItemStack selectedItem)
	{
		super(TYPE, syncId, inventory, player);
		
		this.setSelectedItem(selectedItem);
	}
	
	@Override
	public void clicked(int slotId, int button, ClickType clickType, Player player)
	{
		if(slotId >= 0)
		{
			Slot slot = this.getSlot(slotId);
			this.select(slot);
		}
		super.clicked(slotId, button, clickType, player);
	}
}
