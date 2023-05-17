package net.swedz.draconic_industrialization;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.blocks.block.crystal.CrystalGeoModel;
import net.swedz.draconic_industrialization.blocks.block.crystal.CrystalBlockRenderer;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModel;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorRenderer;
import net.swedz.draconic_industrialization.particles.DIParticles;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

@Environment(EnvType.CLIENT)
public final class DraconicIndustrializationClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		DIEntities.initializeClient();
		DIParticles.initializeClient();
		
		GeoItemRenderer.registerItemRenderer(DIItems.WYVERN_ARMOR, new GeoItemRenderer(new DraconicArmorModel()));
		GeoItemRenderer.registerItemRenderer(DIItems.DRACONIC_ARMOR, new GeoItemRenderer(new DraconicArmorModel()));
		GeoItemRenderer.registerItemRenderer(DIItems.CHAOTIC_ARMOR, new GeoItemRenderer(new DraconicArmorModel()));
		GeoArmorRenderer.registerArmorRenderer(new DraconicArmorRenderer(), DIItems.WYVERN_ARMOR, DIItems.DRACONIC_ARMOR, DIItems.CHAOTIC_ARMOR);
		
		GeoItemRenderer.registerItemRenderer(DIBlocks.CRYSTAL.asItem(), new GeoItemRenderer(new CrystalGeoModel()));
		BlockEntityRenderers.register(DIBlocks.CRYSTAL_ENTITY, (d) -> new CrystalBlockRenderer());
	}
}
