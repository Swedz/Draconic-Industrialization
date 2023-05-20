package net.swedz.draconic_industrialization.api.nbt;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;

public record NBTTagWrapper(CompoundTag tag)
{
	public NBTTagWrapper()
	{
		this(new CompoundTag());
	}
	
	public NBTTagWrapper getOrEmpty(String key)
	{
		return tag.contains(key) ? new NBTTagWrapper(tag.getCompound(key)) : new NBTTagWrapper();
	}
	
	public void set(String key, CompoundTag value)
	{
		tag.put(key, value);
	}
	
	public void set(String key, NBTTagWrapper value)
	{
		this.set(key, value.tag);
	}
	
	public <E extends Enum<E>> E getEnumOrDefault(Class<E> enumClass, String key, E defaultValue)
	{
		return tag.contains(key) ? Enum.valueOf(enumClass, tag.getString(key)) : defaultValue;
	}
	
	public <E extends Enum<E>> void setEnum(String key, E value)
	{
		tag.putString(key, value.name());
	}
	
	public float getFloatOrDefault(String key, float defaultValue)
	{
		return tag.contains(key) ? tag.getFloat(key) : defaultValue;
	}
	
	public void setFloat(String key, float value)
	{
		tag.putFloat(key, value);
	}
	
	public String getString(String key)
	{
		return tag.getString(key);
	}
	
	public String getStringOrDefault(String key, String defaultValue)
	{
		return tag.contains(key) ? tag.getString(key) : defaultValue;
	}
	
	public void setString(String key, String value)
	{
		tag.putString(key, value);
	}
	
	public List<NBTTagWrapper> getList(String key)
	{
		final List<NBTTagWrapper> tags = Lists.newArrayList();
		if(tag.contains(key))
		{
			ListTag list = tag.getList(key, Tag.TAG_COMPOUND);
			for(int i = 0; i < list.size(); i++)
			{
				tags.add(new NBTTagWrapper(list.getCompound(i)));
			}
		}
		return tags;
	}
	
	public void setList(String key, List<NBTTagWrapper> value)
	{
		ListTag list = new ListTag();
		value.forEach((nbt) -> list.add(nbt.tag()));
		tag.put(key, list);
	}
	
	public int getInt(String key)
	{
		return tag.getInt(key);
	}
	
	public void setInt(String key, int value)
	{
		tag.putInt(key, value);
	}
}
