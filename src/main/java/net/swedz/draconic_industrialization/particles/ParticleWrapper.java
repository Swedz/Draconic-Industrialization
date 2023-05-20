package net.swedz.draconic_industrialization.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionContainer;

public record ParticleWrapper<T extends ParticleOptions>(
		String id, ParticleSprite[] sprites,
		DatagenFunctionContainer<ParticleWrapper> datagenFunctions
)
{
	public ParticleWrapper(String id, ParticleSprite[] sprites)
	{
		this(id, sprites, new DatagenFunctionContainer<>());
	}
	
	public ParticleWrapper datagenFunction(DatagenFunction<ParticleWrapper> function)
	{
		datagenFunctions.add(function);
		return this;
	}
}
