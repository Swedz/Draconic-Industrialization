package net.swedz.draconic_industrialization.module.module;

import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

public record DracoModuleReference<M extends DracoModule>(
		String key, DracoGridSlotShape gridShape,
		Class<M> reference, DracoModuleCreator creator
)
{
}
