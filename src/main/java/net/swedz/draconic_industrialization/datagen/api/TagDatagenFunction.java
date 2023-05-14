package net.swedz.draconic_industrialization.datagen.api;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagFile;
import net.minecraft.tags.TagKey;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.datagen.client.DIDatagenClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class TagDatagenFunction<T, R> implements DatagenFunction<T>
{
	protected abstract Registry<R> registry();
	
	private final Map<ResourceLocation, TagBuilder> builders = Maps.newLinkedHashMap();
	
	protected TagsProvider.TagAppender tag(TagKey<R> tag)
	{
		TagBuilder tagBuilder = this.builders.computeIfAbsent(tag.location(), (resourceLocation) -> TagBuilder.create());
		return new TagsProvider.TagAppender(tagBuilder, this.registry());
	}
	
	protected TagsProvider.TagAppender tag(String tag)
	{
		return this.tag(TagKey.create(this.registry().key(), new ResourceLocation(tag)));
	}
	
	@Override
	public void init(DatagenProvider provider, CachedOutput output)
	{
		builders.clear();
	}
	
	@Override
	public void after(DatagenProvider provider, CachedOutput output) throws IOException
	{
		for(Map.Entry<ResourceLocation, TagBuilder> entry : builders.entrySet())
		{
			final ResourceLocation resourceLocation = entry.getKey();
			final TagBuilder tagBuilder = entry.getValue();
			
			List<TagEntry> tags = tagBuilder.build();
			List<TagEntry> missingReferences = tags.stream().filter((e) ->
							!e.verifyIfPresent(this.registry()::containsKey, builders::containsKey))
					.toList();
			if(!missingReferences.isEmpty())
			{
				throw new IllegalArgumentException("Couldn't define tag %s as it is missing following references: %s".formatted(
						resourceLocation,
						missingReferences.stream().map(Objects::toString).collect(Collectors.joining(","))
				));
			}
			else
			{
				final DataResult<JsonElement> result = TagFile.CODEC.encodeStart(JsonOps.INSTANCE, new TagFile(tags, false));
				final JsonElement jsonElement = result.getOrThrow(false, DraconicIndustrialization.LOGGER::error);
				final String tagPath = DIDatagenClient.tagPath(resourceLocation.getNamespace(), this.registry().key().location().getPath(), resourceLocation.getPath());
				provider.writeJsonForce(output, tagPath, jsonElement);
			}
		}
	}
}
