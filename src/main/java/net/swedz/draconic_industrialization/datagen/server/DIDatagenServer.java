package net.swedz.draconic_industrialization.datagen.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public final class DIDatagenServer
{
	public static void configure(FabricDataGenerator dataGenerator)
	{
		dataGenerator.addProvider(ServerDatagenProvider::new);
	}
}
