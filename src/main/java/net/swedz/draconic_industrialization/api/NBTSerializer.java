package net.swedz.draconic_industrialization.api;

import net.minecraft.nbt.CompoundTag;

public interface NBTSerializer
{
	void read(NBTTagWrapper tag);
	
	default void deserialize(NBTTagWrapper tag)
	{
		this.read(tag);
	}
	
	default void deserialize(CompoundTag tag)
	{
		this.deserialize(new NBTTagWrapper(tag));
	}
	
	void write(NBTTagWrapper tag);
	
	default CompoundTag serialize()
	{
		NBTTagWrapper wrapper = new NBTTagWrapper();
		this.write(wrapper);
		return wrapper.tag();
	}
}