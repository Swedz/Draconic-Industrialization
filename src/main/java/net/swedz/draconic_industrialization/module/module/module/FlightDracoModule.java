package net.swedz.draconic_industrialization.module.module.module;

import io.github.ladysnake.pal.VanillaAbilities;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoModuleTick;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.Optional;

public final class FlightDracoModule extends DracoModule
{
	private static final long EU_COST = 1024;
	
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
	
	@Override
	public void tick(DracoModuleTick tick, ItemStack stack, Level level, Player player)
	{
		if(player.getAbilities().flying)
		{
			tick.euCost += EU_COST;
		}
	}
	
	public static void initializeListener()
	{
		// This isn't amazing... but it works for now
		ServerTickEvents.START_WORLD_TICK.register((level) ->
		{
			for(ServerPlayer player : level.players())
			{
				ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
				if(chestplate.getItem() instanceof DraconicArmorItem dracoChestplate &&
						dracoChestplate.dracoEnergy(chestplate).energy() >= EU_COST)
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
