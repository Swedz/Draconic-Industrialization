package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;
import net.swedz.draconic_industrialization.module.module.module.ArmorAppearanceDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ColorizerDracoModule;

import java.util.Map;
import java.util.function.Supplier;

public final class DracoModules
{
	private static final Map<String, DracoModuleReference> REFERENCES = Maps.newHashMap();
	
	public static final DracoModuleReference<ColorizerDracoModule>       COLORIZER       = register(
			"Colorizer", DracoGridSlotShape.of(1, 1),
			ColorizerDracoModule.class, ColorizerDracoModule::new,
			() -> DIItems.MODULE_COLORIZER
	);
	public static final DracoModuleReference<ArmorAppearanceDracoModule> ARMOR_APPERANCE = register(
			"ArmorAppearance", DracoGridSlotShape.of(1, 1),
			ArmorAppearanceDracoModule.class, ArmorAppearanceDracoModule::new,
			() -> DIItems.MODULE_ARMOR_APPEARNCE
	);
	
	private static <M extends DracoModule> DracoModuleReference<M> register(String key, DracoGridSlotShape gridShape, Class<M> moduleClass, DracoModuleCreator<M> creator, Supplier<Item> itemSupplier)
	{
		DracoModuleReference reference = new DracoModuleReference<>(key, gridShape, moduleClass, creator, itemSupplier);
		REFERENCES.put(key, reference);
		return reference;
	}
	
	public static DracoModule create(String key, DracoItem parentItem, NBTTagWrapper tag)
	{
		return REFERENCES.get(key).creator()
				.create(REFERENCES.get(key), parentItem)
				.deserialize(tag);
	}
	
	public static <M extends DracoModule> M create(DracoModuleReference<M> module, DracoItem parentItem, NBTTagWrapper tag)
	{
		return (M) module.creator()
				.create(module, parentItem)
				.deserialize(tag);
	}
}
