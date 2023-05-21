package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

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
				.append(Component.literal("(%d, %d, %d)".formatted((int) color.red * 255, (int) color.green * 255, (int) color.blue * 255)).withStyle(Style.EMPTY.withColor(color.toRGB()))));
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
