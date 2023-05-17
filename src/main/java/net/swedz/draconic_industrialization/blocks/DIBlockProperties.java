package net.swedz.draconic_industrialization.blocks;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.DIItems;

import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public final class DIBlockProperties extends FabricBlockSettings
{
	public static DIBlockProperties draconium()
	{
		return DIBlockProperties
				.of(Material.METAL, MaterialColor.COLOR_PURPLE)
				.sounds(SoundType.METAL)
				.hardness(5f).resistance(6f)
				.requiresTool()
				.needsPickaxe()
				.needsDiamond();
	}
	
	public static DIBlockProperties awakenedDraconium()
	{
		return DIBlockProperties
				.of(Material.METAL, MaterialColor.COLOR_ORANGE)
				.sounds(SoundType.METAL)
				.hardness(50f).resistance(1200f)
				.requiresTool()
				.needsPickaxe()
				.needsDiamond();
	}
	
	public static DIBlockProperties adamantine()
	{
		return DIBlockProperties
				.of(Material.METAL, MaterialColor.COLOR_LIGHT_GREEN)
				.sounds(SoundType.METAL)
				.hardness(50f).resistance(1200f)
				.requiresTool()
				.needsPickaxe()
				.needsDiamond();
	}
	
	public static DIBlockProperties draconiumOre()
	{
		return DIBlockProperties
				.of(Material.STONE, MaterialColor.COLOR_GRAY)
				.sounds(SoundType.STONE)
				.hardness(6f).resistance(16f)
				.requiresTool()
				.needsPickaxe()
				.needsDiamond()
				.tag("c:ores").tag("c:draconium_ores")
				.lootTable((b) -> BlockLoot.createSilkTouchDispatchTable(
								b.block(),
								BlockLoot.applyExplosionDecay(
										b.block(),
										LootItem.lootTableItem(DIItems.DRACONIUM_DUST)
												.apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 5)))
												.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
								)
						)
						.build());
	}
	
	public static DIBlockProperties crystal()
	{
		return DIBlockProperties
				.of(Material.GLASS, MaterialColor.COLOR_GRAY)
				.sounds(SoundType.GLASS)
				.hardness(1f).resistance(1f)
				.requiresTool()
				.needsPickaxe()
				.needsDiamond();
	}
	
	private String englishName;
	
	private DatagenFunctionContainer<DIBlock> datagenFunctions = new DatagenFunctionContainer()
			.add(DatagenFunctions.Server.Block.LOOT_TABLE)
			.add(DatagenFunctions.Server.Block.TAG);
	
	private Set<TagKey<Block>> tags = Sets.newHashSet();
	
	private Function<DIBlock, LootTable> lootTableFunction;
	
	//region Initialization methods
	private DIBlockProperties(Material material, MaterialColor color)
	{
		super(material, color);
	}
	
	private DIBlockProperties(BlockBehaviour.Properties settings)
	{
		super(settings);
	}
	
	public static DIBlockProperties of(Material material)
	{
		return of(material, material.getColor());
	}
	
	public static DIBlockProperties of(Material material, MaterialColor color)
	{
		return new DIBlockProperties(material, color);
	}
	
	public static DIBlockProperties of(Material material, DyeColor color)
	{
		return new DIBlockProperties(material, color.getMaterialColor());
	}
	
	public static DIBlockProperties copyOf(BlockBehaviour behaviour)
	{
		return new DIBlockProperties(((AbstractBlockAccessor) behaviour).getSettings());
	}
	
	public static DIBlockProperties copyOf(BlockBehaviour.Properties properties)
	{
		return new DIBlockProperties(properties);
	}
	//endregion
	
	public String englishName()
	{
		return englishName;
	}
	
	public DIBlockProperties englishName(String englishName)
	{
		this.englishName = englishName;
		return this;
	}
	
	public DatagenFunctionContainer<DIBlock> datagenFunctions()
	{
		return datagenFunctions;
	}
	
	public DIBlockProperties datagenFunction(DatagenFunction<DIBlock> function)
	{
		datagenFunctions.add(function);
		return this;
	}
	
	public Set<TagKey<Block>> tags()
	{
		return Set.copyOf(tags);
	}
	
	public DIBlockProperties tag(TagKey<Block> tag)
	{
		tags.add(tag);
		return this;
	}
	
	public DIBlockProperties tag(String tag)
	{
		return this.tag(TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(tag)));
	}
	
	public DIBlockProperties needsPickaxe()
	{
		return this.tag(BlockTags.MINEABLE_WITH_PICKAXE);
	}
	
	public DIBlockProperties needsAxe()
	{
		return this.tag(BlockTags.MINEABLE_WITH_AXE);
	}
	
	public DIBlockProperties needsShovel()
	{
		return this.tag(BlockTags.MINEABLE_WITH_SHOVEL);
	}
	
	public DIBlockProperties needsHoe()
	{
		return this.tag(BlockTags.MINEABLE_WITH_HOE);
	}
	
	public DIBlockProperties needsStone()
	{
		return this.tag(BlockTags.NEEDS_STONE_TOOL);
	}
	
	public DIBlockProperties needsIron()
	{
		return this.tag(BlockTags.NEEDS_IRON_TOOL);
	}
	
	public DIBlockProperties needsDiamond()
	{
		return this.tag(BlockTags.NEEDS_DIAMOND_TOOL);
	}
	
	public LootTable lootTable(DIBlock block)
	{
		return lootTableFunction != null ? lootTableFunction.apply(block) : null;
	}
	
	public DIBlockProperties lootTable(Function<DIBlock, LootTable> function)
	{
		this.lootTableFunction = function;
		return this;
	}
	
	public DIBlockProperties alwaysDropsSelf()
	{
		return this.lootTable((b) -> BlockLoot.createSingleItemTable(b.item()).build());
	}
	
	//region Inherited methods
	@Override
	public DIBlockProperties noCollision()
	{
		super.noCollision();
		return this;
	}
	
	@Override
	public DIBlockProperties nonOpaque()
	{
		super.nonOpaque();
		return this;
	}
	
	@Override
	public DIBlockProperties slipperiness(float value)
	{
		super.slipperiness(value);
		return this;
	}
	
	@Override
	public DIBlockProperties velocityMultiplier(float velocityMultiplier)
	{
		super.velocityMultiplier(velocityMultiplier);
		return this;
	}
	
	@Override
	public DIBlockProperties jumpVelocityMultiplier(float jumpVelocityMultiplier)
	{
		super.jumpVelocityMultiplier(jumpVelocityMultiplier);
		return this;
	}
	
	@Override
	public DIBlockProperties sounds(SoundType group)
	{
		super.sounds(group);
		return this;
	}
	
	@Deprecated
	@SuppressWarnings("deprecation")
	@Override
	public DIBlockProperties lightLevel(ToIntFunction<BlockState> levelFunction)
	{
		super.lightLevel(levelFunction);
		return this;
	}
	
	@Override
	public DIBlockProperties luminance(ToIntFunction<BlockState> luminanceFunction)
	{
		super.luminance(luminanceFunction);
		return this;
	}
	
	@Override
	public DIBlockProperties strength(float hardness, float resistance)
	{
		super.strength(hardness, resistance);
		return this;
	}
	
	@Override
	public DIBlockProperties breakInstantly()
	{
		super.breakInstantly();
		return this;
	}
	
	@Override
	public DIBlockProperties strength(float strength)
	{
		super.strength(strength);
		return this;
	}
	
	@Override
	public DIBlockProperties ticksRandomly()
	{
		super.ticksRandomly();
		return this;
	}
	
	@Override
	public DIBlockProperties dynamicBounds()
	{
		super.dynamicBounds();
		return this;
	}
	
	@Override
	public DIBlockProperties dropsLike(Block block)
	{
		super.dropsLike(block);
		return this;
	}
	
	@Override
	public DIBlockProperties air()
	{
		super.air();
		return this;
	}
	
	@Override
	public DIBlockProperties allowsSpawning(BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate)
	{
		super.allowsSpawning(predicate);
		return this;
	}
	
	@Override
	public DIBlockProperties solidBlock(BlockBehaviour.StatePredicate predicate)
	{
		super.solidBlock(predicate);
		return this;
	}
	
	@Override
	public DIBlockProperties suffocates(BlockBehaviour.StatePredicate predicate)
	{
		super.suffocates(predicate);
		return this;
	}
	
	@Override
	public DIBlockProperties blockVision(BlockBehaviour.StatePredicate predicate)
	{
		super.blockVision(predicate);
		return this;
	}
	
	@Override
	public DIBlockProperties postProcess(BlockBehaviour.StatePredicate predicate)
	{
		super.postProcess(predicate);
		return this;
	}
	
	@Override
	public DIBlockProperties emissiveLighting(BlockBehaviour.StatePredicate predicate)
	{
		super.emissiveLighting(predicate);
		return this;
	}
	
	@Deprecated
	@SuppressWarnings("deprecation")
	@Override
	public DIBlockProperties lightLevel(int lightLevel)
	{
		super.lightLevel(lightLevel);
		return this;
	}
	
	@Override
	public DIBlockProperties luminance(int luminance)
	{
		super.luminance(luminance);
		return this;
	}
	
	@Override
	public DIBlockProperties hardness(float hardness)
	{
		super.hardness(hardness);
		return this;
	}
	
	@Override
	public DIBlockProperties resistance(float resistance)
	{
		super.resistance(resistance);
		return this;
	}
	
	@Override
	public DIBlockProperties drops(ResourceLocation dropTableId)
	{
		super.drops(dropTableId);
		return this;
	}
	
	@Override
	public DIBlockProperties requiresTool()
	{
		super.requiresTool();
		return this;
	}
	
	@Deprecated
	@SuppressWarnings("deprecation")
	@Override
	public DIBlockProperties materialColor(MaterialColor color)
	{
		super.materialColor(color);
		return this;
	}
	
	@Deprecated
	@SuppressWarnings("deprecation")
	@Override
	public DIBlockProperties materialColor(DyeColor color)
	{
		super.materialColor(color);
		return this;
	}
	
	@Override
	public DIBlockProperties mapColor(MaterialColor color)
	{
		super.mapColor(color);
		return this;
	}
	
	@Override
	public DIBlockProperties mapColor(DyeColor color)
	{
		super.mapColor(color);
		return this;
	}
	
	@Override
	public DIBlockProperties collidable(boolean collidable)
	{
		super.collidable(collidable);
		return this;
	}
	//endregion
}
