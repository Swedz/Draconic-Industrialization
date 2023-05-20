package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.util.List;

public final class ColorizerDracoModule extends DracoModule
{
	public DracoColor color;
	
	public ColorizerDracoModule(DracoModuleReference reference, DracoItem parentItem)
	{
		super(reference, parentItem);
	}
	
	@Override
	public int max()
	{
		return 1;
	}
	
	@Override
	public void appendTooltip(List<Component> lines)
	{
		// TODO
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		super.read(tag);
		color = DracoColor.from(parentItem.tier(), tag.getOrEmpty("Color"));
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		super.write(tag);
		tag.set("Color", color.serialize());
	}
}
