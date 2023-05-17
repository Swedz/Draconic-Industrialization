package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.minecraft.nbt.CompoundTag;
import net.swedz.draconic_industrialization.api.NBTHelper;

public final class DraconicArmorItemData
{
	private static final String MODEL_TYPE_TAG = "ModelType";
	
	public final DraconicArmorModelType modelType;
	
	DraconicArmorItemData(CompoundTag tag)
	{
		this.modelType = DraconicArmorModelType.valueOf(NBTHelper.getStringOrDefault(tag, MODEL_TYPE_TAG, "STRAP"));
	}
}
