package net.swedz.draconic_industrialization.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.api.packet.ClientboundPacket;
import net.swedz.draconic_industrialization.api.packet.Packet;
import net.swedz.draconic_industrialization.api.packet.ServerboundPacket;

import java.util.function.Supplier;

public record PacketChannel<P extends Packet>(ResourceLocation id, PacketType type, Supplier<P> packetCreator)
{
	public P createPacket()
	{
		return packetCreator.get();
	}
	
	public void registerListener()
	{
		if(type == PacketType.SERVERBOUND)
		{
			ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buf, responseSender) ->
			{
				P packet = this.createPacket();
				if(packet instanceof ServerboundPacket serverboundPacket)
				{
					serverboundPacket.read(buf);
					if(serverboundPacket.confirm(server, player, handler, responseSender))
					{
						serverboundPacket.handle(server, player, handler, responseSender);
					}
				}
			});
		}
		else
		{
			ClientPlayNetworking.registerGlobalReceiver(id, (client, handler, buf, responseSender) ->
			{
				P packet = this.createPacket();
				if(packet instanceof ClientboundPacket clientboundPacket)
				{
					clientboundPacket.read(buf);
					if(clientboundPacket.confirm(client, handler, responseSender))
					{
						clientboundPacket.handle(client, handler, responseSender);
					}
				}
			});
		}
	}
}
