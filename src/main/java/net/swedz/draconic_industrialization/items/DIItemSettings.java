package net.swedz.draconic_industrialization.items;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;
import net.swedz.draconic_industrialization.tags.DITags;

import java.util.Set;

public final class DIItemSettings extends FabricItemSettings
{
	public static void ore(DIItemSettings settings)
	{
		settings.tag("c:ores");
	}
	
	public static void draconicArmor(DIItemSettings settings)
	{
		settings.tag(DITags.Items.DRACONIC_ARMOR);
	}
	
	private String englishName;
	
	private MaterialPart materialPart;
	
	private DatagenFunctionContainer<DIItem> datagenFunctions = new DatagenFunctionContainer()
			.add(DatagenFunctions.Server.Item.TAG);
	
	private Set<TagKey<Item>> tags = Sets.newHashSet();
	
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
	
	public DIItemSettings materialPart(DIMaterial material, DIMaterialPart part, RecipeGenerator... recipeGenerators)
	{
		this.materialPart = new MaterialPart(material, part, recipeGenerators);
		return this;
	}
	
	public record MaterialPart(DIMaterial material, DIMaterialPart part, RecipeGenerator... recipeActions)
	{
		public String name()
		{
			return material.id();
		}
		
		public String partId()
		{
			return part.id();
		}
		
		public String tag()
		{
			return DIDatagenClient.tagMaterialTarget(this.name(), this.partId());
		}
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
	
	public Set<TagKey<Item>> tags()
	{
		return Set.copyOf(tags);
	}
	
	public DIItemSettings tag(TagKey<Item> tag)
	{
		tags.add(tag);
		return this;
	}
	
	public DIItemSettings tag(String tag)
	{
		return this.tag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(tag)));
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
