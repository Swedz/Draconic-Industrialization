package net.swedz.draconic_industrialization.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class DIDatagenClient
{
	public static void configure(FabricDataGenerator dataGenerator)
	{
		dataGenerator.addProvider(ClientDatagenProvider::new);
	}
	
	public static String lootTablePath(String modId, String type, String id)
	{
		return "data/%s/loot_tables/%ss/%s.json".formatted(modId, type, id);
	}
	
	public static String tagPath(String modId, String type, String id)
	{
		return "data/%s/tags/%ss/%s.json".formatted(modId, type, id);
	}
	
	public static String tagMaterialTarget(String materialName, String partId)
	{
		return "c:%s_%ss".formatted(materialName, partId);
	}
	
	public static String modelPath(String modId, String type, String id)
	{
		return "assets/%s/models/%s/%s.json".formatted(modId, type, id);
	}
	
	public static String itemModelPath(String modId, String id)
	{
		return modelPath(modId, "item", id);
	}
	
	public static String blockModelPath(String modId, String id)
	{
		return modelPath(modId, "block", id);
	}
	
	public static String modelTarget(String modId, String type, String id)
	{
		return "%s:%s/%s".formatted(modId, type, id);
	}
	
	public static String itemModelTarget(String modId, String id)
	{
		return modelTarget(modId, "item", id);
	}
	
	public static String blockModelTarget(String modId, String id)
	{
		return modelTarget(modId, "block", id);
	}
	
	public static String blockstatesModelPath(String modId, String id)
	{
		return "assets/%s/blockstates/%s.json".formatted(modId, id);
	}
	
	public static String texturePath(String modId, String type, String id)
	{
		return "assets/%s/textures/%s/%s.png".formatted(modId, type, id);
	}
	
	public static String itemTexturePath(String modId, String id)
	{
		return texturePath(modId, "item", id);
	}
	
	public static String blockTexturePath(String modId, String id)
	{
		return texturePath(modId, "block", id);
	}
	
	public static String gradientTextureTarget(String name)
	{
		return "draconic_industrialization:textures/gradient_maps/%s.png".formatted(name);
	}
}
