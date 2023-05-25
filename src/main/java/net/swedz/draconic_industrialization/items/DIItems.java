package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.api.energy.CableTier;
import com.google.common.collect.Sets;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.material.DIMaterialHardness;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Set;

public final class DIItems
{
	private static final Set<DIItem> ITEMS = Sets.newHashSet();
	
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
	
	public static final Item WYVERN_ARMOR   = builder().identifiable("wyvern_armor", "Wyvern Armor").creator((s) -> new DraconicArmorItem(DracoTier.WYVERN, s)).build();
	public static final Item DRACONIC_ARMOR = builder().identifiable("draconic_armor", "Draconic Armor").creator((s) -> new DraconicArmorItem(DracoTier.DRACONIC, s)).build();
	public static final Item CHAOTIC_ARMOR  = builder().identifiable("chaotic_armor", "Chaotic Armor").creator((s) -> new DraconicArmorItem(DracoTier.CHAOTIC, s)).build();
	
	public static final Item MODULE_COLORIZER       = module("module_colorizer", "Colorizer Module", DracoModules.COLORIZER);
	public static final Item MODULE_ARMOR_APPEARNCE = module("module_armor_appearance", "Armor Appearance Module", DracoModules.ARMOR_APPERANCE);
	public static final Item MODULE_SPEED_WYVERN    = module("module_speed_wyvern", "Speed Amplification Module (Wyvern)", DracoModules.SPEED_WYVERN);
	public static final Item MODULE_SPEED_DRACONIC  = module("module_speed_draconic", "Speed Amplification Module (Draconic)", DracoModules.SPEED_DRACONIC);
	public static final Item MODULE_SPEED_CHAOTIC   = module("module_speed_chaotic", "Speed Amplification Module (Chaotic)", DracoModules.SPEED_CHAOTIC);
	public static final Item MODULE_JUMP_WYVERN     = module("module_jump_wyvern", "Jump Amplification Module (Wyvern)", DracoModules.JUMP_WYVERN);
	public static final Item MODULE_JUMP_DRACONIC   = module("module_jump_draconic", "Jump Amplification Module (Draconic)", DracoModules.JUMP_DRACONIC);
	public static final Item MODULE_JUMP_CHAOTIC    = module("module_jump_chaotic", "Jump Amplification Module (Chaotic)", DracoModules.JUMP_CHAOTIC);
	public static final Item MODULE_FLIGHT          = module("module_flight", "Gravitational Alteration Module", DracoModules.FLIGHT);
	public static final Item MODULE_ENERGY_WYVERN   = module("module_energy_wyvern", "Energy Module (Wyvern)", DracoModules.ENERGY_WYVERN);
	public static final Item MODULE_ENERGY_DRACONIC = module("module_energy_draconic", "Energy Module (Draconic)", DracoModules.ENERGY_DRACONIC);
	public static final Item MODULE_ENERGY_CHAOTIC  = module("module_energy_chaotic", "Energy Module (Chaotic)", DracoModules.ENERGY_CHAOTIC);
	
	public static Set<DIItem> all()
	{
		return Set.copyOf(ITEMS);
	}
	
	static void include(ItemBuilder builder, DIItem item)
	{
		ITEMS.add(item);
	}
	
	private static ItemBuilder builder()
	{
		return ItemBuilder.create();
	}
	
	private static DIMaterial material(String id, String englishName, double hardness)
	{
		return new DIMaterial(id, englishName, hardness);
	}
	
	private static Item module(String id, String name, DracoModuleReference moduleReference)
	{
		return builder()
				.identifiable(id, name)
				.creator((s) -> new DracoModuleItem(moduleReference, s))
				.withSettings((s) -> s
						.maxCount(1)
						.datagenFunction(DatagenFunctions.Client.Item.DRACO_MODULE_MODEL))
				.build();
	}
	
	public static void init()
	{
		// Load the class
	}
}
