package net.swedz.draconic_industrialization.datagen.client;

import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.keybinds.DIKeybinds;

import java.util.Map;

final class LangDatagen
{
	static void add(Map<String, String> map)
	{
		// Creative menu
		map.put("itemGroup.draconic_industrialization.draconic_industrialization", "Draconic Industrialization");
		
		// Keybinds
		map.put("category.draconic_industrialization.draconic_industrialization", "Draconic Industrialization");
		DIKeybinds.all().forEach((bind) -> map.put(bind.keyMapping().getName(), bind.englishName()));
		
		// Armor model types
		for(DraconicArmorModelType value : DraconicArmorModelType.values())
		{
			map.put(value.translationKey(), value.englishName());
		}
		
		// Armor shield types
		for(DraconicArmorShieldType value : DraconicArmorShieldType.values())
		{
			map.put(value.translationKey(), value.englishName());
		}
	}
}
