package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.swedz.draconic_industrialization.api.attributes.AccumulatedAttributeWrappers;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.IntegerModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.TopStuffsModuleOptionWidget;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.util.List;
import java.util.UUID;

public final class JumpDracoModule extends PercentageBasedDracoModule
{
	private static final UUID ATTRIBUTE_ID = UUID.fromString("2825593e-fa27-11ed-be56-0242ac120002");
	
	public JumpDracoModule(DracoModuleReference reference, double maxSpeedPercentage)
	{
		super(reference, 5, maxSpeedPercentage);
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return super.applies(item) &&
				item instanceof DraconicArmorItem;
	}
	
	@Override
	public void appendTooltip(DracoItem item, List<Component> lines)
	{
		lines.add(Component
				.translatable("draco_menu.module.jump_amplifier.attribute.boost").withStyle(DracoMenuStylesheet.CONTENT)
				.append(Component.literal(this.bonusString(value))));
	}
	
	@Override
	public void appendWidgets(DracoScreen screen, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new TopStuffsModuleOptionWidget(screen, List.of(
				this.title().withStyle(DracoMenuStylesheet.HEADER),
				Component.translatable("draco_menu.module.jump_amplifier.config.info.0").withStyle(DracoMenuStylesheet.CONTENT)
		)));
		
		widgets.add(new LabelModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.jump_amplifier.config.label"),
				DracoMenuStylesheet.COLOR_HEADER.getRGB()
		));
		
		widgets.add(new IntegerModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.jump_amplifier.config.boost"), 7,
				0, maxValue, 1,
				() -> value,
				(value) -> this.value = value,
				this::bonusString
		));
	}
	
	@Override
	public void applyAttributes(AccumulatedAttributeWrappers attributes, DracoItem item)
	{
		attributes.add(
				Attributes.JUMP_STRENGTH, // FIXME uhhhhh this is horse jump strength... not player jump strength...
				ATTRIBUTE_ID, "DracoArmor.Jump",
				AttributeModifier.Operation.MULTIPLY_BASE,
				EquipmentSlot.CHEST,
				this.bonus()
		);
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		value = tag.getIntOrDefault("boost", maxValue);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.setInt("boost", value);
	}
}
