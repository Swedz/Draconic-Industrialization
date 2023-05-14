package net.swedz.draconic_industrialization.datagen.api;

import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;

import java.io.IOException;

public abstract class LootTableDatagenFunction<T, R> implements DatagenFunction<T>
{
	protected abstract Registry<R> registry();
	
	protected void add(DatagenProvider provider, CachedOutput output, String id, LootTable table) throws IOException
	{
		final ResourceLocation resourceLocation = new ResourceLocation(id);
		final String tablePath = DIDatagenClient.lootTablePath(resourceLocation.getNamespace(), this.registry().key().location().getPath(), resourceLocation.getPath());
		provider.writeJsonIfNotExist(output, tablePath, LootTables.serialize(table));
	}
}
