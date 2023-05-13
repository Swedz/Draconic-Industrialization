package net.swedz.draconic_industrialization.datagen.client;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIItems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class LangDataProvider extends DIClientAssetDataProvider
{
	public LangDataProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}
	
	@Override
	protected void run(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException
	{
		JsonObject langJson = new JsonObject();
		
		for(DIItem item : DIItems.all())
		{
			final DIItemSettings settings = item.settings();
			langJson.addProperty(item.item().getDescriptionId(), settings.englishName());
		}
		
		final String langPath = "assets/%s/lang/en_us.json".formatted(dataGenerator.getModId());
		if(!Files.exists(nonGeneratedPath.resolve(langPath)))
		{
			DataProvider.saveStable(output, GSON.toJsonTree(langJson), outputPath.resolve(langPath));
		}
	}
	
	@Override
	public String getName()
	{
		return "Language Provider";
	}
}
