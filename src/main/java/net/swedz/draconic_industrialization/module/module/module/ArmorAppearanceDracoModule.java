package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.EnumModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.awt.Color;
import java.util.List;

public final class ArmorAppearanceDracoModule extends DracoModule
{
	public DraconicArmorModelType  model;
	public DraconicArmorShieldType shield;
	
	public ArmorAppearanceDracoModule(DracoModuleReference reference)
	{
		super(reference);
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return item instanceof DraconicArmorItem;
	}
	
	@Override
	public int max()
	{
		return 1;
	}
	
	@Override
	public void appendTooltip(DracoItem item, List<Component> lines)
	{
		lines.add(Component
				.translatable("draco_menu.module.armor_appearance.model").withStyle(ChatFormatting.DARK_GRAY)
				.append(Component.translatable(model.translationKey()).withStyle(ChatFormatting.WHITE)));
		lines.add(Component
				.translatable("draco_menu.module.armor_appearance.shield").withStyle(ChatFormatting.DARK_GRAY)
				.append(Component.translatable(shield.translationKey()).withStyle(ChatFormatting.WHITE)));
	}
	
	@Override
	public void appendWidgets(DracoMenu menu, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new LabelModuleOptionWidget(
				menu,
				Component.translatable("draco_menu.module.armor_appearance.config.label"),
				new Color(125, 125, 125, 255).getRGB()
		));
		
		widgets.add(new EnumModuleOptionWidget<>(
				menu,
				Component.translatable("draco_menu.module.armor_appearance.config.model"),
				DraconicArmorModelType.class,
				() -> model,
				(value) -> model = value,
				(value) -> Component.translatable(value.translationKey())
		));
		widgets.add(new EnumModuleOptionWidget<>(
				menu,
				Component.translatable("draco_menu.module.armor_appearance.config.shield"),
				DraconicArmorShieldType.class,
				() -> shield,
				(value) -> shield = value,
				(value) -> Component.translatable(value.translationKey())
		));
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		model = tag.getEnumOrDefault(DraconicArmorModelType.class, "Model", DraconicArmorModelType.DEFAULT);
		shield = tag.getEnumOrDefault(DraconicArmorShieldType.class, "Shield", DraconicArmorShieldType.DEFAULT);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.setEnum("Model", model);
		tag.setEnum("Shield", shield);
	}
}
