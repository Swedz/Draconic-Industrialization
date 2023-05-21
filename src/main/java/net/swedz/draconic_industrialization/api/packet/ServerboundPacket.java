package net.swedz.draconic_industrialization.api.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.swedz.draconic_industrialization.packet.PacketChannel;

public abstract class ServerboundPacket extends Packet
{
	public ServerboundPacket(PacketChannel channel)
	{
		super(channel);
	}
	
	public void send()
	{
		FriendlyByteBuf packet = PacketByteBufs.create();
		this.write(packet);
		ClientPlayNetworking.send(id, packet);
	}
	
	public abstract boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender);
	
	public abstract void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender);
}
