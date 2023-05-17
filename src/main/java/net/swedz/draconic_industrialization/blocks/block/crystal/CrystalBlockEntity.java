package net.swedz.draconic_industrialization.blocks.block.crystal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrystalBlockEntity extends BlockEntity implements IAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	public CrystalBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(DIBlocks.CRYSTAL_ENTITY, blockPos, blockState);
	}
	
	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
	{
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation("animation.crystal.orb_spin", ILoopType.EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}
	
	@Override
	public void registerControllers(AnimationData data)
	{
		data.shouldPlayWhilePaused = true;
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
}
