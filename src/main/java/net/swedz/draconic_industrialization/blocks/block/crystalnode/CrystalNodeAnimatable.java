package net.swedz.draconic_industrialization.blocks.block.crystalnode;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public interface CrystalNodeAnimatable extends IAnimatable
{
	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
	{
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation("animation.crystal_node.orb_spin", ILoopType.EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}
	
	@Override
	default void registerControllers(AnimationData data)
	{
		data.shouldPlayWhilePaused = true;
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}
}
