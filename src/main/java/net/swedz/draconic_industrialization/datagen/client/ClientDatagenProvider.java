package net.swedz.draconic_industrialization.datagen.client;

import aztech.modern_industrialization.ModernIndustrialization;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.DefaultClientPackResources;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItems;
import org.apache.commons.compress.utils.Lists;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class ClientDatagenProvider extends DatagenProvider
{
	ClientDatagenProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator, "Client");
	}
	
	@Override
	public void run(CachedOutput output) throws IOException
	{
		try(final MultiPackResourceManager resourceProvider = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, this.getPacks()))
		{
			final JsonObject langJson = new JsonObject();
			
			DraconicIndustrialization.LOGGER.info("Start of ITEM");
			for(DIItem item : DIItems.all())
			{
				DraconicIndustrialization.LOGGER.info("Running functions for item {}", item.id(true));
				item.settings().datagenFunctions().executeAll(DatagenFunctionCategory.ITEM_CLIENT, this, output, resourceProvider, item);
				
				DraconicIndustrialization.LOGGER.info("Added item {} to language file", item.id(true));
				langJson.addProperty(item.item().getDescriptionId(), item.settings().englishName());
			}
			DraconicIndustrialization.LOGGER.info("End of ITEM");
			
			DraconicIndustrialization.LOGGER.info("Writing LANG");
			Map<String, String> langEntries = Maps.newHashMap();
			LangDatagen.add(langEntries);
			langEntries.forEach(langJson::addProperty);
			this.writeJsonIfNotExist(output, "assets/%s/lang/en_us.json".formatted(dataGenerator.getModId()), langJson);
			DraconicIndustrialization.LOGGER.info("Completed writing LANG");
			
			DraconicIndustrialization.LOGGER.info("Start of BLOCK");
			for(DIBlock block : DIBlocks.all())
			{
				DraconicIndustrialization.LOGGER.info("Running functions for block {}", block.id(true));
				block.properties().datagenFunctions().executeAll(DatagenFunctionCategory.BLOCK_CLIENT, this, output, resourceProvider, block);
			}
			DraconicIndustrialization.LOGGER.info("End of BLOCK");
		}
	}
	
	private List<PackResources> getPacks()
	{
		final List<PackResources> packs = Lists.newArrayList();
		
		// Vanilla Assets
		packs.add(new DefaultClientPackResources(ClientPackSource.BUILT_IN, new AssetIndex(new File(""), "")));
		
		// Modern Industrialization Assets
		ModContainer container = FabricLoader.getInstance().getModContainer(ModernIndustrialization.MOD_ID).orElseThrow();
		packs.add(ModNioResourcePack.create(new ResourceLocation("fabric", container.getMetadata().getId()),
				container.getMetadata().getName(), container, null, PackType.CLIENT_RESOURCES, ResourcePackActivationType.ALWAYS_ENABLED
		));
		
		// Draconic Industrialization Assets
		packs.add(new FolderPackResources(this.nonGeneratedPath().toFile()));
		
		return packs;
	}
}
