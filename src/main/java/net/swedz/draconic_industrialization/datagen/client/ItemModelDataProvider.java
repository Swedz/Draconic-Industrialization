package net.swedz.draconic_industrialization.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIItems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ItemModelDataProvider extends DIClientAssetDataProvider
{
	public ItemModelDataProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}
	
	private void items(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException
	{
		for(DIItem item : DIItems.all())
		{
			final DIItemSettings settings = item.settings();
			final String modelPath = "assets/%s/models/item/%s.json".formatted(
					dataGenerator.getModId(),
					item.location().getPath()
			);
			if(!Files.exists(nonGeneratedPath.resolve(modelPath)))
			{
				DataProvider.saveStable(output, GSON.toJsonTree(settings.toModelJson()), outputPath.resolve(modelPath));
			}
		}
	}
	
	@Override
	protected void run(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException
	{
		this.items(output, outputPath, nonGeneratedPath);
	}
	
	@Override
	public String getName()
	{
		return "Item Model Provider";
	}
}
