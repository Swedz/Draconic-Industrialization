package net.swedz.draconic_industrialization.module.module;

import net.swedz.draconic_industrialization.api.nbt.NBTSerializer;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

public abstract class DracoModule implements NBTSerializer<DracoModule>
{
	protected final String             key;
	protected final DracoGridSlotShape gridShape;
	
	protected final DracoItem parentItem;
	
	public DracoModule(DracoModuleReference reference, DracoItem parentItem)
	{
		this.key = reference.key();
		this.gridShape = reference.gridShape();
		this.parentItem = parentItem;
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
