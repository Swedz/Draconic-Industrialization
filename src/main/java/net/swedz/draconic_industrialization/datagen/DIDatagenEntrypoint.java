package net.swedz.draconic_industrialization.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;

public final class DIDatagenEntrypoint implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator)
	{
		DIDatagenClient.configure(dataGenerator);
	}
}
