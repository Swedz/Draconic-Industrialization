package net.swedz.draconic_industrialization.blocks;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.blocks.block.crystal.CrystalBlock;
import net.swedz.draconic_industrialization.blocks.block.crystal.CrystalBlockEntity;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.items.DIMaterial;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DIBlocks
{
	private static final Set<DIBlock> BLOCKS = Sets.newHashSet();
	
	public static final Block DRACONIUM_BLOCK          = materialPart(DIItems.DRACONIUM, DIBlockProperties.draconium().alwaysDropsSelf(), StandardRecipes::apply);
	public static final Block AWAKENED_DRACONIUM_BLOCK = materialPart(DIItems.AWAKENED_DRACONIUM, DIBlockProperties.awakenedDraconium().alwaysDropsSelf(), StandardRecipes::apply);
	
	public static final Block DRACONIUM_ORE = generic("draconium_ore", "Draconium Ore", (p) -> new DropExperienceBlock(p, UniformInt.of(5, 12)), DIBlockProperties.draconiumOre(), DIItemSettings::draconiumOre, true);
	
	public static final Block ADAMANTINE_COIL = materialPart(DIItems.ADAMANTINE, DIMaterialPart.COIL, DIBlockProperties.adamantine().alwaysDropsSelf(), StandardRecipes::apply);
	
	public static final Block CRYSTAL = block("crystal", "Crystal", CrystalBlock::new, DIBlockProperties.crystal().noCollision(), (s) -> {}, true);
	public static final BlockEntityType<CrystalBlockEntity> CRYSTAL_ENTITY = blockEntity("crystal", CrystalBlockEntity::new, CRYSTAL);
	
	public static Set<DIBlock> all()
	{
		return Set.copyOf(BLOCKS);
	}
	
	public static Block register(Block block, Item blockItem, DIBlockProperties properties)
	{
		BLOCKS.add(new DIBlock(block, blockItem, properties));
		return block;
	}
	
	public static Block block(String id, String englishName, Function<DIBlockProperties, Block> blockFunction, DIBlockProperties properties, Consumer<DIItemSettings> itemSettings, boolean createItem)
	{
		Block block = Registry.register(
				Registry.BLOCK,
				DraconicIndustrialization.id(id),
				blockFunction.apply(properties)
		);
		Item blockItem = createItem ? DIItems.blockItem(id, englishName, block, itemSettings) : null;
		return register(block, blockItem, properties);
	}
	
	public static Block generic(String id, String englishName, Function<DIBlockProperties, Block> blockFunction, DIBlockProperties properties, Consumer<DIItemSettings> itemSettings, boolean createItem)
	{
		properties.datagenFunction(DatagenFunctions.Client.Block.BASIC_MODEL);
		return block(id, englishName, blockFunction, properties, itemSettings, createItem);
	}
	
	public static Block generic(String id, String englishName, DIBlockProperties properties, Consumer<DIItemSettings> itemSettings, boolean createItem)
	{
		return generic(id, englishName, Block::new, properties, itemSettings, createItem);
	}
	
	public static Block materialPart(DIMaterial material, DIMaterialPart part, DIBlockProperties properties, RecipeGenerator... recipeActions)
	{
		final String id = material.fullId(part);
		properties.datagenFunction(DatagenFunctions.Client.Block.BASIC_MODEL);
		Block block = Registry.register(
				Registry.BLOCK,
				DraconicIndustrialization.id(id),
				new Block(properties)
		);
		Item blockItem = DIItems.blockItemMaterialPart(block, material, part, recipeActions);
		return register(block, blockItem, properties);
	}
	
	public static Block materialPart(DIMaterial material, DIBlockProperties properties, RecipeGenerator... recipeActions)
	{
		return materialPart(material, DIMaterialPart.BLOCK, properties, recipeActions);
	}
	
	public static <T extends BlockEntity> BlockEntityType<T> blockEntity(String id, FabricBlockEntityTypeBuilder.Factory blockEntityFactory, Block... blocks)
	{
		return Registry.register(
				Registry.BLOCK_ENTITY_TYPE,
				DraconicIndustrialization.id(id),
				FabricBlockEntityTypeBuilder.create(blockEntityFactory, blocks).build()
		);
	}
	
	public static void init()
	{
		// Load the class
	}
}
