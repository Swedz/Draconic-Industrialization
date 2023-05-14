package net.swedz.draconic_industrialization.datagen.client;

import aztech.modern_industrialization.ModernIndustrialization;
import aztech.modern_industrialization.textures.MITextures;
import aztech.modern_industrialization.textures.TextureManager;
import aztech.modern_industrialization.textures.coloramp.IColoramp;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;
import net.swedz.draconic_industrialization.items.DIItems;
import org.apache.commons.compress.utils.Lists;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static aztech.modern_industrialization.textures.TextureHelper.*;

public final class MaterialPartTextureDataProvider extends DIClientAssetDataProvider
{
	public MaterialPartTextureDataProvider(FabricDataGenerator dataGenerator)
	{
		super(dataGenerator);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void run(CachedOutput output, Path outputPath, Path nonGeneratedPath) throws IOException
	{
		try (MultiPackResourceManager resources = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, this.getPacks()))
		{
			for(DIItem item : DIItems.all())
			{
				final String itemId = item.location().getPath();
				final DIItemSettings settings = item.settings();
				if(settings.isMaterial())
				{
					final DIItemSettings.MaterialPart materialPart = settings.materialPart();
					final String materialName = materialPart.name();
					final String materialPartKey = materialPart.partKey().key().key;
					final String materialSetName = materialPart.materialSet().name;
					
					final String texturePath = "assets/%s/textures/item/materials/%s/%s.png".formatted(
							dataGenerator.getModId(),
							materialName,
							materialPartKey
					);
					if(!Files.exists(nonGeneratedPath.resolve(texturePath)))
					{
						NativeImage texture = generateTexture(
								resources,
								materialSetName,
								materialPartKey,
								new Coloramp(resources, 0, materialName)
						);
						
						byte[] textureBytes = texture.asByteArray();
						output.writeIfNeeded(
								outputPath.resolve(texturePath),
								textureBytes,
								Hashing.sha1().hashBytes(textureBytes)
						);
						texture.close();
					}
				}
			}
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
		File nonGeneratedResources = dataGenerator.getOutputFolder().resolve("../../main/resources").toFile();
		packs.add(new FolderPackResources(nonGeneratedResources));
		
		return packs;
	}
	
	private static NativeImage getTexture(ResourceProvider resources, String textureId) throws IOException
	{
		Resource resource = resources.getResource(new ResourceLocation(textureId))
				.orElseThrow(() -> new IOException("Couldn't find texture " + textureId));
		try (InputStream in = resource.open())
		{
			return NativeImage.read(in);
		}
	}
	
	private static NativeImage generateTexture(ResourceProvider resources, String materialSet, String partTemplate, IColoramp coloramp) throws IOException
	{
		return MITextures.generateTexture(
				new TextureManager(resources, (a, b) -> {}, (a, b) -> {}),
				partTemplate, materialSet, coloramp
		);
	}
	
	@Override
	public String getName()
	{
		return "Material Part Texture Provider";
	}
	
	private static final class Coloramp implements IColoramp
	{
		private final int[] colors = new int[256];
		
		private final int meanRGB;
		
		public Coloramp(ResourceProvider resources, int meanRGB, String name)
		{
			this.meanRGB = meanRGB;
			
			final String gradientMapPath = "draconic_industrialization:textures/gradient_maps/%s.png".formatted(name);
			try (NativeImage gradientMap = MaterialPartTextureDataProvider.getTexture(resources, gradientMapPath))
			{
				for(int i = 0; i < 256; i++)
				{
					int color = gradientMap.getPixelRGBA(i, 0);
					int r = getR(color);
					int g = getG(color);
					int b = getB(color);
					colors[i] = r << 16 | g << 8 | b;
				}
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		
		@Override
		public int getRGB(double luminance)
		{
			return colors[(int) (luminance * 255)];
		}
		
		@Override
		public int getMeanRGB()
		{
			return meanRGB;
		}
	}
}
