package net.swedz.draconic_industrialization;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DraconicIndustrialization implements ModInitializer
{
	public static final String ID = "draconic_industrialization";
	
	public static ResourceLocation id(String name)
	{
		return new ResourceLocation(ID, name);
	}
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	
	@Override
	public void onInitialize()
	{
	
	}
}
