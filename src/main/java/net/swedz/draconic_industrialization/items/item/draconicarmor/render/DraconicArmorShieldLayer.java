package net.swedz.draconic_industrialization.items.item.draconicarmor.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmor;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.tags.DITags;

import static net.minecraft.client.renderer.RenderStateShard.*;

@Environment(EnvType.CLIENT)
public final class DraconicArmorShieldLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	public DraconicArmorShieldLayer(RenderLayerParent<T, M> renderLayerParent)
	{
		super(renderLayerParent);
	}
	
	@Override
	public void render(
			PoseStack matrices, MultiBufferSource buffer, int packedLight,
			T livingEntity, float limbSwing, float limbSwingAmount,
			float partialTick, float ageInTicks,
			float netHeadYaw, float headPitch
	)
	{
		final ItemStack chestplateItem = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
		if(!livingEntity.isInvisible() && chestplateItem.is(DITags.Items.DRACONIC_ARMOR))
		{
			final DraconicArmor draconicArmor = DraconicArmor.fromItemStack(chestplateItem);
			final DraconicArmor.Color color = draconicArmor.color;
			
			float tick = (float) livingEntity.tickCount + partialTick;
			
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
			VertexConsumer vertexConsumer = get(buffer, tick);
			entityModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			entityModel.renderToBuffer(
					matrices, vertexConsumer,
					packedLight, OverlayTexture.NO_OVERLAY,
					color.red, color.green, color.blue, 1f
			);
		}
	}
	
	// TODO make the asset and animation dynamic based on the shield type of the armor
	//  we need to do this in a way where this class isn't loaded server-side
	
	public static VertexConsumer get(MultiBufferSource buffer, float tick)
	{
		float speed = 0.005f;
		return buffer.getBuffer(renderType(
				Mth.cos(tick * speed) * 0.5F % 1.0F,
				tick * speed * 0.75F % 1.0F
		));
	}
	
	private static RenderType renderType(float x, float y)
	{
		return RenderType.create(
				"plasma_shield",
				DefaultVertexFormat.NEW_ENTITY,
				VertexFormat.Mode.QUADS,
				256,
				false,
				true,
				RenderType.CompositeState.builder()
						.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
						.setTextureState(new TextureStateShard(DraconicIndustrialization.id(DraconicArmorShieldType.DEFAULT.texture()), false, false))
						.setTexturingState(new OffsetTexturingStateShard(x, y))
						.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
						.setCullState(NO_CULL)
						.setLightmapState(LIGHTMAP)
						.setOverlayState(OVERLAY)
						.createCompositeState(false)
		);
	}
}
