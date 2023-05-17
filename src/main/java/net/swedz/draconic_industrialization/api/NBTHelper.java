package net.swedz.draconic_industrialization.api;

import net.minecraft.nbt.CompoundTag;

public final class NBTHelper
{
	public static String getStringOrDefault(CompoundTag tag, String key, String defaultString)
	{
		return tag.contains(key) ? tag.getString(key) : defaultString;
	}
	
	public static CompoundTag getTagOrEmpty(CompoundTag tag, String key)
	{
		return tag.contains(key) ? tag.getCompound(key) : new CompoundTag();
	}
}
