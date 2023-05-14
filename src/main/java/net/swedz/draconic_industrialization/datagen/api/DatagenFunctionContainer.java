package net.swedz.draconic_industrialization.datagen.api;

import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.apache.commons.compress.utils.Lists;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class DatagenFunctionContainer<T>
{
	private final Set<DatagenFunction<T>> functions = Sets.newHashSet();
	
	public DatagenFunctionContainer<T> add(DatagenFunction<T> function)
	{
		functions.add(function);
		return this;
	}
	
	public void executeAll(DatagenFunctionCategory category, DatagenProvider provider, CachedOutput output, ResourceProvider resourceProvider, T entry) throws IOException
	{
		List<DatagenFunction<T>> orderedFunctions = Lists.newArrayList(functions.iterator());
		orderedFunctions.sort(Comparator.comparingInt(DatagenFunction::priority));
		for(DatagenFunction<T> function : orderedFunctions)
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
