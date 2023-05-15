package net.swedz.draconic_industrialization.entity.dragonheart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.items.DIItems;

public final class DragonHeartEntityRenderer extends EntityRenderer<DragonHeartEntity>
{
	private final ItemRenderer itemRenderer;
	
	public DragonHeartEntityRenderer(EntityRendererProvider.Context context)
	{
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}
	
	@Override
	public void render(DragonHeartEntity entity, float entityYaw, float partialTick, PoseStack matrices, MultiBufferSource buffer, int packedLight)
	{
		this.renderHeart(entity, entityYaw, partialTick, matrices, buffer, packedLight);
		
		this.renderGlimmer(entity, entityYaw, partialTick, matrices, buffer, packedLight);
		
		super.render(entity, entityYaw, partialTick, matrices, buffer, packedLight);
	}
	
	private void renderHeart(DragonHeartEntity entity, float entityYaw, float partialTick, PoseStack matrices, MultiBufferSource buffer, int packedLight)
	{
		matrices.pushPose();
		
		ItemStack item = new ItemStack(DIItems.DRAGON_HEART);
		BakedModel model = itemRenderer.getModel(item, entity.level, null, 1);
		matrices.translate(0, 0.45, 0);
		matrices.scale(4, 4, 4);
		matrices.mulPose(Vector3f.YP.rotation(entity.rotation(partialTick)));
		itemRenderer.render(item, ItemTransforms.TransformType.GROUND, false, matrices, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
		
		matrices.popPose();
	}
	
	private void renderGlimmer(DragonHeartEntity entity, float entityYaw, float partialTick, PoseStack matrices, MultiBufferSource buffer, int packedLight)
	{
		matrices.pushPose();
		
		// TODO
		
		matrices.popPose();
	}
	
	@Override
	public ResourceLocation getTextureLocation(DragonHeartEntity entity)
	{
		return null;
	}
}
