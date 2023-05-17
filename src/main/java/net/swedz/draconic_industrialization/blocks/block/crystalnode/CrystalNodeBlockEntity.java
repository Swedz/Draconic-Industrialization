package net.swedz.draconic_industrialization.blocks.block.crystalnode;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrystalNodeBlockEntity extends BlockEntity implements CrystalNodeAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	public CrystalNodeBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(DIBlocks.CRYSTAL_NODE_ENTITY, blockPos, blockState);
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
}
