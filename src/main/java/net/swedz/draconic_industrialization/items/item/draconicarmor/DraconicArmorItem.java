package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import software.bernie.example.item.GeckoArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public final class DraconicArmorItem extends GeckoArmorItem implements IAnimatable
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	private final DraconicArmorTier tier;
	
	public DraconicArmorItem(DraconicArmorTier tier, Properties properties)
	{
		super(new DraconicArmorMaterial(), EquipmentSlot.CHEST, properties);
		this.tier = tier;
	}
	
	public DraconicArmorTier tier()
	{
		return tier;
	}
	
	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
	{
		event.getController().setAnimation(new AnimationBuilder()
				.addAnimation("animation.draconic_armor.orb_spin", ILoopType.EDefaultLoopTypes.LOOP));
		return PlayState.CONTINUE;
	}
	
	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
}
