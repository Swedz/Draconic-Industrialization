package net.swedz.draconic_industrialization.api;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public final class EnergyAPIWorkaround
{
	public static Optional<ContainerItemContext> contextOptional(Player player, ItemStack itemStack)
	{
		PlayerInventoryStorage playerInventoryStorage = PlayerInventoryStorage.of(player);
		SingleSlotStorage storage = null;
		for(int i = 0; i < player.getInventory().getContainerSize(); i++)
		{
			if(player.getInventory().getItem(i) == itemStack)
			{
				return Optional.of(ContainerItemContext.ofPlayerSlot(player, playerInventoryStorage.getSlot(i)));
			}
		}
		return Optional.empty();
	}
	
	public static ContainerItemContext context(Player player, ItemStack itemStack)
	{
		return contextOptional(player, itemStack)
				.orElseThrow(() -> new IllegalArgumentException("Failed to locate stack in the player's inventory"));
	}
}
