package net.swedz.draconic_industrialization;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.attributes.DIAttributes;
import net.swedz.draconic_industrialization.blocks.DIBlocks;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.entity.DIEntities;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.module.FlightDracoModule;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.PacketType;
import net.swedz.draconic_industrialization.particles.DIParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

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
			() -> new ItemStack(Registry.ITEM.get(id("awakened_draconium_circuit")))
	);
	
	public static final AbilitySource ABILITY_SOURCE = Pal.getAbilitySource(id(ID));
	
	public static void loadClasses()
	{
		DIItems.init();
		DIBlocks.init();
		DIEntities.init();
		DIParticles.init();
		DIAttributes.init();
		DracoMenu.init();
	}
	
	@Override
	public void onInitialize()
	{
		loadClasses();
		
		DIPacketChannels.registerAllListeners(PacketType.SERVERBOUND);
		
		FlightDracoModule.initializeListener();
		
		EnergyStorage.ITEM.registerForItems(
				(stack, context) -> ((DracoItem) stack.getItem()).dracoEnergy(stack).createStorage(context),
				DIItems.WYVERN_ARMOR, DIItems.DRACONIC_ARMOR, DIItems.CHAOTIC_ARMOR
		);
	}
}
