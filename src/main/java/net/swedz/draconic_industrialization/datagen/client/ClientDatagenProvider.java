package net.swedz.draconic_industrialization.datagen.client;

import aztech.modern_industrialization.machines.blockentities.multiblocks.ElectricBlastFurnaceBlockEntity.Tier;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.resources.AssetIndex;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.client.resources.DefaultClientPackResources;
import net.minecraft.data.CachedOutput;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.blocks.DIBlock;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.mi.DIBlastFurnaceTiers;
import net.swedz.draconic_industrialization.particles.DIParticles;
import net.swedz.draconic_industrialization.particles.ParticleWrapper;

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
		try (final MultiPackResourceManager resourceProvider = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, this.getPacks()))
		{
			final JsonObject langJson = new JsonObject();
			
			DraconicIndustrialization.LOGGER.info("Start of PARTICLE");
			DatagenFunctions.Client.Particle.INSTANCE.globalInit(this, output);
			for(ParticleWrapper particle : DIParticles.all())
			{
				DraconicIndustrialization.LOGGER.info("Running functions for particle {}", particle.id());
				
				particle.datagenFunctions().executeAll(DatagenFunctionCategory.PARTICLE_CLIENT, this, output, resourceProvider, particle);
			}
			DatagenFunctions.Client.Particle.INSTANCE.globalAfter(this, output);
			DraconicIndustrialization.LOGGER.info("End of BLOCK");
			
			DraconicIndustrialization.LOGGER.info("Start of BLOCK");
			DatagenFunctions.Client.Block.INSTANCE.globalInit(this, output);
			for(DIBlock block : DIBlocks.all())
			{
				DraconicIndustrialization.LOGGER.info("Running functions for block {}", block.id(true));
				block.properties().datagenFunctions().executeAll(DatagenFunctionCategory.BLOCK_CLIENT, this, output, resourceProvider, block);
			}
			DatagenFunctions.Client.Block.INSTANCE.globalAfter(this, output);
			DraconicIndustrialization.LOGGER.info("End of BLOCK");
			
			DraconicIndustrialization.LOGGER.info("Start of ITEM");
			DatagenFunctions.Client.Item.INSTANCE.globalInit(this, output);
			for(DIItem item : DIItems.all())
			{
				DraconicIndustrialization.LOGGER.info("Running functions for item {}", item.id(true));
				item.settings().datagenFunctions().executeAll(DatagenFunctionCategory.ITEM_CLIENT, this, output, resourceProvider, item);
				
				DraconicIndustrialization.LOGGER.info("Added item {} to language file", item.id(true));
				langJson.addProperty(item.item().getDescriptionId(), item.settings().englishName());
			}
			DatagenFunctions.Client.Item.INSTANCE.globalAfter(this, output);
			DraconicIndustrialization.LOGGER.info("End of ITEM");
			
			DraconicIndustrialization.LOGGER.info("Start of EBFTIER");
			for(Tier tier : DIBlastFurnaceTiers.all())
			{
				DraconicIndustrialization.LOGGER.info("Added EBF tier {} to language file", tier.englishName());
				langJson.addProperty(tier.getTranslationKey(), tier.englishName());
				langJson.addProperty(
						"rei_categories.modern_industrialization.electric_blast_furnace_%s".formatted(tier.coilBlockId().getPath()),
						"EBF (%s Tier)".formatted(tier.englishName())
				);
			}
			DraconicIndustrialization.LOGGER.info("Start of EBFTIER");
			
			DraconicIndustrialization.LOGGER.info("Writing LANG");
			Map<String, String> langEntries = Maps.newHashMap();
			LangDatagen.add(langEntries);
			langEntries.forEach(langJson::addProperty);
			this.writeJsonIfNotExist(output, "assets/%s/lang/en_us.json".formatted(dataGenerator.getModId()), langJson);
			DraconicIndustrialization.LOGGER.info("Completed writing LANG");
		}
	}
	
	private List<PackResources> getPacks()
	{
		final List<PackResources> packs = Lists.newArrayList();
		
		// Vanilla Assets
		packs.add(new DefaultClientPackResources(ClientPackSource.BUILT_IN, new AssetIndex(new File(""), "")));
		
		// Draconic Industrialization Assets
		packs.add(new FolderPackResources(this.nonGeneratedPath().toFile()));
		
		return packs;
	}
}
