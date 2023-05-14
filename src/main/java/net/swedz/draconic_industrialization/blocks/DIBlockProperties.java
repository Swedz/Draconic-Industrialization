package net.swedz.draconic_industrialization.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;

import java.util.function.ToIntFunction;

public final class DIBlockProperties extends FabricBlockSettings
{
	private String englishName;
	
	private DatagenFunctionContainer<DIBlock> datagenFunctions = new DatagenFunctionContainer();
	
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
