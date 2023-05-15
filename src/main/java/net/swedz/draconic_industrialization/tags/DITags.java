package net.swedz.draconic_industrialization.tags;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public final class DITags
{
	public static final class Items
	{
		public static final TagKey<Item> DRACONIC_ARMOR = di("draconic_armor");
		
		private static TagKey<Item> di(String id)
		{
			return TagKey.create(Registry.ITEM_REGISTRY, DraconicIndustrialization.id(id));
		}
	}
}
