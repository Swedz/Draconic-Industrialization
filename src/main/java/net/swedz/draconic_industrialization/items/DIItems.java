package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.PartTemplate;
import aztech.modern_industrialization.materials.property.MaterialHardness;
import aztech.modern_industrialization.materials.set.MaterialSet;
import com.google.common.collect.Sets;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorTier;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DIItems
{
	private static final Set<DIItem> ITEMS = Sets.newHashSet();
	
	public static final Item DRACONIC_CIRCUIT           = generic("draconic_circuit", "Draconic Circuit");
	public static final Item WYVERN_CIRCUIT             = generic("wyvern_circuit", "Wyvern Circuit");
	public static final Item AWAKENED_DRACONIUM_CIRCUIT = generic("awakened_draconium_circuit", "Awakened Draconium Circuit");
	public static final Item CHAOTIC_CIRCUIT            = generic("chaotic_circuit", "Chaotic Circuit");
	public static final Item DRAGON_HEART               = generic("dragon_heart", "Dragon Heart", (s) -> s.rarity(Rarity.RARE));
	
	public static final DIMaterial DRACONIUM              = material("draconium", "Draconium", MaterialSet.METALLIC, MaterialHardness.AVERAGE);
	public static final Item       DRACONIUM_TINY_DUST    = materialPart(DRACONIUM, MIParts.TINY_DUST, StandardRecipes::apply);
	public static final Item       DRACONIUM_DUST         = materialPart(DRACONIUM, MIParts.DUST, StandardRecipes::apply);
	public static final Item       DRACONIUM_CRUSHED_DUST = materialPart(DRACONIUM, MIParts.CRUSHED_DUST, StandardRecipes::apply);
	public static final Item       DRACONIUM_INGOT        = materialPart(DRACONIUM, MIParts.INGOT, StandardRecipes::apply);
	public static final Item       DRACONIUM_PLATE        = materialPart(DRACONIUM, MIParts.PLATE, StandardRecipes::apply);
	public static final Item       DRACONIUM_CURVED_PLATE = materialPart(DRACONIUM, MIParts.CURVED_PLATE, StandardRecipes::apply);
	public static final Item       DRACONIUM_WIRE         = materialPart(DRACONIUM, MIParts.WIRE, StandardRecipes::apply);
	
	public static final DIMaterial ADAMANTINE           = material("adamantine", "Adamantine", MaterialSet.METALLIC, MaterialHardness.VERY_HARD);
	public static final Item       ADAMANTINE_TINY_DUST = materialPart(ADAMANTINE, MIParts.TINY_DUST, StandardRecipes::apply);
	public static final Item       ADAMANTINE_DUST      = materialPart(ADAMANTINE, MIParts.DUST, StandardRecipes::apply);
	public static final Item       ADAMANTINE_INGOT     = materialPart(ADAMANTINE, MIParts.INGOT, StandardRecipes::apply);
	public static final Item       ADAMANTINE_PLATE     = materialPart(ADAMANTINE, MIParts.PLATE, StandardRecipes::apply);
	
	public static final DIMaterial AWAKENED_DRACONIUM              = material("awakened_draconium", "Awakened Draconium", MaterialSet.METALLIC, MaterialHardness.HARD);
	public static final Item       AWAKENED_DRACONIUM_TINY_DUST    = materialPart(AWAKENED_DRACONIUM, MIParts.TINY_DUST, StandardRecipes::apply);
	public static final Item       AWAKENED_DRACONIUM_DUST         = materialPart(AWAKENED_DRACONIUM, MIParts.DUST, StandardRecipes::apply);
	public static final Item       AWAKENED_DRACONIUM_INGOT        = materialPart(AWAKENED_DRACONIUM, MIParts.INGOT, StandardRecipes::apply);
	public static final Item       AWAKENED_DRACONIUM_PLATE        = materialPart(AWAKENED_DRACONIUM, MIParts.PLATE, StandardRecipes::apply);
	public static final Item       AWAKENED_DRACONIUM_CURVED_PLATE = materialPart(AWAKENED_DRACONIUM, MIParts.CURVED_PLATE, StandardRecipes::apply);
	public static final Item       AWAKENED_DRACONIUM_WIRE         = materialPart(AWAKENED_DRACONIUM, MIParts.WIRE, StandardRecipes::apply);
	
	public static final Item WYVERN_ARMOR   = item("wyvern_armor", "Wyvern Armor", (s) -> new DraconicArmorItem(DraconicArmorTier.WYVERN, s), DIItemSettings::draconicArmor);
	public static final Item DRACONIC_ARMOR = item("draconic_armor", "Draconic Armor", (s) -> new DraconicArmorItem(DraconicArmorTier.DRACONIC, s), DIItemSettings::draconicArmor);
	public static final Item CHAOTIC_ARMOR  = item("chaotic_armor", "Chaotic Armor", (s) -> new DraconicArmorItem(DraconicArmorTier.CHAOTIC, s), DIItemSettings::draconicArmor);
	
	public static Set<DIItem> all()
	{
		return Set.copyOf(ITEMS);
	}
	
	public static Item register(String id, Item item, DIItemSettings settings)
	{
		Registry.register(Registry.ITEM, DraconicIndustrialization.id(id), item);
		ITEMS.add(new DIItem(item, settings));
		return item;
	}
	
	public static Item item(String id, String englishName, Function<DIItemSettings, Item> itemCreator, Consumer<DIItemSettings> settingsConsumer)
	{
		final DIItemSettings settings = new DIItemSettings()
				.englishName(englishName)
				.tab(DraconicIndustrialization.CREATIVE_TAB);
		settingsConsumer.accept(settings);
		return register(id, itemCreator.apply(settings), settings);
	}
	
	public static Item item(String id, String englishName, Function<DIItemSettings, Item> itemCreator)
	{
		return item(id, englishName, itemCreator, (s) ->
		{
		});
	}
	
	public static Item blockItem(String id, String englishName, Block block, Consumer<DIItemSettings> settingsConsumer)
	{
		final DIItemSettings settings = new DIItemSettings()
				.englishName(englishName)
				.tab(DraconicIndustrialization.CREATIVE_TAB)
				.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL);
		settingsConsumer.accept(settings);
		return register(id, new BlockItem(block, settings), settings);
	}
	
	public static Item blockItemMaterialPart(Block block, DIMaterial material, RecipeGenerator... recipeActions)
	{
		final PartTemplate partTemplate = MIParts.BLOCK.of(null);
		final String id = material.fullId(partTemplate);
		return blockItem(id, material.fullEnglishName(partTemplate), block, (s) -> s
				.materialPart(material, partTemplate, recipeActions)
				.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_RECIPE)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_TAG));
	}
	
	public static Item generic(String id, String englishName, Consumer<DIItemSettings> settingsConsumer)
	{
		final DIItemSettings settings = new DIItemSettings()
				.englishName(englishName)
				.tab(DraconicIndustrialization.CREATIVE_TAB)
				.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL);
		settingsConsumer.accept(settings);
		return register(id, new Item(settings), settings);
	}
	
	public static Item generic(String id, String englishName)
	{
		return generic(id, englishName, (settings) ->
		{
		});
	}
	
	public static DIMaterial material(String id, String englishName, MaterialSet materialSet, MaterialHardness hardness)
	{
		return new DIMaterial(id, englishName, materialSet, hardness);
	}
	
	public static Item materialPart(DIMaterial material, PartTemplate part, RecipeGenerator... recipeActions)
	{
		final String id = material.fullId(part);
		final String fullEnglishName = material.fullEnglishName(part);
		return generic(id, fullEnglishName, (s) -> s
				.materialPart(material, part, recipeActions)
				.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL)
				.datagenFunction(DatagenFunctions.Client.Item.MATERIAL_PART_TEXTURE)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_RECIPE)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_TAG));
	}
	
	public static void init()
	{
		// Load the class
	}
}
