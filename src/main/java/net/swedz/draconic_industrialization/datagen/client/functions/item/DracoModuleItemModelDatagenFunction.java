package net.swedz.draconic_industrialization.datagen.client.functions.item;

import com.google.gson.JsonArray;
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
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.io.IOException;

public final class DracoModuleItemModelDatagenFunction extends ClientDatagenFunction<DIItem>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_CLIENT;
	}
	
	private String format(String string, int width, int height, boolean baseLayer)
	{
		if((baseLayer && (width > 1 || height > 1)) || (!baseLayer && width != height))
		{
			string += "_%dx%d".formatted(width, height);
		}
		return string;
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
	
	private JsonObject buildItemModelJson(DatagenProvider provider, DIItem item, boolean gui)
	{
		final DracoModuleItem moduleItem = (DracoModuleItem) item.item();
		final DracoGridSlotShape shape = moduleItem.moduleReference().gridShape();
		final int width = shape.width();
		final int height = shape.height();
		
		final JsonObject json = new JsonObject();
		
		json.addProperty("parent", "item/generated");
		
		JsonObject textures = new JsonObject();
		String baseLayer = this.baseLayer(moduleItem.moduleReference().tier());
		String iconLayer = "draconic_industrialization:item/module/%s".formatted(moduleItem.moduleReference().id());
		if(gui)
		{
			baseLayer = this.format(baseLayer, width, height, true);
			iconLayer = this.format(iconLayer, width, height, false);
		}
		textures.addProperty("layer0", baseLayer);
		textures.addProperty("layer1", iconLayer);
		json.add("textures", textures);
		
		if(!gui)
		{
			JsonArray overrides = new JsonArray();
			JsonObject guiOverride = new JsonObject();
			JsonObject guiOverridePredicate = new JsonObject();
			guiOverridePredicate.addProperty(DraconicIndustrialization.id("in_draco_gui").toString(), 1);
			guiOverride.add("predicate", guiOverridePredicate);
			guiOverride.addProperty("model", DIDatagenClient.itemModelTarget(provider.dataGenerator().getModId(), item.id(false) + "_in_gui"));
			overrides.add(guiOverride);
			json.add("overrides", overrides);
		}
		
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
		
		final String baseModelPath = DIDatagenClient.itemModelPath(provider.dataGenerator().getModId(), item.id(false));
		provider.writeJsonIfNotExist(output, baseModelPath, this.buildItemModelJson(provider, item, false));
		
		final String guiModelPath = DIDatagenClient.itemModelPath(provider.dataGenerator().getModId(), item.id(false) + "_in_gui");
		provider.writeJsonIfNotExist(output, guiModelPath, this.buildItemModelJson(provider, item, true));
	}
}
