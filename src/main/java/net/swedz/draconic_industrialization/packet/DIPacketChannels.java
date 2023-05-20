package net.swedz.draconic_industrialization.packet;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public final class DIPacketChannels
{
	public static final class ClientToServer
	{
		public static final ResourceLocation DRACO_MENU_REQUEST_OPEN = DraconicIndustrialization.id("draco_menu.request_open");
		
		public static final ResourceLocation DRACO_MENU_INSERT_ITEM = DraconicIndustrialization.id("draco_menu.insert_item");
		public static final ResourceLocation DRACO_MENU_TAKE_ITEM = DraconicIndustrialization.id("draco_menu.take_item");
	}
}
