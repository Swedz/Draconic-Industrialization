package net.swedz.draconic_industrialization.datagen.client.functions.item;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.datagen.api.ClientDatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;

import java.io.IOException;

public final class DracoModuleItemModelDatagenFunction extends ClientDatagenFunction<DIItem>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_CLIENT;
	}
	
	private String baseLayer(DracoTier tier)
	{
		return switch (tier)
				{
					case WYVERN -> "draconic_industrialization:item/module/wyvern";
					case DRACONIC -> "draconic_industrialization:item/module/draconic";
					case CHAOTIC -> "draconic_industrialization:item/module/chaotic";
				};
	}
	
	private JsonObject buildItemModelJson(DIItem item)
	{
		final DracoModuleItem moduleItem = (DracoModuleItem) item.item();
		
		final JsonObject json = new JsonObject();
		
		json.addProperty("parent", "item/generated");
		
		JsonObject textures = new JsonObject();
		textures.addProperty("layer0", this.baseLayer(moduleItem.moduleReference().tier()));
		textures.addProperty("layer1", "draconic_industrialization:item/module/%s".formatted(moduleItem.moduleReference().id()));
		json.add("textures", textures);
		
		return json;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item) throws IOException
	{
		if(item.isBlock())
		{
			DraconicIndustrialization.LOGGER.warn("Tried applying DracoModuleItemModelDatagenFunction to a block item, why?");
			return;
		}
		if(!(item.item() instanceof DracoModuleItem))
		{
			DraconicIndustrialization.LOGGER.warn("Tried applying DracoModuleItemModelDatagenFunction to a non-module item");
			return;
		}
		
		final DIItemSettings settings = item.settings();
		final String modelPath = DIDatagenClient.itemModelPath(provider.dataGenerator().getModId(), item.id(false));
		provider.writeJsonIfNotExist(output, modelPath, this.buildItemModelJson(item));
	}
}
