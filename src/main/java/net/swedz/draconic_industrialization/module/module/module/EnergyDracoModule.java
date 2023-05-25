package net.swedz.draconic_industrialization.module.module.module;

import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

public final class EnergyDracoModule extends DracoModule
{
	private final long capacity, io;
	
	public EnergyDracoModule(DracoModuleReference reference, long capacity, long io)
	{
		super(reference);
		this.capacity = capacity;
		this.io = io;
	}
	
	public long capacity()
	{
		return capacity;
	}
	
	public long io()
	{
		return io;
	}
	
	@Override
	public boolean hasStuffToConfigure()
	{
		return false;
	}
}
