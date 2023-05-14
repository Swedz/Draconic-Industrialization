package net.swedz.draconic_industrialization;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.swedz.draconic_industrialization.items.DIItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DraconicIndustrialization implements ModInitializer
{
	public static final String ID = "draconic_industrialization";
	
	public static ResourceLocation id(String name)
	{
		return new ResourceLocation(ID, name);
	}
	
	public static final Logger LOGGER = LoggerFactory.getLogger("Draconic Industrialization");
	
	public static final CreativeModeTab CREATIVE_TAB = FabricItemGroupBuilder.build(
			DraconicIndustrialization.id("draconic_industrialization"),
			DIItems.AWAKENED_DRACONIC_CIRCUIT::getDefaultInstance
	);
	
	@Override
	public void onInitialize()
	{
		DIItems.init();
	}
}
