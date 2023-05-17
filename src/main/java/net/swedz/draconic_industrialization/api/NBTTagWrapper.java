package net.swedz.draconic_industrialization.api;

import net.minecraft.nbt.CompoundTag;

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
}
