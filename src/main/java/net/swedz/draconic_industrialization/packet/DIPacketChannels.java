package net.swedz.draconic_industrialization.packet;

import com.google.common.collect.Sets;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.packet.ClientboundPacket;
import net.swedz.draconic_industrialization.api.packet.ServerboundPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.InsertModuleDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.PostModuleConfigChangesDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.RequestOpenDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.TakeModuleDracoMenuPacket;

import java.util.Set;
import java.util.function.Supplier;

public final class DIPacketChannels
{
	private static final Set<PacketChannel> CHANNELS = Sets.newHashSet();
	
	public interface Serverbound
	{
		PacketChannel<RequestOpenDracoMenuPacket>             DRACO_MENU_REQUEST_OPEN               = create("draco_menu.request_open", RequestOpenDracoMenuPacket::new);
		PacketChannel<InsertModuleDracoMenuPacket>            DRACO_MENU_INSERT_MODULE              = create("draco_menu.insert_item", InsertModuleDracoMenuPacket::new);
		PacketChannel<TakeModuleDracoMenuPacket>              DRACO_MENU_TAKE_MODULE                = create("draco_menu.take_item", TakeModuleDracoMenuPacket::new);
		PacketChannel<PostModuleConfigChangesDracoMenuPacket> DRACO_MENU_POST_MODULE_CONFIG_CHANGES = create("draco_menu.post_module_config_changes", PostModuleConfigChangesDracoMenuPacket::new);
		
		static <P extends ServerboundPacket> PacketChannel create(String id, Supplier<P> packetHandler)
		{
			PacketChannel channel = new PacketChannel(DraconicIndustrialization.id(id), PacketType.SERVERBOUND, packetHandler);
			CHANNELS.add(channel);
			return channel;
		}
		
		static void init()
		{
			// Load the class
		}
	}
	
	public interface Clientbound
	{
		static <P extends ClientboundPacket> PacketChannel create(String id, Supplier<P> packetHandler)
		{
			PacketChannel channel = new PacketChannel(DraconicIndustrialization.id(id), PacketType.CLIENTBOUND, packetHandler);
			CHANNELS.add(channel);
			return channel;
		}
		
		static void init()
		{
			// Load the class
		}
	}
	
	public static void registerAllListeners(PacketType type)
	{
		type.init();
		CHANNELS.forEach((c) ->
		{
			if(c.type() == type)
			{
				c.registerListener();
			}
		});
	}
}
