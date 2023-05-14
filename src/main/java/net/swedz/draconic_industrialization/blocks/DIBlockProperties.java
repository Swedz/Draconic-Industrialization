package net.swedz.draconic_industrialization.blocks;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public final class DIBlockProperties extends FabricBlockSettings
{
	private String englishName;
	
	private String textureLocation;
	
	private DIBlockProperties(Material material, MaterialColor color)
	{
		super(material, color);
	}
	
	private DIBlockProperties(BlockBehaviour.Properties settings)
	{
		super(settings);
	}
	
	public static DIBlockProperties of(Material material)
	{
		return of(material, material.getColor());
	}
	
	public static DIBlockProperties of(Material material, MaterialColor color)
	{
		return new DIBlockProperties(material, color);
	}
	
	public static DIBlockProperties of(Material material, DyeColor color)
	{
		return new DIBlockProperties(material, color.getMaterialColor());
	}
	
	public static DIBlockProperties copyOf(BlockBehaviour behaviour)
	{
		return new DIBlockProperties(((AbstractBlockAccessor) behaviour).getSettings());
	}
	
	public static DIBlockProperties copyOf(BlockBehaviour.Properties properties)
	{
		return new DIBlockProperties(properties);
	}
	
	public String englishName()
	{
		return englishName;
	}
	
	public DIBlockProperties englishName(String englishName)
	{
		this.englishName = englishName;
		return this;
	}
	
	public String textureLocation()
	{
		return textureLocation;
	}
	
	public DIBlockProperties textureLocation(String path)
	{
		this.textureLocation = path;
		return this;
	}
	
	public JsonObject toModelJson()
	{
		JsonObject json = new JsonObject();
		
		json.addProperty("parent", "block/cube_all");
		
		JsonObject textures = new JsonObject();
		textures.addProperty("all", "%s:block/%s".formatted(
				DraconicIndustrialization.ID,
				textureLocation
		));
		json.add("textures", textures);
		
		return json;
	}
}
