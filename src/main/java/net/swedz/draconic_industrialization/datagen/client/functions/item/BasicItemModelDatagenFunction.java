package net.swedz.draconic_industrialization.datagen.client.functions.item;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.datagen.api.ClientDatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;

import java.io.IOException;

public final class BasicItemModelDatagenFunction extends ClientDatagenFunction<DIItem>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_CLIENT;
	}
	
	private JsonObject buildItemModelJson(DIItem item)
	{
		final JsonObject json = new JsonObject();
		
		if(item.isBlock())
		{
			json.addProperty("parent", DIDatagenClient.blockModelTarget(item.modId(), item.id(false)));
		}
		else
		{
			json.addProperty("parent", "item/generated");
			
			JsonObject textures = new JsonObject();
			textures.addProperty("layer0", DIDatagenClient.itemModelTarget(item.modId(), item.id(false)));
			json.add("textures", textures);
		}
		
		return json;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item) throws IOException
	{
		final DIItemSettings settings = item.settings();
		final String modelPath = DIDatagenClient.itemModelPath(provider.dataGenerator().getModId(), item.id(false));
		provider.writeJsonIfNotExist(output, modelPath, this.buildItemModelJson(item));
	}
}
