package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public final class DraconicArmorModel extends AnimatedGeoModel<DraconicArmorItem>
{
	@Override
	public ResourceLocation getModelResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id("geo/draconic_armor.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(draconicArmorItem.tier().asset());
	}
	
	@Override
	public ResourceLocation getAnimationResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id("animations/draconic_armor.animation.json");
	}
}
