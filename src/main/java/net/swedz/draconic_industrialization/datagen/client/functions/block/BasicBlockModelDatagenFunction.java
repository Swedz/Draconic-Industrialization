package net.swedz.draconic_industrialization.datagen.client.functions.block;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.datagen.api.ClientDatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;

import java.io.IOException;

public final class BasicBlockModelDatagenFunction extends ClientDatagenFunction<DIBlock>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.BLOCK_CLIENT;
	}
	
	private JsonObject buildBlockModelJson(DIBlock block)
	{
		JsonObject json = new JsonObject();
		
		json.addProperty("parent", "block/cube_all");
		
		JsonObject textures = new JsonObject();
		textures.addProperty("all", DIDatagenClient.blockModelTarget(block.modId(), block.id(false)));
		json.add("textures", textures);
		
		return json;
	}
	
	private JsonObject buildBlockstatesJson(DIBlock block)
	{
		JsonObject json = new JsonObject();
		
		JsonObject variants = new JsonObject();
		
		JsonObject model = new JsonObject();
		model.addProperty("model", DIDatagenClient.blockModelTarget(block.modId(), block.id(false)));
		
		variants.add("", model);
		
		json.add("variants", variants);
		
		return json;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIBlock block) throws IOException
	{
		final String blockId = block.id(false);
		
		final String blockModelPath = DIDatagenClient.blockModelPath(provider.modId(), blockId);
		provider.writeJsonIfNotExist(output, blockModelPath, this.buildBlockModelJson(block));
		
		final String blockstatesModelPath = DIDatagenClient.blockstatesModelPath(provider.modId(), blockId);
		provider.writeJsonIfNotExist(output, blockstatesModelPath, this.buildBlockstatesJson(block));
	}
}
