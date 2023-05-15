package net.swedz.draconic_industrialization.particles;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public record ParticleSprite(ResourceLocation asset, ResourceLocation location)
{
	public ParticleSprite(String asset, String name)
	{
		this(DraconicIndustrialization.id(asset), DraconicIndustrialization.id(name));
	}
}
