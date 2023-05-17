package net.swedz.draconic_industrialization.items.item.draconicarmor.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.NBTSerializer;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;

public final class DraconicArmor implements NBTSerializer
{
	private static final String PARENT_KEY = "DraconicArmorData";
	
	public DraconicArmorModelType modelType;
	
	private DraconicArmor()
	{
	}
	
	public static DraconicArmor fromItemStack(ItemStack itemStack)
	{
		DraconicArmor draconicArmor = new DraconicArmor();
		CompoundTag parentTag = itemStack.getOrCreateTag();
		CompoundTag tag = parentTag.contains(PARENT_KEY) ? parentTag.getCompound(PARENT_KEY) : new CompoundTag();
		draconicArmor.deserialize(tag);
		return draconicArmor;
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		modelType = tag.getEnumOrDefault(DraconicArmorModelType.class, "ModelType", DraconicArmorModelType.STRAP);
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.setEnum("ModelType", modelType);
	}
}
