package net.swedz.draconic_industrialization.datagen.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;

public abstract class DIClientAssetDataProvider implements DataProvider
{
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	protected final FabricDataGenerator dataGenerator;
	
	public DIClientAssetDataProvider(FabricDataGenerator dataGenerator)
	{
		this.dataGenerator = dataGenerator;
	}
	
	protected abstract void run(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException;
	
	@Override
	public void run(CachedOutput output) throws IOException
	{
		this.run(
				output,
				dataGenerator.getOutputFolder(),
				dataGenerator.getOutputFolder().resolve("../../main/resources")
		);
	}
}
