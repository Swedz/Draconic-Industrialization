package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.EnumModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.TopStuffsModuleOptionWidget;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

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
				.translatable("draco_menu.module.armor_appearance.model").withStyle(DracoMenuStylesheet.CONTENT)
				.append(Component.translatable(model.translationKey())));
		lines.add(Component
				.translatable("draco_menu.module.armor_appearance.shield").withStyle(DracoMenuStylesheet.CONTENT)
				.append(Component.translatable(shield.translationKey())));
	}
	
	@Override
	public void appendWidgets(DracoScreen screen, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new TopStuffsModuleOptionWidget(screen, List.of(
				this.title().withStyle(DracoMenuStylesheet.HEADER),
				Component.translatable("draco_menu.module.armor_appearance.config.info.0").withStyle(DracoMenuStylesheet.CONTENT),
				Component.translatable("draco_menu.module.armor_appearance.config.info.1").withStyle(DracoMenuStylesheet.CONTENT),
				Component.translatable("draco_menu.module.armor_appearance.config.info.2").withStyle(DracoMenuStylesheet.CONTENT)
		)));
		
		widgets.add(new LabelModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.armor_appearance.config.label"),
				DracoMenuStylesheet.COLOR_HEADER.getRGB()
		));
		
		widgets.add(new EnumModuleOptionWidget<>(
				screen,
				Component.translatable("draco_menu.module.armor_appearance.config.model"),
				DraconicArmorModelType.class,
				() -> model,
				(value) -> model = value,
				(value) -> Component.translatable(value.translationKey())
		));
		widgets.add(new EnumModuleOptionWidget<>(
				screen,
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
