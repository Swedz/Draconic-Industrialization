package net.swedz.draconic_industrialization.blocks.block.crystal;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrystalBlockItem extends BlockItem implements CrystalAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	public CrystalBlockItem(Block block, Item.Properties properties)
	{
		super(block, properties);
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
}
