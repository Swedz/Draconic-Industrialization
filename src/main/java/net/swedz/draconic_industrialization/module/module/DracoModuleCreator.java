package net.swedz.draconic_industrialization.module.module;

import net.swedz.draconic_industrialization.module.DracoItem;

public interface DracoModuleCreator<M extends DracoModule>
{
	M create(String key, DracoItem parentItem);
}
