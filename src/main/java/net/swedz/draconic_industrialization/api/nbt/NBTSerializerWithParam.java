package net.swedz.draconic_industrialization.api.nbt;

import net.minecraft.nbt.CompoundTag;

public interface NBTSerializerWithParam<T extends NBTSerializerWithParam<T, A>, A>
{
	void read(NBTTagWrapper tag, A param);
	
	default T deserialize(NBTTagWrapper tag, A param)
	{
		this.read(tag, param);
		return (T) this;
	}
	
	default T deserialize(CompoundTag tag, A param)
	{
		this.deserialize(new NBTTagWrapper(tag), param);
		return (T) this;
	}
	
	void write(NBTTagWrapper tag, A param);
	
	default CompoundTag serialize(A param)
	{
		NBTTagWrapper wrapper = new NBTTagWrapper();
		this.write(wrapper, param);
		return wrapper.tag();
	}
}
