package net.swedz.draconic_industrialization;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;

public final class DraconicIndustrializationCardinalComponents implements ItemComponentInitializer
{
	public static final ComponentKey<DracoItemConfiguration> DRACO = ComponentRegistry.getOrCreate(DraconicIndustrialization.id("item.draco"), DracoItemConfiguration.class);
	
	@Override
	public void registerItemComponentFactories(ItemComponentFactoryRegistry registry)
	{
		registry.register((i) -> i instanceof DracoItem, DRACO, DracoItemConfiguration::new);
	}
}
