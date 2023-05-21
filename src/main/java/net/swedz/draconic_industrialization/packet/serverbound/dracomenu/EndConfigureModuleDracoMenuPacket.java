package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.packet.ServerboundPacket;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class EndConfigureModuleDracoMenuPacket extends ServerboundPacket
{
	public EndConfigureModuleDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_END_CONFIGURE_MODULE);
	}
	
	@Override
	public void read(FriendlyByteBuf packet)
	{
	
	}
	
	@Override
	public void write(FriendlyByteBuf packet)
	{
	
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		return true;
	}
	
	@Override
	public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		DraconicIndustrialization.LOGGER.info("End configuration received");
	}
}
