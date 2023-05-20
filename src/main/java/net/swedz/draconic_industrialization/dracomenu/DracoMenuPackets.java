package net.swedz.draconic_industrialization.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class DracoMenuPackets
{
	public static void initServer()
	{
		ServerPlayNetworking.registerGlobalReceiver(DIPacketChannels.ClientToServer.DRACO_MENU_REQUEST_OPEN, DracoMenuPackets::handleClientRequestOpen);
		
		ServerPlayNetworking.registerGlobalReceiver(DIPacketChannels.ClientToServer.DRACO_MENU_INSERT_ITEM, (server, player, handler, packet, responseSender) ->
		{
			if(validateClientInsertItem(server, player, handler, packet, responseSender))
			{
				handleClientInsertItem(server, player, handler, packet, responseSender);
			}
		});
		
		ServerPlayNetworking.registerGlobalReceiver(DIPacketChannels.ClientToServer.DRACO_MENU_TAKE_ITEM, (server, player, handler, packet, responseSender) ->
		{
			if(validateClientTakeItem(server, player, handler, packet, responseSender))
			{
				handleClientTakeItem(server, player, handler, packet, responseSender);
			}
		});
	}
	
	private static void handleClientRequestOpen(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf packet, PacketSender responseSender)
	{
		player.openMenu(new SimpleMenuProvider(
				DracoMenu::new,
				Component.translatable("screen.draconic_industrialization.draco")
		));
	}
	
	private static boolean validateClientInsertItem(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf packet, PacketSender responseSender)
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
	
	private static void handleClientInsertItem(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf packet, PacketSender responseSender)
	{
		final DracoMenu menu = (DracoMenu) player.containerMenu;
		final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
		final ItemStack insertItem = menu.getCarried();
		final DracoModuleItem moduleItem = (DracoModuleItem) insertItem.getItem();
		final DracoModule module = DracoModules.create(moduleItem.moduleReference(), itemConfiguration.item(), new NBTTagWrapper(insertItem.getOrCreateTagElement("DracoModule")));
		final int slotX = packet.readInt();
		final int slotY = packet.readInt();
		
		if(!module.applies() || itemConfiguration.countModules(module.reference()) >= module.max())
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
	
	private static boolean validateClientTakeItem(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf packet, PacketSender responseSender)
	{
		if(!(player.containerMenu instanceof DracoMenu menu))
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Take Item) packet without being inside of the menu",
					player.getName()
			);
			return false;
		}
		if(!menu.hasSelectedItem())
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Take Item) packet without a selected item",
					player.getName()
			);
			return false;
		}
		if(!menu.getCarried().isEmpty())
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Take Item) packet while carrying an item in their cursor",
					player.getName()
			);
			return false;
		}
		return true;
	}
	
	private static void handleClientTakeItem(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf packet, PacketSender responseSender)
	{
		final DracoMenu menu = (DracoMenu) player.containerMenu;
		final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
		final int slotX = packet.readInt();
		final int slotY = packet.readInt();
		
		itemConfiguration.grid().pull(slotX, slotY).ifPresent((entry) ->
				server.execute(() ->
				{
					itemConfiguration.save();
					menu.setCarried(new ItemStack(entry.module().reference().item()));
				}));
	}
}
