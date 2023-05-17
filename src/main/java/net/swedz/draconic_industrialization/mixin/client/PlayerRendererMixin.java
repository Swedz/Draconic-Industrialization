package net.swedz.draconic_industrialization.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmor;
import net.swedz.draconic_industrialization.items.item.draconicarmor.render.shield.ShieldRenderTypes;
import net.swedz.draconic_industrialization.tags.DITags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin
{
	@Inject(
			method = "renderHand",
			at = @At("RETURN")
	)
	private void renderHand(
			PoseStack matrices, MultiBufferSource buffer, int packedLight,
			AbstractClientPlayer player,
			ModelPart rendererArm, ModelPart rendererArmwear,
			CallbackInfo callback
	)
	{
		final ItemStack chestplateItem = player.getItemBySlot(EquipmentSlot.CHEST);
		if(!player.isInvisible() && chestplateItem.is(DITags.Items.DRACONIC_ARMOR))
		{
			final DraconicArmor draconicArmor = DraconicArmor.fromItemStack(chestplateItem);
			final DraconicArmor.Color color = draconicArmor.color;
			
			float partialTick = Minecraft.getInstance().getFrameTime();
			float tick = (float) player.tickCount + partialTick;
			
			final VertexConsumer vertexConsumer = ShieldRenderTypes.getVertexConsumer(draconicArmor.shieldType, buffer, player.tickCount + partialTick);
			rendererArm.render(
					matrices, vertexConsumer,
					packedLight, OverlayTexture.NO_OVERLAY,
					color.red, color.green, color.blue, 1f
			);
			rendererArmwear.render(
					matrices, vertexConsumer,
					packedLight, OverlayTexture.NO_OVERLAY,
					color.red, color.green, color.blue, 1f
			);
		}
	}
}
