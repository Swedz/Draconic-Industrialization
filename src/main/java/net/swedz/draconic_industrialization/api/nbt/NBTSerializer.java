package net.swedz.draconic_industrialization.api.nbt;

import net.minecraft.nbt.CompoundTag;

public interface NBTSerializer<T extends NBTSerializer<T>>
{
	void read(NBTTagWrapper tag);
	
	default T deserialize(NBTTagWrapper tag)
	{
		this.read(tag);
		return (T) this;
	}
	
	default T deserialize(CompoundTag tag)
	{
		this.deserialize(new NBTTagWrapper(tag));
		return (T) this;
	}
	
	void write(NBTTagWrapper tag);
	
	default CompoundTag serialize()
	{
		NBTTagWrapper wrapper = new NBTTagWrapper();
		this.write(wrapper);
		return wrapper.tag();
	}
}
