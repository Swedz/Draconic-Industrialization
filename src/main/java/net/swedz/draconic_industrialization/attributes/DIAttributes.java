package net.swedz.draconic_industrialization.attributes;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

import java.util.Map;

public final class DIAttributes
{
	private static final Map<Attribute, String> ATTRIBUTES = Maps.newHashMap();
	
	public static final Attribute JUMP_HEIGHT = register("generic.jump_height", "Jump Height", 1, 0, 8, true);
	
	private static Attribute register(String id, String englishName, double defaultValue, boolean sync)
	{
		Attribute attribute = Registry.register(Registry.ATTRIBUTE, DraconicIndustrialization.id(id), new Attribute("attribute.name.draconic_industrialization.%s".formatted(id), defaultValue).setSyncable(sync));
		ATTRIBUTES.put(attribute, englishName);
		return attribute;
	}
	
	private static Attribute register(String id, String englishName, double defaultValue, double min, double max, boolean sync)
	{
		Attribute attribute = Registry.register(Registry.ATTRIBUTE, DraconicIndustrialization.id(id), new RangedAttribute("attribute.name.draconic_industrialization.%s".formatted(id), defaultValue, min, max).setSyncable(sync));
		ATTRIBUTES.put(attribute, englishName);
		return attribute;
	}
	
	public static Map<Attribute, String> all()
	{
		return Map.copyOf(ATTRIBUTES);
	}
	
	public static void init()
	{
	}
}
