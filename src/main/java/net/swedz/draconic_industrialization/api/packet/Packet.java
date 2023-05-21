package net.swedz.draconic_industrialization.api.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.packet.PacketChannel;

public abstract class Packet
{
	protected final ResourceLocation id;
	
	public Packet(PacketChannel channel)
	{
		this.id = channel.id();
	}
	
	public ResourceLocation id()
	{
		return id;
	}
	
	public abstract void read(FriendlyByteBuf packet);
	
	public abstract void write(FriendlyByteBuf packet);
}
