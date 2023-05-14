package net.swedz.draconic_industrialization.datagen.api;

import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;
import java.util.Set;

public final class DatagenFunctionContainer<T>
{
	private final Set<DatagenFunction<T>> functions = Sets.newHashSet();
	
	public void add(DatagenFunction<T> function)
	{
		functions.add(function);
	}
	
	public void executeAll(DatagenFunctionCategory category, DatagenProvider provider, CachedOutput output, ResourceProvider resourceProvider, T entry) throws IOException
	{
		for(DatagenFunction<T> function : functions)
		{
			if(function.category() == category)
			{
				final ClientDatagenFunction<T> clientFunction = function instanceof ClientDatagenFunction<T> ? (ClientDatagenFunction<T>) function : null;
				
				function.init(provider, output);
				
				if(clientFunction != null)
				{
					clientFunction.withResourceProvider(resourceProvider);
				}
				
				function.run(provider, output, entry);
				
				if(clientFunction != null)
				{
					clientFunction.resetResourceProvider();
				}
				
				function.after(provider, output);
			}
		}
	}
	
	public void executeAll(DatagenFunctionCategory category, DatagenProvider provider, CachedOutput output, T entry) throws IOException
	{
		this.executeAll(category, provider, output, null, entry);
	}
}
