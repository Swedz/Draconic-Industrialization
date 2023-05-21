package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class StartConfigureModuleDracoMenuPacket extends SlotXYServerboundPacket
{
	public StartConfigureModuleDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_START_CONFIGURE_MODULE);
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		return true;
	}
	
	@Override
	public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		DraconicIndustrialization.LOGGER.info("Start configuration received");
	}
}
