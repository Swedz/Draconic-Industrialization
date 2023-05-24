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
		
		map.put("draco_menu.module.colorizer.attribute.color", "■ Color: ");
		map.put("draco_menu.module.colorizer.config.info.0", "Your item's color may be changed using the following RGB/Hex color options.");
		map.put("draco_menu.module.colorizer.config.info.1", "You can also load your clipboard if it is a Hex color code (such as #FF0000, or F00).");
		map.put("draco_menu.module.colorizer.config.label", "Shield Color");
		map.put("draco_menu.module.colorizer.config.red", "■ Red");
		map.put("draco_menu.module.colorizer.config.green", "■ Green");
		map.put("draco_menu.module.colorizer.config.blue", "■ Blue");
		map.put("draco_menu.module.colorizer.config.hex", "■ Hex");
		
		map.put("draco_menu.module.armor_appearance.attribute.model", "■ Model: ");
		map.put("draco_menu.module.armor_appearance.attribute.shield", "■ Shield: ");
		map.put("draco_menu.module.armor_appearance.config.info.0", "Your armor's model can be changed between a set of premade models.");
		map.put("draco_menu.module.armor_appearance.config.info.1", "Additionally, you can change how the shield layer surrounding your character model appears.");
		map.put("draco_menu.module.armor_appearance.config.info.2", "More options are available to patrons!");
		map.put("draco_menu.module.armor_appearance.config.label", "Armor Appearance");
		map.put("draco_menu.module.armor_appearance.config.model", "■ Model");
		map.put("draco_menu.module.armor_appearance.config.shield", "■ Shield");
		
		map.put("draco_menu.module.speed_amplifier.attribute.speed", "■ Speed: ");
		map.put("draco_menu.module.speed_amplifier.config.label", "Speed Amplifier");
		map.put("draco_menu.module.speed_amplifier.config.info.0", "The speed of your items can be amplified by a certain percentage.");
		map.put("draco_menu.module.speed_amplifier.config.info.1", "This can mean different things for different item types. For armor, it means your movement speed when wearing it. For tools, it means the digging speed.");
		map.put("draco_menu.module.speed_amplifier.config.speed", "■ Speed");
		
		map.put("draco_menu.module.generic.clipboard", "Load from clipboard");
		
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
