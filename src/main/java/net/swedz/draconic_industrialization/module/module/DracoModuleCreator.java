package net.swedz.draconic_industrialization.module.module;

import net.swedz.draconic_industrialization.module.DracoItem;

public interface DracoModuleCreator<M extends DracoModule>
{
	M create(DracoModuleReference<M> reference, DracoItem parentItem);
}
