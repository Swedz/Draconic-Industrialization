package net.swedz.draconic_industrialization.items;

import aztech.modern_industrialization.api.energy.CableTier;
import aztech.modern_industrialization.pipes.MIPipes;
import aztech.modern_industrialization.pipes.api.PipeNetworkType;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetwork;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetworkData;
import aztech.modern_industrialization.pipes.electricity.ElectricityNetworkNode;
import aztech.modern_industrialization.pipes.impl.PipeItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.MCThingBuilder;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;

public final class ItemBuilder extends MCThingBuilder<Item, DIItemSettings, ItemBuilder>
{
	public static ItemBuilder create()
	{
		return new ItemBuilder();
	}
	
	@Override
	public ItemBuilder identifiable(String id, String englishName)
	{
		super.identifiable(id, englishName);
		settings.englishName(englishName);
		return this;
	}
	
	@Override
	protected DIItemSettings defaultSettings()
	{
		return new DIItemSettings().tab(DraconicIndustrialization.CREATIVE_TAB);
	}
	
	@Override
	protected Creator<Item, DIItemSettings> defaultCreator()
	{
		return Item::new;
	}
	
	@Override
	protected void commonRegister(Item item, DIItemSettings settings)
	{
		Registry.register(Registry.ITEM, this.encloseId(), item);
		DIItems.include(this, new DIItem(item, settings));
	}
	
	public ItemBuilder excludeFromCreativeTab()
	{
		settings.tab(null);
		return this;
	}
	
	public ItemBuilder generateBasicModel()
	{
		settings.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL);
		return this;
	}
	
	public ItemBuilder material(DIMaterial material, DIMaterialPart part, RecipeGenerator... recipeGenerators)
	{
		this.identifiable(material.fullId(part), material.fullEnglishName(part));
		settings.materialPart(material, part, recipeGenerators)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_RECIPE)
				.datagenFunction(DatagenFunctions.Server.Item.MATERIAL_TAG);
		return this;
	}
	
	public ItemBuilder materialBasic(DIMaterial material, DIMaterialPart part, RecipeGenerator... recipeGenerators)
	{
		this.material(material, part, recipeGenerators);
		this.generateBasicModel();
		return this;
	}
	
	public ItemBuilder materialCable(DIMaterial material, int color, CableTier tier, RecipeGenerator... recipeGenerators)
	{
		this.material(material, DIMaterialPart.CABLE, recipeGenerators);
		this.creator((settings) ->
		{
			PipeNetworkType type = PipeNetworkType.register(DraconicIndustrialization.id(id),
					(identifier, data) -> new ElectricityNetwork(identifier, data, tier),
					ElectricityNetworkNode::new, color | 0xff000000, false
			);
			PipeItem item = new PipeItem(settings, type, new ElectricityNetworkData());
			MIPipes.INSTANCE.register(type, item);
			MIPipes.ELECTRICITY_PIPE_TIER.put(item, tier);
			MIPipes.PIPE_MODEL_NAMES.add(DraconicIndustrialization.id("item/" + id));
			return item;
		});
		return this;
	}
	
	public ItemBuilder creatorBlock(Block block)
	{
		return this.creator((s) -> new BlockItem(block, s));
	}
}
