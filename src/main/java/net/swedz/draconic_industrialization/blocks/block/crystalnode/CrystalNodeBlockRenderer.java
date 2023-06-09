package net.swedz.draconic_industrialization.blocks.block.crystalnode;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CrystalNodeBlockRenderer extends GeoBlockRenderer<CrystalNodeBlockEntity>
{
	public CrystalNodeBlockRenderer()
	{
		super(new CrystalNodeGeoModel());
	}
	
	@Override
	public void renderEarly(
			CrystalNodeBlockEntity animatable, PoseStack matrices,
			float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer,
			int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha)
	{
		matrices.translate(0, -0.01, 0);
		super.renderEarly(animatable, matrices, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
