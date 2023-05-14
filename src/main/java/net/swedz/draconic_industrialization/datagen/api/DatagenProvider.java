package net.swedz.draconic_industrialization.datagen.api;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class DatagenProvider implements DataProvider
{
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	
	protected final FabricDataGenerator dataGenerator;
	
	private final String name;
	
	protected DatagenProvider(FabricDataGenerator dataGenerator, String name)
	{
		this.dataGenerator = dataGenerator;
		this.name = name;
	}
	
	public FabricDataGenerator dataGenerator()
	{
		return dataGenerator;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	public String modId()
	{
		return dataGenerator.getModId();
	}
	
	public Path outputPath()
	{
		return dataGenerator.getOutputFolder();
	}
	
	public Path nonGeneratedPath()
	{
		return dataGenerator.getOutputFolder().resolve("../../main/resources");
	}
	
	public void writeJsonForce(CachedOutput output, String path, JsonElement json) throws IOException
	{
		DataProvider.saveStable(output, GSON.toJsonTree(json), this.outputPath().resolve(path));
	}
	
	public void writeJsonIfNotExist(CachedOutput output, String path, JsonElement json) throws IOException
	{
		if(!Files.exists(this.nonGeneratedPath().resolve(path)))
		{
			this.writeJsonForce(output, path, json);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void writeImageIfNotExist(CachedOutput output, String path, NativeImage image) throws IOException
	{
		if(!Files.exists(this.nonGeneratedPath().resolve(path)))
		{
			byte[] textureBytes = image.asByteArray();
			output.writeIfNeeded(
					this.outputPath().resolve(path),
					textureBytes,
					Hashing.sha1().hashBytes(textureBytes)
			);
			image.close();
		}
	}
}
