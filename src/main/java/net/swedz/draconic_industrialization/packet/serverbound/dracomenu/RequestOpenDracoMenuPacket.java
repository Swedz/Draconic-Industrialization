package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.SimpleMenuProvider;
import net.swedz.draconic_industrialization.api.packet.ServerboundPacket;
import net.swedz.draconic_industrialization.dracomenu.DracoMenu;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

public final class RequestOpenDracoMenuPacket extends ServerboundPacket
{
	public RequestOpenDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_REQUEST_OPEN);
	}
	
	@Override
	public void read(FriendlyByteBuf packet)
	{
		// Nothing
	}
	
	@Override
	public void write(FriendlyByteBuf packet)
	{
		// Nothing
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		return true;
	}
	
	@Override
	public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		player.openMenu(new SimpleMenuProvider(
				DracoMenu::new,
				Component.translatable("screen.draconic_industrialization.draco")
		));
	}
}
