package net.swedz.draconic_industrialization.particles;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.inventory.InventoryMenu;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctions;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public final class DIParticles
{
	private static final Map<SimpleParticleType, ParticleWrapper> WRAPPERS = Maps.newHashMap();
	
	public static final SimpleParticleType HEART_SPARKLE = create("heart_sparkle", FabricParticleTypes.simple(), new ParticleSprite("particle/heart_sparkle", "heart_sparkle"));
	
	public static Collection<ParticleWrapper> all()
	{
		return WRAPPERS.values();
	}
	
	public static <T extends ParticleOptions> SimpleParticleType create(String id, SimpleParticleType particle, ParticleSprite... sprites)
	{
		Registry.register(Registry.PARTICLE_TYPE, DraconicIndustrialization.id(id), particle);
		WRAPPERS.put(particle, new ParticleWrapper(id, sprites)
				.datagenFunction(DatagenFunctions.Client.Particle.TEXTURE));
		return particle;
	}
	
	public static void init()
	{
		// Load the class
	}
	
	public static void initClient()
	{
		ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register((texture, registry) ->
				WRAPPERS.values().forEach((wrapper) ->
						List.of(wrapper.sprites()).forEach((sprite) ->
								registry.register(sprite.asset()))));
	}
}
