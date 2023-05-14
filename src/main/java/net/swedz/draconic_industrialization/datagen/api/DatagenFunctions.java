package net.swedz.draconic_industrialization.datagen.api;

import net.swedz.draconic_industrialization.datagen.client.functions.block.BasicBlockModelDatagenFunction;
import net.swedz.draconic_industrialization.datagen.client.functions.item.BasicItemModelDatagenFunction;
import net.swedz.draconic_industrialization.datagen.client.functions.item.ItemMaterialPartTextureDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.item.ItemMaterialTagDatagenFunction;

public final class DatagenFunctions
{
	public static final class Client
	{
		public interface Block
		{
			BasicBlockModelDatagenFunction BASIC_MODEL = new BasicBlockModelDatagenFunction();
		}
		
		public interface Item
		{
			BasicItemModelDatagenFunction          BASIC_MODEL           = new BasicItemModelDatagenFunction();
			ItemMaterialPartTextureDatagenFunction MATERIAL_PART_TEXTURE = new ItemMaterialPartTextureDatagenFunction();
		}
	}
	
	public static final class Server
	{
		public interface Block
		{
		
		}
		
		public interface Item
		{
			ItemMaterialTagDatagenFunction MATERIAL_TAG = new ItemMaterialTagDatagenFunction();
		}
	}
}
