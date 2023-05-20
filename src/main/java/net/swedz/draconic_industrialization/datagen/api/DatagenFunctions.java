package net.swedz.draconic_industrialization.datagen.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.data.CachedOutput;
import net.swedz.draconic_industrialization.datagen.client.functions.block.BasicBlockModelDatagenFunction;
import net.swedz.draconic_industrialization.datagen.client.functions.item.BasicItemModelDatagenFunction;
import net.swedz.draconic_industrialization.datagen.client.functions.particle.ParticleTextureDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.block.BlockLootTableDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.block.BlockTagDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.item.ItemMaterialRecipeDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.item.ItemMaterialTagDatagenFunction;
import net.swedz.draconic_industrialization.datagen.server.functions.item.ItemTagDatagenFunction;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class DatagenFunctions
{
	public static final class Client
	{
		public static final class Block extends FunctionStorage<Block>
		{
			public static final BasicBlockModelDatagenFunction BASIC_MODEL = new BasicBlockModelDatagenFunction();
			
			public static final Block INSTANCE = new Block().collect();
		}
		
		public static final class Item extends FunctionStorage<Item>
		{
			public static final BasicItemModelDatagenFunction BASIC_MODEL = new BasicItemModelDatagenFunction();
			
			public static final Item INSTANCE = new Item().collect();
		}
		
		public static final class Particle extends FunctionStorage<Particle>
		{
			public static final ParticleTextureDatagenFunction TEXTURE = new ParticleTextureDatagenFunction();
			
			public static final Particle INSTANCE = new Particle().collect();
		}
	}
	
	public static final class Server
	{
		public static final class Block extends FunctionStorage<Block>
		{
			public static final BlockLootTableDatagenFunction LOOT_TABLE = new BlockLootTableDatagenFunction();
			public static final BlockTagDatagenFunction       TAG        = new BlockTagDatagenFunction();
			
			public static final Block INSTANCE = new Block().collect();
		}
		
		public static final class Item extends FunctionStorage<Item>
		{
			public static final ItemMaterialRecipeDatagenFunction MATERIAL_RECIPE = new ItemMaterialRecipeDatagenFunction();
			public static final ItemMaterialTagDatagenFunction    MATERIAL_TAG    = new ItemMaterialTagDatagenFunction();
			public static final ItemTagDatagenFunction            TAG             = new ItemTagDatagenFunction();
			
			public static final Item INSTANCE = new Item().collect();
		}
	}
	
	private abstract static class FunctionStorage<T>
	{
		private final Set<DatagenFunction> functions = Sets.newHashSet();
		
		T collect()
		{
			for(Field field : this.getClass().getFields())
			{
				int modifiers = field.getModifiers();
				if(Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) &&
						DatagenFunction.class.isAssignableFrom(field.getType()))
				{
					try
					{
						functions.add((DatagenFunction) field.get(null));
					}
					catch (IllegalAccessException ex)
					{
						throw new RuntimeException(ex);
					}
				}
			}
			return (T) this;
		}
		
		public List<DatagenFunction> allOrdered()
		{
			List<DatagenFunction> orderedFunctions = Lists.newArrayList(functions.iterator());
			orderedFunctions.sort(Comparator.comparingInt(DatagenFunction::priority));
			return orderedFunctions;
		}
		
		public void globalInit(DatagenProvider provider, CachedOutput output)
		{
			for(DatagenFunction function : this.allOrdered())
			{
				function.globalInit(provider, output);
			}
		}
		
		public void globalAfter(DatagenProvider provider, CachedOutput output) throws IOException
		{
			for(DatagenFunction function : this.allOrdered())
			{
				function.globalAfter(provider, output);
			}
		}
	}
}
