package net.swedz.draconic_industrialization.items;

import net.swedz.draconic_industrialization.material.DIMaterialPart;

public record DIMaterial(String id, String englishName, double hardness)
{
	public String fullId(DIMaterialPart part)
	{
		return "%s_%s".formatted(id, part.id());
	}
	
	public String fullEnglishName(DIMaterialPart part)
	{
		return part.englishName(englishName);
	}
	
	public int recipeDuration()
	{
		return (int) (hardness * 200);
	}
}
