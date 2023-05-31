package net.swedz.draconic_industrialization.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.listener.LivingEntityHurtListener;
import net.swedz.draconic_industrialization.mixinducks.LivingEntityDracoShieldImmunityMixinDuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityHurtListenerMixin extends Entity implements LivingEntityDracoShieldImmunityMixinDuck
{
	public LivingEntityHurtListenerMixin(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}
	
	@Unique
	private long draconicShieldImmunity;
	
	@Unique
	@Override
	public long draconicShieldImmunity()
	{
		return draconicShieldImmunity;
	}
	
	@Unique
	@Override
	public void draconicShieldImmunity(long duration)
	{
		draconicShieldImmunity = duration;
	}
	
	@Inject(
			method = "baseTick",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isDeadOrDying()Z")
	)
	public void baseTick(CallbackInfo callback)
	{
		if(draconicShieldImmunity > 0)
		{
			draconicShieldImmunity--;
		}
	}
	
	@ModifyVariable(
			method = "hurt",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"),
			argsOnly = true
	)
	public float triggerBeforeHurt(float damage, DamageSource source)
	{
		LivingEntity entity = (LivingEntity) (Object) this;
		damage = entity.level.isClientSide ? damage : LivingEntityHurtListener.BEFORE_HURT.invoker().beforeHurt(entity, source, damage);
		damage = Math.max(0, damage);
		return damage;
	}
	
	@Inject(
			method = "hurt",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isSleeping()Z"),
			cancellable = true
	)
	public void afterHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback)
	{
		if(amount <= 0)
		{
			callback.setReturnValue(false);
		}
	}
}
