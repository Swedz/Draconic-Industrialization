package net.swedz.draconic_industrialization;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
		DIEntities.initClient();
		DIParticles.initClient();
		DIItems.initClient();
		
		//GeoItemRenderer.registerItemRenderer(DIBlocks.CRYSTAL_NODE.asItem(), new GeoItemRenderer(new CrystalNodeGeoModel()));
		//BlockEntityRenderers.register(DIBlocks.CRYSTAL_NODE_ENTITY, (d) -> new CrystalNodeBlockRenderer());
	}
}
