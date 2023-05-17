package net.swedz.draconic_industrialization.items.item.draconicarmor.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmor;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public final class DraconicArmorRenderer extends GeoArmorRenderer<DraconicArmorItem>
{
	private static final int MAX_LIGHT  = 15728640;
	private static final int HALF_LIGHT = MAX_LIGHT / 2;
	
	public DraconicArmorRenderer()
	{
		super(null);
		this.setModel(this.new Model());
	}
	
	private int calculateLight(int packedLightIn)
	{
		return (int) (((float) packedLightIn / MAX_LIGHT) * HALF_LIGHT) + HALF_LIGHT;
	}
	
	@Override
	public void render(PoseStack stack, MultiBufferSource bufferIn, int packedLightIn)
	{
		final DraconicArmor data = DraconicArmor.fromItemStack(itemStack);
		if(data.modelType.shouldRender())
		{
			super.render(stack, bufferIn, this.calculateLight(packedLightIn));
		}
	}
	
	private final class Model extends AnimatedGeoModel<DraconicArmorItem>
	{
		@Override
		public ResourceLocation getModelResource(DraconicArmorItem draconicArmorItem)
		{
			final ItemStack itemStack = DraconicArmorRenderer.this.itemStack;
			final DraconicArmor data = DraconicArmor.fromItemStack(itemStack);
			return DraconicIndustrialization.id(data.modelType.model());
		}
		
		@Override
		public ResourceLocation getTextureResource(DraconicArmorItem draconicArmorItem)
		{
			final ItemStack itemStack = DraconicArmorRenderer.this.itemStack;
			final DraconicArmor data = DraconicArmor.fromItemStack(itemStack);
			return DraconicIndustrialization.id(data.modelType.texture(draconicArmorItem.tier()));
		}
		
		@Override
		public ResourceLocation getAnimationResource(DraconicArmorItem draconicArmorItem)
		{
			final ItemStack itemStack = DraconicArmorRenderer.this.itemStack;
			final DraconicArmor data = DraconicArmor.fromItemStack(itemStack);
			return DraconicIndustrialization.id(data.modelType.animation());
		}
	}
}
