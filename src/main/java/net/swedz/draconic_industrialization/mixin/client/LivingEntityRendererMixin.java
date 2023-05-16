package net.swedz.draconic_industrialization.mixin.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.swedz.draconic_industrialization.render.plasma.PlasmaLayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M>
{
	protected LivingEntityRendererMixin(EntityRendererProvider.Context context)
	{
		super(context);
	}
	
	@Shadow
	@Final
	protected abstract boolean addLayer(RenderLayer<T, M> feature);
	
	@Inject(method = "<init>", at = @At("TAIL"))
	public void onInit(EntityRendererProvider.Context context, M entityModel, float f, CallbackInfo ci)
	{
		addLayer(new PlasmaLayer<>(this));
	}
}