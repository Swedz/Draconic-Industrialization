package net.swedz.draconic_industrialization.blocks;

import com.google.common.collect.Sets;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.recipes.StandardRecipes;

import java.util.Set;

public final class DIBlocks
{
	private static final Set<DIBlock> BLOCKS = Sets.newHashSet();
	
	public static final Block DRACONIUM_BLOCK = builder().material(DIItems.DRACONIUM, StandardRecipes::apply).withSettings(DIBlockProperties.draconium().alwaysDropsSelf()).build();
	public static final Block AWAKENED_DRACONIUM_BLOCK = builder().material(DIItems.AWAKENED_DRACONIUM, StandardRecipes::apply).withSettings(DIBlockProperties.awakenedDraconium().alwaysDropsSelf()).build();
	
	public static final Block DRACONIUM_ORE = builder().material(DIItems.DRACONIUM, DIMaterialPart.ORE).creator((s) -> new DropExperienceBlock(s, UniformInt.of(5, 12))).withSettings(DIBlockProperties.draconiumOre()).withItemSettings(DIItemSettings::ore).build();
	
	public static final Block ADAMANTINE_COIL = builder().material(DIItems.ADAMANTINE, DIMaterialPart.COIL, StandardRecipes::apply).withSettings(DIBlockProperties.adamantine().alwaysDropsSelf()).build();
	
	/*private static final BlockBuilder CRYSTAL_NODE_BUILDER = builder().identifiable("crystal_node", "Crystal Node").withSettings(DIBlockProperties.crystal());
	public static final Block CRYSTAL_NODE = CRYSTAL_NODE_BUILDER.build();
	public static final BlockEntityType<CrystalBlockEntity> CRYSTAL_NODE_ENTITY = CRYSTAL_NODE_BUILDER.buildEntity(CRYSTAL_NODE);*/
	
	/*public static final Block CRYSTAL = block("crystal", "Crystal", DIBlockProperties.crystal().noCollision(), CrystalBlock::new, (s) -> {}, CrystalBlockItem::new);
	public static final BlockEntityType<CrystalBlockEntity> CRYSTAL_ENTITY = blockEntity("crystal", CrystalBlockEntity::new, CRYSTAL);*/
	
	public static Set<DIBlock> all()
	{
		return Set.copyOf(BLOCKS);
	}
	
	static void include(DIBlock block)
	{
		BLOCKS.add(block);
	}
	
	public static BlockBuilder builder()
	{
		return BlockBuilder.create();
	}
	
	public static void init()
	{
		// Load the class
	}
}
