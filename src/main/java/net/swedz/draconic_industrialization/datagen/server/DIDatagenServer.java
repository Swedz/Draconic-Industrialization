package net.swedz.draconic_industrialization.datagen.server;

import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class DIDatagenServer
{
	private static final Set<String> TAG_CACHE = Sets.newHashSet();
	
	public static void trackTag(String tag)
	{
		TAG_CACHE.add(tag);
	}
	
	public static boolean hasTag(TagKey<Item> tag)
	{
		return TAG_CACHE.contains(tag.location().toString());
	}
	
	public static void configure(FabricDataGenerator dataGenerator)
	{
		TAG_CACHE.clear();
		
		dataGenerator.addProvider(ServerDatagenProvider::new);
	}
}
