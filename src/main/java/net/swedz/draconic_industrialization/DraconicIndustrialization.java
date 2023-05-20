package net.swedz.draconic_industrialization;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.dracomenu.DracoMenu;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.particles.DIParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DraconicIndustrialization implements ModInitializer
{
	public static final String ID = "draconic_industrialization";
	
	public static ResourceLocation id(String name)
	{
		return new ResourceLocation(ID, name);
	}
	
	public static final Logger LOGGER = LoggerFactory.getLogger("Draconic Industrialization");
	
	public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.build(
			DraconicIndustrialization.id("draconic_industrialization"),
			() -> new ItemStack(Registry.ITEM.get(id("awakened_draconium_circuit")))
	);
	
	public static void loadClasses()
	{
		DIItems.init();
		DIBlocks.init();
		DIEntities.init();
		DIParticles.init();
		
		ServerPlayNetworking.registerGlobalReceiver(DIPacketChannels.ClientToServer.REQUEST_DRACO_MENU, (server, player, handler, buf, responseSender) ->
				player.openMenu(new SimpleMenuProvider(
						DracoMenu::new,
						Component.translatable("screen.draconic_industrialization.draco")
				)));
	}
	
	@Override
	public void onInitialize()
	{
		loadClasses();
	}
}
