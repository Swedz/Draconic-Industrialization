package net.swedz.draconic_industrialization.particles;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;

public record ParticleWrapper<T extends ParticleOptions>(
		String id, ParticleFactoryRegistry.PendingParticleFactory<T> provider, ParticleSprite[] sprites,
		DatagenFunctionContainer<ParticleWrapper> datagenFunctions
)
{
	public ParticleWrapper(String id, ParticleFactoryRegistry.PendingParticleFactory<T> provider, ParticleSprite[] sprites)
	{
		this(id, provider, sprites, new DatagenFunctionContainer<>());
	}
	
	public ParticleWrapper datagenFunction(DatagenFunction<ParticleWrapper> function)
	{
		datagenFunctions.add(function);
		return this;
	}
}
