package net.swedz.draconic_industrialization.datagen.client;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.blocks.DIBlockProperties;
import net.swedz.draconic_industrialization.blocks.DIBlocks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class BlockModelDataProvider extends DIClientAssetDataProvider
{
	public BlockModelDataProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}
	
	@Override
	protected void run(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException
	{
		for(DIBlock block : DIBlocks.all())
		{
			if(block.properties().textureLocation() == null)
			{
				continue;
			}
			
			final String blockId = block.location().getPath();
			final DIBlockProperties properties = block.properties();
			
			final String modelPath = "assets/%s/models/%s/%s.json";
			
			final String blockModelPath = modelPath.formatted(dataGenerator.getModId(), "block", blockId);
			if(!Files.exists(nonGeneratedPath.resolve(blockModelPath)))
			{
				DataProvider.saveStable(output, GSON.toJsonTree(properties.toModelJson()), outputPath.resolve(blockModelPath));
			}
			
			final String blockModelTarget = "%s:block/%s".formatted(dataGenerator.getModId(), blockId);
			
			final String itemModelPath = modelPath.formatted(dataGenerator.getModId(), "item", blockId);
			if(!Files.exists(nonGeneratedPath.resolve(itemModelPath)))
			{
				JsonObject json = new JsonObject();
				json.addProperty("parent", blockModelTarget);
				DataProvider.saveStable(output, GSON.toJsonTree(json), outputPath.resolve(itemModelPath));
			}
			
			final String blockstateModelPath = "assets/%s/blockstates/%s.json".formatted(dataGenerator.getModId(), blockId);
			if(!Files.exists(nonGeneratedPath.resolve(blockstateModelPath)))
			{
				JsonObject json = new JsonObject();
				JsonObject variants = new JsonObject();
				JsonObject model = new JsonObject();
				model.addProperty("model", blockModelTarget);
				variants.add("", model);
				json.add("variants", variants);
				DataProvider.saveStable(output, GSON.toJsonTree(json), outputPath.resolve(blockstateModelPath));
			}
		}
	}
	
	@Override
	public String getName()
	{
		return "Block Model Provider";
	}
}
