package net.swedz.draconic_industrialization.blocks.block.crystal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CrystalBlockRenderer extends GeoBlockRenderer<CrystalBlockEntity>
{
	public CrystalBlockRenderer()
	{
		super(new CrystalBlockModel());
	}
	
	@Override
	public void renderEarly(
			CrystalBlockEntity animatable, PoseStack matrices,
			float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer,
			int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha)
	{
		matrices.translate(0, -0.01, 0);
		super.renderEarly(animatable, matrices, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
