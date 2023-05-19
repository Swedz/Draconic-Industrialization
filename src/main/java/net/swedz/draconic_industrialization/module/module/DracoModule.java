package net.swedz.draconic_industrialization.module.module;

import net.swedz.draconic_industrialization.api.NBTSerializer;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;

public abstract class DracoModule implements NBTSerializer<DracoModule>
{
	protected final String key;
	
	protected final DracoItem parentItem;
	
	public DracoModule(String key, DracoItem parentItem)
	{
		this.key = key;
		this.parentItem = parentItem;
	}
	
	public String getKey()
	{
		return key;
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
