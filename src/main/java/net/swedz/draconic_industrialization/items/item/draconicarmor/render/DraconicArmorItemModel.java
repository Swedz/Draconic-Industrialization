package net.swedz.draconic_industrialization.items.item.draconicarmor.render;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorModelType;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public final class DraconicArmorItemModel extends AnimatedGeoModel<DraconicArmorItem>
{
	@Override
	public ResourceLocation getModelResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.DEFAULT.model());
	}
	
	@Override
	public ResourceLocation getTextureResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.DEFAULT.texture(draconicArmorItem.tier()));
	}
	
	@Override
	public ResourceLocation getAnimationResource(DraconicArmorItem draconicArmorItem)
	{
		return DraconicIndustrialization.id(DraconicArmorModelType.DEFAULT.animation());
	}
}
