package net.swedz.draconic_industrialization.packet.serverbound.dracomenu;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;

import java.io.IOException;

public final class PostModuleConfigChangesDracoMenuPacket extends SlotXYServerboundPacket
{
	public DracoModule module;
	public CompoundTag moduleTag;
	
	public PostModuleConfigChangesDracoMenuPacket()
	{
		super(DIPacketChannels.Serverbound.DRACO_MENU_POST_MODULE_CONFIG_CHANGES);
	}
	
	@Override
	public void read(FriendlyByteBuf packet)
	{
		super.read(packet);
		try (ByteBufInputStream stream = new ByteBufInputStream(packet))
		{
			moduleTag = NbtIo.read(stream);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void write(FriendlyByteBuf packet)
	{
		super.write(packet);
		try (ByteBufOutputStream stream = new ByteBufOutputStream(packet))
		{
			NbtIo.write(module.serialize(null), stream);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public boolean confirm(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, PacketSender responseSender)
	{
		if(!(player.containerMenu instanceof DracoMenu menu))
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Post Module Config Changes) packet without being inside of the menu",
					player.getName()
			);
			return false;
		}
		if(!menu.hasSelectedItem())
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Post Module Config Changes) packet without a selected item being present",
					player.getName()
			);
			return false;
		}
		if(moduleTag == null || !moduleTag.contains("Key"))
		{
			DraconicIndustrialization.LOGGER.warn(
					"{} sent a Draco Menu (Post Module Config Changes) packet without a key provided in the nbt tag",
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
		
		itemConfiguration.grid().get(slotX, slotY).ifPresent((entry) ->
				server.execute(() ->
				{
					if(!moduleTag.getString("Key").equals(entry.module().key()))
					{
						DraconicIndustrialization.LOGGER.warn(
								"{} sent a Draco Menu (Post Module Config Changes) packet with a mismatching key value",
								player.getName()
						);
						return;
					}
					
					entry.module().deserialize(moduleTag, menu.getSelectedItem().item());
					itemConfiguration.save();
				}));
	}
}
