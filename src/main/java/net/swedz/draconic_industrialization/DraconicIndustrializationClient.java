package net.swedz.draconic_industrialization;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.HeartParticle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.blocks.block.crystalnode.CrystalNodeBlockRenderer;
import net.swedz.draconic_industrialization.blocks.block.crystalnode.CrystalNodeGeoModel;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.main.MainDracoScreen;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.items.item.draconicarmor.render.DraconicArmorItemModel;
import net.swedz.draconic_industrialization.items.item.draconicarmor.render.DraconicArmorRenderer;
import net.swedz.draconic_industrialization.keybinds.DIKeybinds;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.PacketType;
import net.swedz.draconic_industrialization.particles.DIParticles;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@Environment(EnvType.CLIENT)
public final class DraconicIndustrializationClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		DIKeybinds.initClient();
		DIEntities.initClient();
		
		DIParticles.initClient();
		ParticleFactoryRegistry.getInstance().register(DIParticles.HEART_SPARKLE, HeartParticle.Provider::new);
		
		GeoItemRenderer.registerItemRenderer(DIItems.WYVERN_ARMOR, new GeoItemRenderer(new DraconicArmorItemModel()));
		GeoItemRenderer.registerItemRenderer(DIItems.DRACONIC_ARMOR, new GeoItemRenderer(new DraconicArmorItemModel()));
		GeoItemRenderer.registerItemRenderer(DIItems.CHAOTIC_ARMOR, new GeoItemRenderer(new DraconicArmorItemModel()));
		GeoArmorRenderer.registerArmorRenderer(new DraconicArmorRenderer(), DIItems.WYVERN_ARMOR);
		GeoArmorRenderer.registerArmorRenderer(new DraconicArmorRenderer(), DIItems.DRACONIC_ARMOR);
		GeoArmorRenderer.registerArmorRenderer(new DraconicArmorRenderer(), DIItems.CHAOTIC_ARMOR);
		
		GeoItemRenderer.registerItemRenderer(DIBlocks.CRYSTAL_NODE.asItem(), new GeoItemRenderer(new CrystalNodeGeoModel()));
		BlockEntityRenderers.register(DIBlocks.CRYSTAL_NODE_ENTITY, (d) -> new CrystalNodeBlockRenderer());
		
		MenuScreens.register(DracoMenu.TYPE, MainDracoScreen::new);
		
		DIPacketChannels.registerAllListeners(PacketType.CLIENTBOUND);
	}
}
