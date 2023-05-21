package net.swedz.draconic_industrialization.module.module;

public interface DracoModuleCreator<M extends DracoModule>
{
	M create(DracoModuleReference<M> reference);
}
