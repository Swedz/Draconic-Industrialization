package net.swedz.draconic_industrialization.material;

import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;

import java.util.function.Function;

public enum DIMaterialPart
{
	TINY_DUST("Tiny Dust"),
	DUST("Dust"),
	CRUSHED_DUST("Crushed Dust"),
	HOT_INGOT("Hot Ingot"),
	INGOT("Ingot"),
	PLATE("Plate"),
	CURVED_PLATE("Curved Plate"),
	WIRE("Wire"),
	CABLE("Cable"),
	BLOCK("Block of %s"::formatted),
	COIL("Coil"),
	ORE("%s Ore"::formatted);
	
	private final Function<String, String> englishNameGenerator;
	
	DIMaterialPart(Function<String, String> englishNameGenerator)
	{
		this.englishNameGenerator = englishNameGenerator;
	}
	
	DIMaterialPart(String englishName)
	{
		this(new BasicEnglishNameGenerator(englishName));
	}
	
	public String englishName(String materialEnglishName)
	{
		return englishNameGenerator.apply(materialEnglishName);
	}
	
	public String id()
	{
		return this.name().toLowerCase();
	}
	
	public String tag(String materialId)
	{
		return DIDatagenClient.tagMaterialTarget(materialId, this.id());
	}
	
	private record BasicEnglishNameGenerator(String englishName) implements Function<String, String>
	{
		@Override
		public String apply(String materialEnglishName)
		{
			return "%s %s".formatted(materialEnglishName, englishName);
		}
	}
}
