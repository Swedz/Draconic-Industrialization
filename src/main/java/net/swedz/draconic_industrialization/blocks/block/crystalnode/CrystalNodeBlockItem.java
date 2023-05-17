package net.swedz.draconic_industrialization.blocks.block.crystalnode;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrystalNodeBlockItem extends BlockItem implements CrystalNodeAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	public CrystalNodeBlockItem(Block block, Item.Properties properties)
	{
		super(block, properties);
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
}
