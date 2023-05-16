package net.swedz.draconic_industrialization.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.render.plasma.PlasmaLayer;
import net.swedz.draconic_industrialization.tags.DITags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.stream.StreamSupport;

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
		if(!player.isInvisible())
		{
			Optional<ItemStack> optionalDraconicArmor = StreamSupport.stream(player.getArmorSlots().spliterator(), false)
					.filter((i) -> i.is(DITags.Items.DRACONIC_ARMOR))
					.findFirst();
			if(optionalDraconicArmor.isPresent())
			{
				float partialTick = Minecraft.getInstance().getFrameTime();
				float tick = (float) player.tickCount + partialTick;
				
				final ItemStack draconicArmor = optionalDraconicArmor.get();
				final DraconicArmorItem draconicArmorItem = (DraconicArmorItem) draconicArmor.getItem();
				final float[] rgb = draconicArmorItem.tier().defaultRGB();
				
				final VertexConsumer vertexConsumer = PlasmaLayer.plasma(buffer, player.tickCount + partialTick);
				rendererArm.render(
						matrices, vertexConsumer,
						packedLight, OverlayTexture.NO_OVERLAY,
						rgb[0], rgb[1], rgb[2], 1
				);
				rendererArmwear.render(
						matrices, vertexConsumer,
						packedLight, OverlayTexture.NO_OVERLAY,
						rgb[0], rgb[1], rgb[2], 1
				);
			}
		}
	}
}
