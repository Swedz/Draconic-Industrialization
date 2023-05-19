package net.swedz.draconic_industrialization.module.module.module;

import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;

public final class ColorizerDracoModule extends DracoModule
{
	public DracoColor color;
	
	public ColorizerDracoModule(String key, DracoItem parentItem)
	{
		super(key, parentItem);
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
