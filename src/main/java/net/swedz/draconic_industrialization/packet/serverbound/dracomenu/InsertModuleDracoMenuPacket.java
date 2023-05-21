package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.dracomenu.DracoMenu;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class InsertModuleDracoMenuPacket extends SlotXYServerboundPacket
{
	public InsertModuleDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_INSERT_MODULE);
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		if(!(player.containerMenu instanceof DracoMenu menu))
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Insert Item) packet without being inside of the menu",
					player.getName()
			);
			return false;
		}
		if(!menu.hasSelectedItem())
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Insert Item) packet without a selected item",
					player.getName()
			);
			return false;
		}
		if(!(menu.getCarried().getItem() instanceof DracoModuleItem))
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Insert Item) packet without carried item",
					player.getName()
			);
			return false;
		}
		return true;
	}
	
	@Override
	public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		final DracoMenu menu = (DracoMenu) player.containerMenu;
		final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
		final ItemStack insertItem = menu.getCarried();
		final DracoModuleItem moduleItem = (DracoModuleItem) insertItem.getItem();
		final DracoModule module = DracoModules.create(moduleItem.moduleReference(), itemConfiguration.item(), new NBTTagWrapper(insertItem.getOrCreateTagElement(DracoModuleItem.PARENT_KEY)));
		
		if(!module.applies(itemConfiguration.item()) ||
				itemConfiguration.countModules(module.reference()) >= module.max())
		{
			return;
		}
		
		if(itemConfiguration.grid().add(slotX, slotY, module))
		{
			server.execute(() ->
			{
				menu.setCarried(ItemStack.EMPTY);
				itemConfiguration.save();
			});
		}
	}
}
