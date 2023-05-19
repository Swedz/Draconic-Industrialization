package net.swedz.draconic_industrialization;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.blocks.block.crystalnode.CrystalNodeBlockRenderer;
import net.swedz.draconic_industrialization.blocks.block.crystalnode.CrystalNodeGeoModel;
import net.swedz.draconic_industrialization.dracomenu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.DracoScreen;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.keybinds.DIKeybinds;
import net.swedz.draconic_industrialization.particles.DIParticles;
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
		DIItems.initClient();
		
		GeoItemRenderer.registerItemRenderer(DIBlocks.CRYSTAL_NODE.asItem(), new GeoItemRenderer(new CrystalNodeGeoModel()));
		BlockEntityRenderers.register(DIBlocks.CRYSTAL_NODE_ENTITY, (d) -> new CrystalNodeBlockRenderer());
		
		MenuScreens.register(DracoMenu.TYPE, DracoScreen::new);
	}
}
