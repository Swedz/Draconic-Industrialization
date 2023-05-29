package net.swedz.draconic_industrialization.module;

import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrializationCardinalComponents;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSize;

public interface DracoItem
{
	DracoTier tier();
	
	DracoGridSize gridSize();
	
	default DracoItemConfiguration dracoConfiguration(ItemStack itemStack)
	{
		return DraconicIndustrializationCardinalComponents.DRACO.get(itemStack);
	}
	
	default DracoItemEnergy dracoEnergy(ItemStack itemStack)
	{
		return DraconicIndustrializationCardinalComponents.ENERGY.get(itemStack);
	}
}
