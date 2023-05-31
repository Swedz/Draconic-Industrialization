package net.swedz.draconic_industrialization.module;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;

public final class DracoModuleTick
{
	public final long tick;
	
	public long euCost;
	
	private boolean shouldUpdateItemConfiguration;
	
	public DracoModuleTick(long tick)
	{
		this.tick = tick;
	}
	
	public void apply(ContainerItemContext context, DracoItemConfiguration itemConfiguration, DracoItemEnergy itemEnergy)
	{
		if(euCost > 0)
		{
			itemEnergy.extract(euCost);
			itemEnergy.save();
		}
		
		if(shouldUpdateItemConfiguration)
		{
			itemConfiguration.save();
		}
	}
	
	public void needsItemConfigurationUpdate()
	{
		shouldUpdateItemConfiguration = true;
	}
}
