package net.swedz.draconic_industrialization.datagen.client;

import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorShieldType;

import java.util.Map;

final class LangDatagen
{
	static void add(Map<String, String> map)
	{
		map.put("itemGroup.draconic_industrialization.draconic_industrialization", "Draconic Industrialization");
		
		for(DraconicArmorModelType value : DraconicArmorModelType.values())
		{
			map.put(value.translationKey(), value.englishName());
		}
		
		for(DraconicArmorShieldType value : DraconicArmorShieldType.values())
		{
			map.put(value.translationKey(), value.englishName());
		}
	}
}
