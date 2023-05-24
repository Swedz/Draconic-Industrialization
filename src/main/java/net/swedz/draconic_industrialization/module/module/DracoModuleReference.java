package net.swedz.draconic_industrialization.module.module;

import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.util.function.Supplier;

public record DracoModuleReference<M extends DracoModule>(
		String id, String key, DracoTier tier, DracoGridSlotShape gridShape,
		Class<M> reference, DracoModuleCreator<M> creator,
		Supplier<Item> itemSupplier
)
{
	public M create()
	{
		return creator.create(this);
	}
	
	public Item item()
	{
		return itemSupplier.get();
	}
}
