package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.minecraft.network.FriendlyByteBuf;
import net.swedz.draconic_industrialization.api.packet.ServerboundPacket;
import net.swedz.draconic_industrialization.packet.PacketChannel;

public abstract class SlotXYServerboundPacket extends ServerboundPacket
{
	public int slotX, slotY;
	
	public SlotXYServerboundPacket(PacketChannel channel)
	{
		super(channel);
	}
	
	@Override
	public void read(FriendlyByteBuf packet)
	{
		slotX = packet.readInt();
		slotY = packet.readInt();
	}
	
	@Override
	public void write(FriendlyByteBuf packet)
	{
		packet.writeInt(slotX);
		packet.writeInt(slotY);
	}
}
