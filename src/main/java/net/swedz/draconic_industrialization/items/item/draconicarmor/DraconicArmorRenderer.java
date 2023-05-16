package net.swedz.draconic_industrialization.items.item.draconicarmor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public final class DraconicArmorRenderer extends GeoArmorRenderer<DraconicArmorItem>
{
	private static final int MAX_LIGHT = 15728640;
	private static final int HALF_LIGHT = MAX_LIGHT / 2;
	
	public DraconicArmorRenderer()
	{
		super(new DraconicArmorModel());
	}
	
	private int calculateLight(int packedLightIn)
	{
		return (int) (((float) packedLightIn / MAX_LIGHT) * HALF_LIGHT) + HALF_LIGHT;
	}
	
	@Override
	public void render(PoseStack stack, MultiBufferSource bufferIn, int packedLightIn)
	{
		super.render(stack, bufferIn, this.calculateLight(packedLightIn));
	}
}
