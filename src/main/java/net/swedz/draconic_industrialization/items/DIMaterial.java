package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.materials.part.PartEnglishNameFormatter;
import aztech.modern_industrialization.materials.part.PartKeyProvider;
import aztech.modern_industrialization.materials.part.PartTemplate;
import aztech.modern_industrialization.materials.set.MaterialSet;

import java.lang.reflect.Field;

public record DIMaterial(String id, String englishName, MaterialSet materialSet)
{
	public String fullId(PartKeyProvider part)
	{
		return "%s_%s".formatted(id, part.key().key);
	}
	
	public String fullEnglishName(PartTemplate part)
	{
		try
		{
			Field field = PartTemplate.class.getDeclaredField("englishNameFormatter");
			field.setAccessible(true);
			return ((PartEnglishNameFormatter) field.get(part)).format(englishName);
		}
		catch (IllegalAccessException | NoSuchFieldException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	public String fullEnglishNameAsBlock()
	{
		return "Block of %s".formatted(englishName);
	}
}
