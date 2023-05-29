package net.swedz.draconic_industrialization.module;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;

public final class DracoModuleTick
{
	public long euCost;
	
	public void apply(ContainerItemContext context, DracoItemConfiguration itemConfiguration, DracoItemEnergy energy)
	{
		if(euCost > 0)
		{
			energy.extract(euCost);
			energy.save();
		}
	}
}
