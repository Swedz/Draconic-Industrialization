package net.swedz.draconic_industrialization.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.entity.dragonheart.DragonHeartEntity;
import net.swedz.draconic_industrialization.entity.dragonheart.DragonHeartEntityRenderer;

public final class DIEntities
{
	public static final EntityType<DragonHeartEntity> DRAGON_HEART = Registry.register(
			Registry.ENTITY_TYPE,
			DraconicIndustrialization.id("dragon_heart"),
			FabricEntityTypeBuilder.create()
					.entityFactory(DragonHeartEntity::new)
					.dimensions(EntityDimensions.fixed(1.5f, 1.5f))
					.build()
	);
	
	public static void init()
	{
		// Load the class
	}
	
	public static void initializeClient()
	{
		EntityRendererRegistry.register(DRAGON_HEART, DragonHeartEntityRenderer::new);
	}
}
