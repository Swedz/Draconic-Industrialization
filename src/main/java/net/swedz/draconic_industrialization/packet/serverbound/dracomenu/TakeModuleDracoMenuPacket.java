package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.DracoMenu;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class TakeModuleDracoMenuPacket extends SlotXYServerboundPacket
{
	public TakeModuleDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_TAKE_MODULE);
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
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
	
	@Override
	public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		final DracoMenu menu = (DracoMenu) player.containerMenu;
		final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
		
		itemConfiguration.grid().pull(slotX, slotY).ifPresent((entry) ->
				server.execute(() ->
				{
					itemConfiguration.save();
					menu.setCarried(entry.module().itemize(itemConfiguration.item()));
				}));
	}
}
