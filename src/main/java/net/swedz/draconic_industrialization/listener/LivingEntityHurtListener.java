package net.swedz.draconic_industrialization.listener;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.swedz.draconic_industrialization.module.module.module.ShieldDracoModule;

public interface LivingEntityHurtListener
{
	/**
	 * Warning!!! This event should only be listened to <b>once</b>!
	 * <br>
	 * This is because there is not a way to define priority orders for any listeners. So it would make it uncertain what the resulting damage would be.
	 * <br>
	 * Just know that this should only ever be used by {@link ShieldDracoModule}
	 */
	Event<LivingEntityHurtListener> BEFORE_HURT = EventFactory.createArrayBacked(LivingEntityHurtListener.class, (callbacks) -> (entity, source, damage) ->
	{
		for(LivingEntityHurtListener callback : callbacks)
		{
			damage = callback.beforeHurt(entity, source, damage);
		}
		return damage;
	});
	
	float beforeHurt(LivingEntity entity, DamageSource source, float damage);
}
