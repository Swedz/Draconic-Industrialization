package net.swedz.draconic_industrialization.items.item.draconicarmor.render.shield;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.DracoModules;

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
		if(!livingEntity.isInvisible() && chestplateItem.getItem() instanceof DraconicArmorItem armorItem)
		{
			final DracoItemConfiguration configuration = armorItem.dracoConfiguration(chestplateItem);
			final DracoColor color = configuration.getModuleOrCreate(DracoModules.COLORIZER).color;
			final DraconicArmorShieldType shieldType = configuration.getModuleOrCreate(DracoModules.ARMOR_APPERANCE).shield;
			
			float tick = (float) livingEntity.tickCount + partialTick;
			
			EntityModel<T> entityModel = this.getParentModel();
			entityModel.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTick);
			VertexConsumer vertexConsumer = ShieldRenderTypes.getVertexConsumer(shieldType, buffer, tick);
			entityModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			entityModel.renderToBuffer(
					matrices, vertexConsumer,
					packedLight, OverlayTexture.NO_OVERLAY,
					color.red, color.green, color.blue, 1f
			);
		}
	}
}
