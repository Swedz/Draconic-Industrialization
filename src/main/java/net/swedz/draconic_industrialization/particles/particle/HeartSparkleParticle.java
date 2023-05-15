package net.swedz.draconic_industrialization.particles.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public final class HeartSparkleParticle extends TextureSheetParticle
{
	public HeartSparkleParticle(ClientLevel clientLevel, double xo, double yo, double zo, double xd, double yd, double zd)
	{
		super(clientLevel, xo, yo, zo, xd, yd, zd);
	}
	
	@Override
	public ParticleRenderType getRenderType()
	{
		return null;
	}
}
