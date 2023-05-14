package net.swedz.draconic_industrialization.datagen.api;

import net.minecraft.server.packs.resources.ResourceProvider;

public abstract class ClientDatagenFunction<T> implements DatagenFunction<T>
{
	protected ResourceProvider resourceProvider;
	
	public void withResourceProvider(ResourceProvider resourceProvider)
	{
		this.resourceProvider = resourceProvider;
	}
	
	public void resetResourceProvider()
	{
		this.resourceProvider = null;
	}
}
