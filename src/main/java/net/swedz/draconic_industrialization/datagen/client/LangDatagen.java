package net.swedz.draconic_industrialization.datagen.client;

import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
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
		
		// Draco Menu
		map.put("screen.draconic_industrialization.draco", "Draco Configurator");
		map.put("draco_menu.module.colorizer.color", "■ Color: ");
		map.put("draco_menu.module.colorizer.config.label", "Shield Color");
		map.put("draco_menu.module.colorizer.config.red", "■ Red");
		map.put("draco_menu.module.colorizer.config.green", "■ Green");
		map.put("draco_menu.module.colorizer.config.blue", "■ Blue");
		map.put("draco_menu.module.armor_appearance.model", "■ Model: ");
		map.put("draco_menu.module.armor_appearance.shield", "■ Shield: ");
		map.put("draco_menu.module.armor_appearance.config.label", "Armor Appearance");
		map.put("draco_menu.module.armor_appearance.config.model", "■ Model");
		map.put("draco_menu.module.armor_appearance.config.shield", "■ Shield");
		
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
