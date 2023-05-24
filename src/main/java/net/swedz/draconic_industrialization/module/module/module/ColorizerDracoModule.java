package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.HexFromClipboardModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.IntegerModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.LabelModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.SpacingModuleOptionWidget;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.TopStuffsModuleOptionWidget;
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
				.translatable("draco_menu.module.colorizer.attribute.color").withStyle(DracoMenuStylesheet.CONTENT)
				.append(Component.literal("(%d, %d, %d)".formatted((int) (color.red * 255), (int) (color.green * 255), (int) (color.blue * 255))).withStyle(Style.EMPTY.withColor(color.toRGB()))));
	}
	
	@Override
	public void appendWidgets(DracoScreen screen, List<ModuleOptionWidget> widgets)
	{
		widgets.add(new TopStuffsModuleOptionWidget(screen, List.of(
				this.title().withStyle(DracoMenuStylesheet.HEADER),
				Component.translatable("draco_menu.module.colorizer.config.info.0").withStyle(DracoMenuStylesheet.CONTENT),
				Component.translatable("draco_menu.module.colorizer.config.info.1").withStyle(DracoMenuStylesheet.CONTENT)
		)));
		
		widgets.add(new LabelModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.colorizer.config.label"),
				DracoMenuStylesheet.COLOR_HEADER.getRGB()
		));
		
		widgets.add(new IntegerModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.colorizer.config.red"), 7,
				0, 255, 16,
				() -> (int) (color.red * 255),
				(value) -> color.red = value / 255f
		));
		widgets.add(new IntegerModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.colorizer.config.green"), 7,
				0, 255, 16,
				() -> (int) (color.green * 255),
				(value) -> color.green = value / 255f
		));
		widgets.add(new IntegerModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.colorizer.config.blue"), 7,
				0, 255, 16,
				() -> (int) (color.blue * 255),
				(value) -> color.blue = value / 255f
		));
		
		widgets.add(new SpacingModuleOptionWidget(screen, 5));
		
		widgets.add(new HexFromClipboardModuleOptionWidget(
				screen,
				Component.translatable("draco_menu.module.colorizer.config.hex"), 7,
				() -> Integer.toHexString(color.toRGB()).substring(2).toUpperCase(),
				(value) ->
				{
					Color c = Color.decode("#" + value);
					color.red = c.getRed() / 255f;
					color.green = c.getGreen() / 255f;
					color.blue = c.getBlue() / 255f;
				}
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
