package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.swedz.draconic_industrialization.api.attributes.AccumulatedAttributeWrappers;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.IntegerModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.TopStuffsModuleOptionWidget;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.util.List;
import java.util.UUID;

public final class SpeedDracoModule extends DracoModule
{
	private static final UUID ATTRIBUTE_ID = UUID.fromString("99a5b4c6-fa1f-11ed-be56-0242ac120002");
	
	private static final double FRACTION = 20;
	private static final double FRACTION_MULTIPLE = 100 / FRACTION;
	
	private final DracoTier tier;
	private final int       maxSpeed;
	
	private int speed;
	
	public SpeedDracoModule(DracoModuleReference reference, DracoTier tier, double maxSpeedPercentage)
	{
		super(reference);
		this.tier = tier;
		if(maxSpeedPercentage % FRACTION_MULTIPLE != 0)
		{
			throw new IllegalArgumentException("Speed percentage must be multiple of %f, was given %f instead".formatted(FRACTION_MULTIPLE, maxSpeedPercentage));
		}
		this.maxSpeed = (int) Math.ceil(maxSpeedPercentage / 100D * FRACTION);
	}
	
	private double speedBonus()
	{
		return speed / FRACTION;
	}
	
	private int speedPercentage()
	{
		return (int) (this.speedBonus() * 100);
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return item instanceof DraconicArmorItem && item.tier().greaterThanOrEqualTo(tier);
	}
	
	@Override
	public int max()
	{
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void appendTooltip(DracoItem item, List<Component> lines)
	{
		lines.add(Component
				.translatable("draco_menu.module.speed_amplifier.attribute.speed").withStyle(DracoMenuStylesheet.CONTENT)
				.append(Component.literal("+%d%%".formatted(this.speedPercentage()))));
	}
	
	@Override
	public void appendWidgets(DracoScreen screen, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new TopStuffsModuleOptionWidget(screen, List.of(
				this.title().withStyle(DracoMenuStylesheet.HEADER),
				Component.translatable("draco_menu.module.speed_amplifier.config.info.0").withStyle(DracoMenuStylesheet.CONTENT),
				Component.translatable("draco_menu.module.speed_amplifier.config.info.1").withStyle(DracoMenuStylesheet.CONTENT)
		)));
		
		widgets.add(new LabelModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.speed_amplifier.config.label"),
				DracoMenuStylesheet.COLOR_HEADER.getRGB()
		));
		
		widgets.add(new IntegerModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.speed_amplifier.config.speed"), 7,
				0, maxSpeed, 1,
				() -> speed,
				(value) -> speed = value,
				(value) -> "+%d%%".formatted((int) (value / FRACTION * 100))
		));
	}
	
	@Override
	public void applyAttributes(AccumulatedAttributeWrappers attributes, DracoItem item)
	{
		attributes.add(
				Attributes.MOVEMENT_SPEED,
				ATTRIBUTE_ID, "DracoArmor.Speed",
				AttributeModifier.Operation.MULTIPLY_BASE,
				EquipmentSlot.CHEST,
				this.speedBonus()
		);
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		speed = tag.getIntOrDefault("speed", maxSpeed);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.setInt("speed", speed);
	}
}
