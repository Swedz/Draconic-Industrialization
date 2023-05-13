package net.swedz.draconic_industrialization.items;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public final class DIItemSettings extends FabricItemSettings
{
	private String englishName;
	
	private String textureLocation;
	
	public String englishName()
	{
		return englishName;
	}
	
	public DIItemSettings englishName(String englishName)
	{
		this.englishName = englishName;
		return this;
	}
	
	public String textureLocation()
	{
		return textureLocation;
	}
	
	public DIItemSettings textureLocation(String path)
	{
		this.textureLocation = path;
		return this;
	}
	
	public JsonObject toModelJson()
	{
		JsonObject json = new JsonObject();
		
		json.addProperty("parent", "item/generated");
		
		JsonObject textures = new JsonObject();
		textures.addProperty("layer0", "%s:item/%s".formatted(
				DraconicIndustrialization.ID,
				textureLocation
		));
		json.add("textures", textures);
		
		return json;
	}
}
