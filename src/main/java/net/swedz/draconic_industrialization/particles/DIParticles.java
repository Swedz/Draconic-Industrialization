package net.swedz.draconic_industrialization.particles;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.DustParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.inventory.InventoryMenu;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class DIParticles
{
	private static final Map<ParticleType, ParticleWrapper> WRAPPERS = Maps.newHashMap();
	
	public static final ParticleType HEART_SPARKLE = create("heart_sparkle", FabricParticleTypes.complex(DustParticleOptions.DESERIALIZER), DustParticle.Provider::new, new ParticleSprite("particle/heart_sparkle", "heart_sparkle"));
	
	public static Collection<ParticleWrapper> all()
	{
		return WRAPPERS.values();
	}
	
	public static <T extends ParticleOptions> ParticleType<T> create(String id, ParticleType<T> particle, ParticleFactoryRegistry.PendingParticleFactory<T> provider, ParticleSprite... sprites)
	{
		Registry.register(Registry.PARTICLE_TYPE, DraconicIndustrialization.id(id), particle);
		WRAPPERS.put(particle, new ParticleWrapper(id, provider, sprites)
				.datagenFunction(DatagenFunctions.Client.Particle.TEXTURE));
		return particle;
	}
	
	public static void init()
	{
		// Load the class
	}
	
	public static void initializeClient()
	{
		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((texture, registry) ->
				WRAPPERS.values().forEach((wrapper) ->
						List.of(wrapper.sprites()).forEach((sprite) ->
								registry.register(sprite.asset()))));
		
		WRAPPERS.forEach((type, wrapper) -> ParticleFactoryRegistry.getInstance().register(type, wrapper.provider()));
	}
}
