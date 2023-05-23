package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.IntegerModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.awt.Color;
import java.util.List;

public final class ColorizerDracoModule extends DracoModule
{
	public DracoColor color;
	
	public ColorizerDracoModule(DracoModuleReference reference)
	{
		super(reference);
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
				.translatable("draco_menu.module.colorizer.color").withStyle(ChatFormatting.DARK_GRAY)
				.append(Component.literal("(%d, %d, %d)".formatted((int) (color.red * 255), (int) (color.green * 255), (int) (color.blue * 255))).withStyle(Style.EMPTY.withColor(color.toRGB()))));
	}
	
	@Override
	public void appendWidgets(DracoMenu menu, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new LabelModuleOptionWidget(
				menu,
				Component.translatable("draco_menu.module.colorizer.config.label"),
				new Color(125, 125, 125, 255).getRGB()
		));
		
		widgets.add(new IntegerModuleOptionWidget(menu,
				Component.translatable("draco_menu.module.colorizer.config.red"), 7,
				0, 255,
				() -> (int) (color.red * 255),
				(value) -> color.red = value / 255f
		));
		widgets.add(new IntegerModuleOptionWidget(menu,
				Component.translatable("draco_menu.module.colorizer.config.green"), 7,
				0, 255,
				() -> (int) (color.green * 255),
				(value) -> color.green = value / 255f
		));
		widgets.add(new IntegerModuleOptionWidget(menu,
				Component.translatable("draco_menu.module.colorizer.config.blue"), 7,
				0, 255,
				() -> (int) (color.blue * 255),
				(value) -> color.blue = value / 255f
		));
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		color = DracoColor.from(item.tier(), tag.getOrEmpty("Color"));
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.set("Color", color.serialize());
	}
}
