package net.swedz.draconic_industrialization.items.item.draconicarmor;

import aztech.modern_industrialization.util.TextHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSize;
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
import team.reborn.energy.api.EnergyStorage;

import java.awt.Color;
import java.util.List;

public final class DraconicArmorItem extends GeckoArmorItem implements IAnimatable, DracoItem
{
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
	
	private final DracoTier tier;
	
	public DraconicArmorItem(DracoTier tier, Properties properties)
	{
		super(new DraconicArmorMaterial(), EquipmentSlot.CHEST, properties);
		this.tier = tier;
	}
	
	@Override
	public DracoTier tier()
	{
		return tier;
	}
	
	@Override
	public DracoGridSize gridSize()
	{
		return switch (tier)
				{
					case WYVERN -> DracoGridSize.of(4, 3); // 12
					case DRACONIC -> DracoGridSize.of(5, 5); // 25 (+13)
					case CHAOTIC -> DracoGridSize.of(7, 6); // 42 (+17)
				};
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
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return factory;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot)
	{
		return ImmutableMultimap.of();
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag)
	{
		final EnergyStorage energyStorage = this.dracoConfiguration(stack).energyStorage();
		tooltip.add(TextHelper
				.getEuTextMaxed(energyStorage.getAmount(), energyStorage.getCapacity())
				.withStyle(Style.EMPTY.withColor(this.getBarColor(stack)).withItalic(false)));
	}
	
	@Override
	public boolean isBarVisible(ItemStack stack)
	{
		return !(Minecraft.getInstance().screen instanceof DracoScreen screen) ||
				!screen.getMenu().hasSelectedItem() ||
				screen.getMenu().getSelectedItem().stack() != stack;
	}
	
	@Override
	public int getBarWidth(ItemStack stack)
	{
		final DracoItemConfiguration itemConfiguration = this.dracoConfiguration(stack);
		final EnergyStorage energyStorage = itemConfiguration.energyStorage();
		return (int) Math.round(energyStorage.getAmount() / (double) energyStorage.getCapacity() * 13);
	}
	
	@Override
	public int getBarColor(ItemStack stack)
	{
		return new Color(255, 216, 0, 255).getRGB();
	}
}
