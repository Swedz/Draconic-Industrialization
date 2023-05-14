package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.PartEnglishNameFormatter;
import aztech.modern_industrialization.materials.part.PartTemplate;
import aztech.modern_industrialization.materials.set.MaterialSet;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Consumer;

public final class DIItems
{
	private static final Set<DIItem> ITEMS = Sets.newHashSet();
	
	public static final Item WYVERN_CIRCUIT            = generic("wyvern_circuit", "Wyvern Circuit");
	public static final Item DRACONIC_CIRCUIT          = generic("draconic_circuit", "Draconic Circuit");
	public static final Item AWAKENED_DRACONIC_CIRCUIT = generic("awakened_draconic_circuit", "Awakened Draconic Circuit");
	public static final Item CHAOTIC_CIRCUIT           = generic("chaotic_circuit", "Chaotic Circuit");
	public static final Item DRAGON_HEART              = generic("dragon_heart", "Dragon Heart", (s) -> s.rarity(Rarity.RARE));
	
	private static final DIMaterial DRACONIUM              = material("draconium", "Draconium", MaterialSet.METALLIC);
	public static final  Item       DRACONIUM_INGOT        = materialPart(DRACONIUM, MIParts.INGOT);
	public static final  Item       DRACONIUM_PLATE        = materialPart(DRACONIUM, MIParts.PLATE);
	public static final  Item       DRACONIUM_CURVED_PLATE = materialPart(DRACONIUM, MIParts.CURVED_PLATE);
	public static final  Item       DRACONIUM_WIRE         = materialPart(DRACONIUM, MIParts.WIRE);
	
	private static final DIMaterial AWAKENED_DRACONIUM              = material("awakened_draconium", "Awakened Draconium", MaterialSet.METALLIC);
	public static final  Item       AWAKENED_DRACONIUM_INGOT        = materialPart(AWAKENED_DRACONIUM, MIParts.INGOT);
	public static final  Item       AWAKENED_DRACONIUM_PLATE        = materialPart(AWAKENED_DRACONIUM, MIParts.PLATE);
	public static final  Item       AWAKENED_DRACONIUM_CURVED_PLATE = materialPart(AWAKENED_DRACONIUM, MIParts.CURVED_PLATE);
	public static final  Item       AWAKENED_DRACONIUM_WIRE         = materialPart(AWAKENED_DRACONIUM, MIParts.WIRE);
	
	public static Set<DIItem> all()
	{
		return Set.copyOf(ITEMS);
	}
	
	private static Item register(Item item, DIItemSettings settings)
	{
		ITEMS.add(new DIItem(item, settings));
		return item;
	}
	
	private static Item generic(String id, String englishName, Consumer<DIItemSettings> settingsConsumer)
	{
		final DIItemSettings settings = new DIItemSettings()
				.textureLocation(id)
				.englishName(englishName)
				.tab(DraconicIndustrialization.CREATIVE_TAB);
		settingsConsumer.accept(settings);
		return register(Registry.register(
				Registry.ITEM,
				DraconicIndustrialization.id(id),
				new Item(settings)
		), settings);
	}
	
	private static Item generic(String id, String englishName)
	{
		return generic(id, englishName, (settings) ->
		{
		});
	}
	
	private static DIMaterial material(String id, String englishName, MaterialSet materialSet)
	{
		return new DIMaterial(id, englishName, materialSet);
	}
	
	private static Item materialPart(DIMaterial material, PartTemplate part)
	{
		final String name = material.id();
		final String englishName = material.englishName();
		final String fullEnglishName;
		try
		{
			Field field = PartTemplate.class.getDeclaredField("englishNameFormatter");
			field.setAccessible(true);
			fullEnglishName = ((PartEnglishNameFormatter) field.get(part)).format(englishName);
		}
		catch (IllegalAccessException | NoSuchFieldException ex)
		{
			throw new RuntimeException(ex);
		}
		final String partId = part.key().key;
		return generic("%s_%s".formatted(name, partId), fullEnglishName, (s) -> s
				.textureLocation("materials/%s/%s".formatted(name, partId))
				.materialPart(name, part, material.materialSet()));
	}
	
	public static void init()
	{
		// Load the class
	}
}
