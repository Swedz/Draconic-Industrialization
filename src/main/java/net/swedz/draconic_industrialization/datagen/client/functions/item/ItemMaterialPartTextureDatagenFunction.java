package net.swedz.draconic_industrialization.datagen.client.functions.item;

import aztech.modern_industrialization.textures.MITextures;
import aztech.modern_industrialization.textures.TextureManager;
import aztech.modern_industrialization.textures.coloramp.IColoramp;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.swedz.draconic_industrialization.datagen.api.ClientDatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;
import net.swedz.draconic_industrialization.items.DIItem;
import net.swedz.draconic_industrialization.items.DIItemSettings;

import java.io.IOException;
import java.io.InputStream;

import static aztech.modern_industrialization.textures.TextureHelper.*;

public final class ItemMaterialPartTextureDatagenFunction extends ClientDatagenFunction<DIItem>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.ITEM_CLIENT;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, DIItem item) throws IOException
	{
		final DIItemSettings settings = item.settings();
		if(item.isBlock())
		{
			return;
		}
		if(!settings.isMaterial())
		{
			throw new IllegalArgumentException("Provided non-material item to ItemMaterialPartTextureDatagenFunction '%s'".formatted(item.id(true)));
		}
		final DIItemSettings.MaterialPart materialPart = settings.materialPart();
		final String materialName = materialPart.name();
		final String materialPartKey = materialPart.partId();
		final String materialSetName = materialPart.materialSet().name;
		
		final String texturePath = DIDatagenClient.itemTexturePath(item.modId(), "%s_%s".formatted(materialName, materialPartKey));
		NativeImage texture = generateTexture(
				resourceProvider,
				materialSetName,
				materialPartKey,
				new Coloramp(resourceProvider, 0, materialName)
		);
		provider.writeImageIfNotExist(output, texturePath, texture);
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
	
	private static final class Coloramp implements IColoramp
	{
		private final int[] colors = new int[256];
		
		private final int meanRGB;
		
		public Coloramp(ResourceProvider resources, int meanRGB, String name)
		{
			this.meanRGB = meanRGB;
			
			final String gradientMapPath = DIDatagenClient.gradientTextureTarget(name);
			try (NativeImage gradientMap = ItemMaterialPartTextureDatagenFunction.getTexture(resources, gradientMapPath))
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
