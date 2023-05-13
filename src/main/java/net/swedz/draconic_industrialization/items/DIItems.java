package net.swedz.draconic_industrialization.items;

import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

import java.util.Set;

public final class DIItems
{
	private static final Set<DIItem> ITEMS = Sets.newHashSet();
	
	public static final Item WYVERN_CIRCUIT            = generic("wyvern_circuit", "Wyvern Circuit");
	public static final Item DRACONIC_CIRCUIT          = generic("draconic_circuit", "Draconic Circuit");
	public static final Item AWAKENED_DRACONIC_CIRCUIT = generic("awakened_draconic_circuit", "Awakened Draconic Circuit");
	public static final Item CHAOTIC_CIRCUIT           = generic("chaotic_circuit", "Chaotic Circuit");
	
	public static Set<DIItem> all()
	{
		return Set.copyOf(ITEMS);
	}
	
	private static Item register(Item item, DIItemSettings settings)
	{
		ITEMS.add(new DIItem(item, settings));
		return item;
	}
	
	private static Item generic(String id, String englishName)
	{
		final DIItemSettings settings = new DIItemSettings().textureLocation(id).englishName(englishName);
		return register(Registry.register(
				Registry.ITEM,
				DraconicIndustrialization.id(id),
				new Item(settings)
		), settings);
	}
	
	public static void init()
	{
		// Load the class
	}
}
