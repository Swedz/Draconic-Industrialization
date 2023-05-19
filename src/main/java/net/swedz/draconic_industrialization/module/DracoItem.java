package net.swedz.draconic_industrialization.module;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.tier.DracoTier;

public interface DracoItem
{
	DracoTier tier();
	
	default DracoItemConfiguration dracoConfiguration(ItemStack itemStack)
	{
		DracoItemConfiguration dracoItemConfiguration = new DracoItemConfiguration(this, itemStack);
		CompoundTag parentTag = itemStack.getOrCreateTag();
		CompoundTag tag = parentTag.contains(DracoItemConfiguration.PARENT_KEY) ? parentTag.getCompound(DracoItemConfiguration.PARENT_KEY) : new CompoundTag();
		return dracoItemConfiguration.deserialize(tag);
	}
}
