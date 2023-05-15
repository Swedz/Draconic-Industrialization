package net.swedz.draconic_industrialization.render.plasma;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.tags.DITags;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static net.minecraft.client.renderer.RenderStateShard.*;

@Environment(EnvType.CLIENT)
public final class PlasmaLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	public PlasmaLayer(RenderLayerParent<T, M> renderLayerParent)
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
		if(!livingEntity.isInvisible())
		{
			Optional<ItemStack> optionalDraconicArmor = StreamSupport.stream(livingEntity.getArmorSlots().spliterator(), false)
					.filter((i) -> i.is(DITags.Items.DRACONIC_ARMOR))
					.findFirst();
			if(optionalDraconicArmor.isPresent())
			{
				float tick = (float) livingEntity.tickCount + partialTick;
				
				final ItemStack draconicArmor = optionalDraconicArmor.get();
				final DraconicArmorItem draconicArmorItem = (DraconicArmorItem) draconicArmor.getItem();
				final float[] rgb = draconicArmorItem.tier().defaultRGB();
				
				EntityModel<T> entityModel = this.getParentModel();
				entityModel.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
				VertexConsumer vertexConsumer = buffer.getBuffer(plasma(
						DraconicIndustrialization.id("textures/entity/plasma_glint.png"),
						Mth.cos(tick * 0.01F) * 0.5F % 1.0F,
						tick * 0.01F * 0.75F % 1.0F
				));
				entityModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
				entityModel.renderToBuffer(
						matrices, vertexConsumer,
						packedLight, OverlayTexture.NO_OVERLAY,
						rgb[0], rgb[1], rgb[2], 1F
				);
			}
		}
	}
	
	public static RenderType plasma(ResourceLocation asset, float x, float y)
	{
		return RenderType.create(
				"enchant_effect",
				DefaultVertexFormat.NEW_ENTITY,
				VertexFormat.Mode.QUADS,
				256,
				false,
				true,
				RenderType.CompositeState.builder()
						.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
						.setTextureState(new RenderStateShard.TextureStateShard(asset, false, false))
						.setTexturingState(new OffsetTexturingStateShard(x, y))
						.setTransparencyState(ADDITIVE_TRANSPARENCY)
						.setCullState(NO_CULL)
						.setDepthTestState(EQUAL_DEPTH_TEST)
						.setLightmapState(LIGHTMAP)
						.setOverlayState(OVERLAY)
						.createCompositeState(false)
		);
	}
}
