package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.api.nbt.NBTSerializer;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.util.List;

public abstract class DracoModule implements NBTSerializer<DracoModule>
{
	protected final DracoModuleReference reference;
	
	protected final String             key;
	protected final DracoGridSlotShape gridShape;
	
	protected final DracoItem parentItem;
	
	public DracoModule(DracoModuleReference reference, DracoItem parentItem)
	{
		this.reference = reference;
		this.key = reference.key();
		this.gridShape = reference.gridShape();
		this.parentItem = parentItem;
	}
	
	public DracoModuleReference reference()
	{
		return reference;
	}
	
	public String key()
	{
		return key;
	}
	
	public DracoGridSlotShape gridShape()
	{
		return gridShape;
	}
	
	public DracoItem parentItem()
	{
		return parentItem;
	}
	
	public boolean applies()
	{
		return true;
	}
	
	public int max()
	{
		return Integer.MAX_VALUE;
	}
	
	public abstract void appendTooltip(List<Component> lines);
	
	public List<Component> tooltip()
	{
		List<Component> lines = Lists.newArrayList();
		lines.add(reference.item().getName(reference.item().getDefaultInstance()));
		this.appendTooltip(lines);
		return lines;
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		tag.setString("Key", key);
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.setString("Key", key);
	}
}
