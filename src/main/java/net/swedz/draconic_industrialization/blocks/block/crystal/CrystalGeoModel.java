package net.swedz.draconic_industrialization.blocks.block.crystal;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrystalGeoModel<T extends CrystalAnimatable> extends AnimatedGeoModel<T>
{
	@Override
	public ResourceLocation getModelResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("geo/crystal.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("textures/block/crystal.png");
	}
	
	@Override
	public ResourceLocation getAnimationResource(T crystalAnimatable)
	{
		return DraconicIndustrialization.id("animations/crystal.animation.json");
	}
}
