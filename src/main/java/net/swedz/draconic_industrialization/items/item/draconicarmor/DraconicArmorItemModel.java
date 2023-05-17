package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public final class DraconicArmorItemModel extends AnimatedGeoModel<DraconicArmorItem>
{
	@Override
	public ResourceLocation getModelResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.STRAP.model());
	}
	
	@Override
	public ResourceLocation getTextureResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.STRAP.texture(draconicArmorItem.tier()));
	}
	
	@Override
	public ResourceLocation getAnimationResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.STRAP.animation());
	}
}
