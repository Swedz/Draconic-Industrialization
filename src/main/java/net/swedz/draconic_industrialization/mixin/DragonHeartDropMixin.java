package net.swedz.draconic_industrialization.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.entity.dragonheart.DragonHeartEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public abstract class DragonHeartDropMixin extends Entity
{
	public DragonHeartDropMixin(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
	}
	
	@Shadow
	public int dragonDeathTime;
	
	@Inject(
			method = "tickDeath",
			at = @At("TAIL")
	)
	private void tickDeath(CallbackInfo callback)
	{
		if(dragonDeathTime == 200)
		{
			DragonHeartEntity entity = DIEntities.DRAGON_HEART.create(level);
			entity.setPos(this.position());
			level.addFreshEntity(entity);
		}
	}
}
