package net.swedz.draconic_industrialization.api.packet;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.swedz.draconic_industrialization.packet.PacketChannel;

public abstract class ClientboundPacket extends Packet
{
	public ClientboundPacket(PacketChannel channel)
	{
		super(channel);
	}
	
	public void send(ServerPlayer player)
	{
		FriendlyByteBuf packet = PacketByteBufs.create();
		this.write(packet);
		ServerPlayNetworking.send(player, id, packet);
	}
	
	public abstract boolean confirm(Minecraft client, ClientPacketListener handler, PacketSender responseSender);
	
	public abstract void handle(Minecraft client, ClientPacketListener handler, PacketSender responseSender);
}
