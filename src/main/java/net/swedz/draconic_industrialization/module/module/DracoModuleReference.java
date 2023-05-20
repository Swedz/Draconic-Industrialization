package net.swedz.draconic_industrialization.module.module;

import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.util.function.Supplier;

public record DracoModuleReference<M extends DracoModule>(
		String key, DracoGridSlotShape gridShape,
		Class<M> reference, DracoModuleCreator creator,
		Supplier<Item> itemSupplier
)
{
	public Item item()
	{
		return itemSupplier.get();
	}
}
