package net.swedz.draconic_industrialization.blocks.block.crystal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrystalBlockEntity extends BlockEntity implements CrystalAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	public CrystalBlockEntity(BlockPos blockPos, BlockState blockState)
	{
		super(DIBlocks.CRYSTAL_ENTITY, blockPos, blockState);
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
}
