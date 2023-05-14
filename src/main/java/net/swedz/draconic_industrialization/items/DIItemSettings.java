package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.materials.part.PartKeyProvider;
import aztech.modern_industrialization.materials.set.MaterialSet;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;

public final class DIItemSettings extends FabricItemSettings
{
	private String englishName;
	
	private MaterialPart materialPart;
	
	private DatagenFunctionContainer<DIItem> datagenFunctions = new DatagenFunctionContainer();
	
	public String englishName()
	{
		return englishName;
	}
	
	public DIItemSettings englishName(String englishName)
	{
		this.englishName = englishName;
		return this;
	}
	
	public boolean isMaterial()
	{
		return materialPart != null;
	}
	
	public MaterialPart materialPart()
	{
		return materialPart;
	}
	
	public DIItemSettings materialPart(String name, PartKeyProvider partKey, MaterialSet materialSet)
	{
		this.materialPart = new MaterialPart(name, partKey, materialSet);
		return this;
	}
	
	public DatagenFunctionContainer<DIItem> datagenFunctions()
	{
		return datagenFunctions;
	}
	
	public DIItemSettings datagenFunction(DatagenFunction<DIItem> function)
	{
		datagenFunctions.add(function);
		return this;
	}
	
	public record MaterialPart(String name, PartKeyProvider partKey, MaterialSet materialSet)
	{
		public String partId()
		{
			return partKey.key().key;
		}
	}
	
	//region Inherited methods
	@Override
	public DIItemSettings equipmentSlot(EquipmentSlotProvider equipmentSlotProvider)
	{
		super.equipmentSlot(equipmentSlotProvider);
		return this;
	}
	
	@Override
	public DIItemSettings customDamage(CustomDamageHandler handler)
	{
		super.customDamage(handler);
		return this;
	}
	
	@Override
	public DIItemSettings food(FoodProperties foodComponent)
	{
		super.food(foodComponent);
		return this;
	}
	
	@Override
	public DIItemSettings maxCount(int maxCount)
	{
		super.maxCount(maxCount);
		return this;
	}
	
	@Override
	public DIItemSettings maxDamageIfAbsent(int maxDamage)
	{
		super.maxDamageIfAbsent(maxDamage);
		return this;
	}
	
	@Override
	public DIItemSettings maxDamage(int maxDamage)
	{
		super.maxDamage(maxDamage);
		return this;
	}
	
	@Override
	public DIItemSettings recipeRemainder(Item recipeRemainder)
	{
		super.recipeRemainder(recipeRemainder);
		return this;
	}
	
	@Override
	public DIItemSettings group(CreativeModeTab group)
	{
		super.group(group);
		return this;
	}
	
	@Override
	public DIItemSettings rarity(Rarity rarity)
	{
		super.rarity(rarity);
		return this;
	}
	
	@Override
	public DIItemSettings fireproof()
	{
		super.fireproof();
		return this;
	}
	
	@Override
	public DIItemSettings stacksTo(int maxStackSize)
	{
		super.stacksTo(maxStackSize);
		return this;
	}
	
	@Override
	public DIItemSettings defaultDurability(int maxDamage)
	{
		super.defaultDurability(maxDamage);
		return this;
	}
	
	@Override
	public DIItemSettings durability(int maxDamage)
	{
		super.durability(maxDamage);
		return this;
	}
	
	@Override
	public DIItemSettings craftRemainder(Item craftingRemainingItem)
	{
		super.craftRemainder(craftingRemainingItem);
		return this;
	}
	
	@Override
	public DIItemSettings tab(CreativeModeTab category)
	{
		super.tab(category);
		return this;
	}
	
	@Override
	public DIItemSettings fireResistant()
	{
		super.fireResistant();
		return this;
	}
	//endregion
}
