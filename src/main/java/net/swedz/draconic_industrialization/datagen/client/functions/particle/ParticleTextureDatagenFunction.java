package net.swedz.draconic_industrialization.datagen.client.functions.particle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.datagen.api.ClientDatagenFunction;
import net.swedz.draconic_industrialization.datagen.api.DatagenFunctionCategory;
import net.swedz.draconic_industrialization.datagen.api.DatagenProvider;
import net.swedz.draconic_industrialization.particles.ParticleSprite;
import net.swedz.draconic_industrialization.particles.ParticleWrapper;

import java.io.IOException;

public final class ParticleTextureDatagenFunction extends ClientDatagenFunction<ParticleWrapper>
{
	@Override
	public DatagenFunctionCategory category()
	{
		return DatagenFunctionCategory.PARTICLE_CLIENT;
	}
	
	private JsonObject buildParticleJson(ParticleWrapper particle)
	{
		final JsonObject json = new JsonObject();
		
		JsonArray textures = new JsonArray();
		for(ParticleSprite sprite : particle.sprites())
		{
			textures.add(sprite.location().toString());
		}
		json.add("textures", textures);
		
		return json;
	}
	
	@Override
	public void run(DatagenProvider provider, CachedOutput output, ParticleWrapper particle) throws IOException
	{
		final String path = "assets/%s/particles/%s.json".formatted(provider.modId(), particle.id());
		provider.writeJsonIfNotExist(output, path, this.buildParticleJson(particle));
	}
}
