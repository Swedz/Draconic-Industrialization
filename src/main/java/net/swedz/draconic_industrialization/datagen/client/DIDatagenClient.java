package net.swedz.draconic_industrialization.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class DIDatagenClient
{
	public static void configure(FabricDataGenerator dataGenerator)
	{
		dataGenerator.addProvider(new ItemModelDataProvider(dataGenerator));
		dataGenerator.addProvider(new LangDataProvider(dataGenerator));
	}
}
