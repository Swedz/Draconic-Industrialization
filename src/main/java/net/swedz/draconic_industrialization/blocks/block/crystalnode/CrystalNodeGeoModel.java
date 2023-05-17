package net.swedz.draconic_industrialization.blocks.block.crystalnode;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrystalNodeGeoModel<T extends CrystalNodeAnimatable> extends AnimatedGeoModel<T>
{
	@Override
	public ResourceLocation getModelResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("geo/crystal_node.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("textures/block/crystal_node.png");
	}
	
	@Override
	public ResourceLocation getAnimationResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("animations/crystal_node.animation.json");
	}
}
