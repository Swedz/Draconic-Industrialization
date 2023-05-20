package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.api.energy.CableTier;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.render.DraconicArmorItemModel;
import net.swedz.draconic_industrialization.items.item.draconicarmor.render.DraconicArmorRenderer;
import net.swedz.draconic_industrialization.material.DIMaterialHardness;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Map;
import java.util.Set;

public final class DIItems
{
	private static final Map<ItemBuilder, DIItem> BUILDERS = Maps.newHashMap();
	private static final Set<DIItem>              ITEMS    = Sets.newHashSet();
	
	public static final Item DRACONIC_CIRCUIT           = builder().identifiable("draconic_circuit", "Draconic Circuit").generateBasicModel().build();
	public static final Item WYVERN_CIRCUIT             = builder().identifiable("wyvern_circuit", "Wyvern Circuit").generateBasicModel().build();
	public static final Item AWAKENED_DRACONIUM_CIRCUIT = builder().identifiable("awakened_draconium_circuit", "Awakened Draconium Circuit").generateBasicModel().build();
	public static final Item CHAOTIC_CIRCUIT            = builder().identifiable("chaotic_circuit", "Chaotic Circuit").generateBasicModel().build();
	public static final Item DRAGON_HEART               = builder().identifiable("dragon_heart", "Dragon Heart").generateBasicModel().withSettings((s) -> s.rarity(Rarity.RARE)).build();
	
	public static final DIMaterial DRACONIUM              = material("draconium", "Draconium", DIMaterialHardness.AVERAGE);
	public static final Item       DRACONIUM_TINY_DUST    = builder().materialBasic(DRACONIUM, DIMaterialPart.TINY_DUST, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_DUST         = builder().materialBasic(DRACONIUM, DIMaterialPart.DUST, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_CRUSHED_DUST = builder().materialBasic(DRACONIUM, DIMaterialPart.CRUSHED_DUST, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_HOT_INGOT    = builder().materialBasic(DRACONIUM, DIMaterialPart.HOT_INGOT, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_INGOT        = builder().materialBasic(DRACONIUM, DIMaterialPart.INGOT, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_PLATE        = builder().materialBasic(DRACONIUM, DIMaterialPart.PLATE, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_CURVED_PLATE = builder().materialBasic(DRACONIUM, DIMaterialPart.CURVED_PLATE, StandardRecipes::apply).build();
	public static final Item       DRACONIUM_WIRE         = builder().materialBasic(DRACONIUM, DIMaterialPart.WIRE, StandardRecipes::apply).build();
	
	public static final DIMaterial ADAMANTINE           = material("adamantine", "Adamantine", DIMaterialHardness.VERY_HARD);
	public static final Item       ADAMANTINE_TINY_DUST = builder().materialBasic(ADAMANTINE, DIMaterialPart.TINY_DUST, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_DUST      = builder().materialBasic(ADAMANTINE, DIMaterialPart.DUST, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_HOT_INGOT = builder().materialBasic(ADAMANTINE, DIMaterialPart.HOT_INGOT, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_INGOT     = builder().materialBasic(ADAMANTINE, DIMaterialPart.INGOT, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_PLATE     = builder().materialBasic(ADAMANTINE, DIMaterialPart.PLATE, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_WIRE      = builder().materialBasic(ADAMANTINE, DIMaterialPart.WIRE, StandardRecipes::apply).build();
	public static final Item       ADAMANTINE_CABLE     = builder().materialCable(ADAMANTINE, 0x49A954, CableTier.EV, StandardRecipes::apply).build();
	
	public static final DIMaterial AWAKENED_DRACONIUM              = material("awakened_draconium", "Awakened Draconium", DIMaterialHardness.HARD);
	public static final Item       AWAKENED_DRACONIUM_TINY_DUST    = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.TINY_DUST, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_DUST         = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.DUST, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_HOT_INGOT    = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.HOT_INGOT, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_INGOT        = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.INGOT, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_PLATE        = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.PLATE, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_CURVED_PLATE = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.CURVED_PLATE, StandardRecipes::apply).build();
	public static final Item       AWAKENED_DRACONIUM_WIRE         = builder().materialBasic(AWAKENED_DRACONIUM, DIMaterialPart.WIRE, StandardRecipes::apply).build();
	
	public static final Item WYVERN_ARMOR   = builder().identifiable("wyvern_armor", "Wyvern Armor").creator((s) -> new DraconicArmorItem(DracoTier.WYVERN, s)).withItemRenderer(new DraconicArmorItemModel()).withArmorRenderer(new DraconicArmorRenderer()).build();
	public static final Item DRACONIC_ARMOR = builder().identifiable("draconic_armor", "Draconic Armor").creator((s) -> new DraconicArmorItem(DracoTier.DRACONIC, s)).withItemRenderer(new DraconicArmorItemModel()).withArmorRenderer(new DraconicArmorRenderer()).build();
	public static final Item CHAOTIC_ARMOR  = builder().identifiable("chaotic_armor", "Chaotic Armor").creator((s) -> new DraconicArmorItem(DracoTier.CHAOTIC, s)).withItemRenderer(new DraconicArmorItemModel()).withArmorRenderer(new DraconicArmorRenderer()).build();
	
	public static final Item MODULE_COLORIZER       = builder().identifiable("module_colorizer", "Colorizer Module").creator((s) -> new DracoModuleItem(DracoModules.COLORIZER, s)).generateBasicModel().build();
	public static final Item MODULE_ARMOR_APPEARNCE = builder().identifiable("module_armor_appearance", "Armor Appearance Module").creator((s) -> new DracoModuleItem(DracoModules.ARMOR_APPERANCE, s)).generateBasicModel().build();
	
	public static Set<DIItem> all()
	{
		return Set.copyOf(ITEMS);
	}
	
	static void include(ItemBuilder builder, DIItem item)
	{
		BUILDERS.put(builder, item);
		ITEMS.add(item);
	}
	
	public static ItemBuilder builder()
	{
		return ItemBuilder.create();
	}
	
	public static DIMaterial material(String id, String englishName, double hardness)
	{
		return new DIMaterial(id, englishName, hardness);
	}
	
	public static void init()
	{
		// Load the class
	}
	
	public static void initClient()
	{
		BUILDERS.forEach((b, i) -> b.registerRenderers(i.item()));
	}
}
