package net.swedz.draconic_industrialization.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.swedz.draconic_industrialization.attributes.DIAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntityAttributeStuffMixin
{
	@Inject(
			method = "createLivingAttributes",
			at = @At("RETURN")
	)
	private static void createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> callback)
	{
		callback.getReturnValue()
				.add(DIAttributes.JUMP_HEIGHT);
	}
	
	@Shadow
	public abstract double getAttributeValue(Attribute attribute);
	
	@ModifyReturnValue(
			method = "getJumpPower",
			at = @At("RETURN")
	)
	protected float getJumpPower(float originalValue)
	{
		return originalValue * (float) this.getAttributeValue(DIAttributes.JUMP_HEIGHT);
	}
}
