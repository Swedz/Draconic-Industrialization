package net.swedz.draconic_industrialization.blocks.block.crystal;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrystalBlockModel extends AnimatedGeoModel<CrystalBlockEntity>
{
	@Override
	public ResourceLocation getModelResource(CrystalBlockEntity crystalBlockEntity)
	{
		return DraconicIndustrialization.id("geo/crystal.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureResource(CrystalBlockEntity crystalBlockEntity)
	{
		return DraconicIndustrialization.id("textures/block/crystal.png");
	}
	
	@Override
	public ResourceLocation getAnimationResource(CrystalBlockEntity crystalBlockEntity)
	{
		return DraconicIndustrialization.id("animations/crystal.animation.json");
	}
}
