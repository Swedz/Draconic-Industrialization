package net.swedz.draconic_industrialization.datagen.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.CachedOutput;

import java.io.IOException;

public interface DatagenFunction<T>
{
	Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	DatagenFunctionCategory category();
	
	/**
	 * Value used to sort the functions before they are called. Functions are sorted by priority in ascending order (i.e. larger numbers run later).
	 *
	 * @return the priority
	 */
	default int priority()
	{
		return 0;
	}
	
	default void globalInit(DatagenProvider provider, CachedOutput output)
	{
	}
	
	default void init(DatagenProvider provider, CachedOutput output)
	{
	}
	
	void run(DatagenProvider provider, CachedOutput output, T object) throws IOException;
	
	default void globalAfter(DatagenProvider provider, CachedOutput output) throws IOException
	{
	}
	
	default void after(DatagenProvider provider, CachedOutput output)
	{
	}
}
