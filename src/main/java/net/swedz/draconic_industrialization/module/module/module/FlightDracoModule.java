package net.swedz.draconic_industrialization.module.module.module;

import io.github.ladysnake.pal.VanillaAbilities;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.Optional;
import java.util.UUID;

public final class FlightDracoModule extends DracoModule
{
	private static final UUID ATTRIBUTE_ID = UUID.fromString("5863eaf8-fa94-11ed-be56-0242ac120002");
	
	public FlightDracoModule(DracoModuleReference reference)
	{
		super(reference);
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return super.applies(item) &&
				item instanceof DraconicArmorItem;
	}
	
	@Override
	public int max()
	{
		return 1;
	}
	
	@Override
	public boolean hasStuffToConfigure()
	{
		return false;
	}
	
	public static void initializeListener()
	{
		// This isn't amazing... but it works for now
		ServerTickEvents.START_WORLD_TICK.register((level) ->
		{
			for(ServerPlayer player : level.players())
			{
				ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
				if(chestplate.getItem() instanceof DraconicArmorItem dracoChestplate)
				{
					Optional<FlightDracoModule> optionalFlightModule = dracoChestplate.dracoConfiguration(chestplate).getModule(DracoModules.FLIGHT);
					if(optionalFlightModule.isPresent())
					{
						DraconicIndustrialization.ABILITY_SOURCE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
						continue;
					}
				}
				DraconicIndustrialization.ABILITY_SOURCE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
			}
		});
	}
}