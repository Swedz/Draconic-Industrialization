package net.swedz.draconic_industrialization.blocks;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.swedz.draconic_industrialization.api.MCThingBuilder;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIMaterial;
import net.swedz.draconic_industrialization.items.ItemBuilder;
import net.swedz.draconic_industrialization.material.DIMaterialPart;
import net.swedz.draconic_industrialization.recipes.RecipeGenerator;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class BlockBuilder extends MCThingBuilder<Block, DIBlockProperties, BlockBuilder>
{
	public static BlockBuilder create()
	{
		return new BlockBuilder();
	}
	
	@Override
	protected Creator<Block, DIBlockProperties> defaultCreator()
	{
		return Block::new;
	}
	
	@Override
	protected void commonRegister(Block block, DIBlockProperties settings)
	{
		Registry.register(Registry.BLOCK, this.encloseId(), block);
		Item blockItem = blockItemCreator != null ? blockItemCreator.create(block).build() : null;
		DIBlocks.include(new DIBlock(block, blockItem, settings));
	}
	
	private List<Consumer<DIBlockProperties>> scheduledBlockSettingsConsumers = Lists.newArrayList();
	private List<Consumer<DIItemSettings>>    scheduledItemSettingsConsumers  = Lists.newArrayList();
	
	private boolean shouldGenerateBasicModel;
	
	private BlockItemCreator blockItemCreator;
	
	private FabricBlockEntityTypeBuilder.Factory blockEntityFactory;
	
	private BlockBuilder scheduleBlockSettings(Consumer<DIBlockProperties> consumer)
	{
		scheduledBlockSettingsConsumers.add(consumer);
		return this;
	}
	
	private BlockBuilder scheduleItemSettings(Consumer<DIItemSettings> consumer)
	{
		scheduledItemSettingsConsumers.add(consumer);
		return this;
	}
	
	@Override
	public BlockBuilder withSettings(Consumer<DIBlockProperties> settingsConsumer)
	{
		return this.scheduleBlockSettings(settingsConsumer);
	}
	
	public BlockBuilder withItemSettings(Consumer<DIItemSettings> itemSettingsConsumer)
	{
		return this.scheduleItemSettings(itemSettingsConsumer);
	}
	
	public BlockBuilder generateBasicModel()
	{
		shouldGenerateBasicModel = true;
		this.scheduleBlockSettings((s) -> s.datagenFunction(DatagenFunctions.Client.Block.BASIC_MODEL));
		this.scheduleItemSettings((s) -> s.datagenFunction(DatagenFunctions.Client.Item.BASIC_MODEL));
		return this;
	}
	
	public BlockBuilder withItem(BlockItemCreator blockItemCreator)
	{
		this.blockItemCreator = (block) ->
		{
			ItemBuilder builder = blockItemCreator.create(block)
					.identifiable(id, englishName);
			scheduledItemSettingsConsumers.forEach(builder::withSettings);
			return builder;
		};
		return this;
	}
	
	public BlockBuilder withItem()
	{
		return this.withItem((block) -> ItemBuilder.create()
				.creatorBlock(block));
	}
	
	public BlockBuilder withItemCreator(BiFunction<Block, DIItemSettings, Item> creator)
	{
		return this.withItem((block) -> ItemBuilder.create()
				.creator((settings) -> creator.apply(block, settings)));
	}
	
	public BlockBuilder material(DIMaterial material, DIMaterialPart part, RecipeGenerator... recipeGenerators)
	{
		this.identifiable(material.fullId(part), material.fullEnglishName(part));
		this.generateBasicModel();
		this.withItem((block) -> ItemBuilder.create()
				.materialBasic(material, part, recipeGenerators)
				.creatorBlock(block));
		return this;
	}
	
	public BlockBuilder material(DIMaterial material, RecipeGenerator... recipeGenerators)
	{
		return this.material(material, DIMaterialPart.BLOCK, recipeGenerators);
	}
	
	public BlockBuilder withBlockEntityFactory(FabricBlockEntityTypeBuilder.Factory blockEntityFactory)
	{
		this.blockEntityFactory = blockEntityFactory;
		return this;
	}
	
	@Override
	public Block build()
	{
		scheduledBlockSettingsConsumers.forEach((consumer) -> consumer.accept(settings));
		return super.build();
	}
	
	public <T extends BlockEntity> BlockEntityType<T> buildEntity(Class<T> clazz, Block block)
	{
		return Registry.register(
				Registry.BLOCK_ENTITY_TYPE,
				this.encloseId(),
				FabricBlockEntityTypeBuilder.create(blockEntityFactory, block).build()
		);
	}
	
	public interface BlockItemCreator
	{
		ItemBuilder create(Block block);
	}
}
