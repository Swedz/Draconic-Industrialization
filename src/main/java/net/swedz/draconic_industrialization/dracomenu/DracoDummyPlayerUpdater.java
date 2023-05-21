package net.swedz.draconic_industrialization.dracomenu;

public interface DracoDummyPlayerUpdater
{
	DracoDummyPlayerUpdater NOOP = (from, to) ->
	{
	};
	
	void update(DracoItemStack from, DracoItemStack to);
}
