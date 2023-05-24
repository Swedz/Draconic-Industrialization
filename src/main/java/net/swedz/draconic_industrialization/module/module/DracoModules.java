package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;
import net.swedz.draconic_industrialization.module.module.module.ArmorAppearanceDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ColorizerDracoModule;
import net.swedz.draconic_industrialization.module.module.module.SpeedDracoModule;

import java.util.Map;
import java.util.function.Supplier;

public final class DracoModules
{
	private static final Map<String, DracoModuleReference> REFERENCES = Maps.newHashMap();
	
	public static final DracoModuleReference<ColorizerDracoModule>       COLORIZER       = register(
			"Colorizer", DracoGridSlotShape.single(),
			ColorizerDracoModule.class, ColorizerDracoModule::new,
			() -> DIItems.MODULE_COLORIZER
	);
	public static final DracoModuleReference<ArmorAppearanceDracoModule> ARMOR_APPERANCE = register(
			"ArmorAppearance", DracoGridSlotShape.single(),
			ArmorAppearanceDracoModule.class, ArmorAppearanceDracoModule::new,
			() -> DIItems.MODULE_ARMOR_APPEARNCE
	);
	
	public static final DracoModuleReference<SpeedDracoModule> SPEED_WYVERN   = register(
			"SpeedWyvern", DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, DracoTier.WYVERN, 10),
			() -> DIItems.MODULE_SPEED_WYVERN
	);
	public static final DracoModuleReference<SpeedDracoModule> SPEED_DRACONIC = register(
			"SpeedDraconic", DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, DracoTier.DRACONIC, 25),
			() -> DIItems.MODULE_SPEED_DRACONIC
	);
	public static final DracoModuleReference<SpeedDracoModule> SPEED_CHAOTIC  = register(
			"SpeedChaotic", DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, DracoTier.CHAOTIC, 50),
			() -> DIItems.MODULE_SPEED_CHAOTIC
	);
	
	private static <M extends DracoModule> DracoModuleReference<M> register(String key, DracoGridSlotShape gridShape, Class<M> moduleClass, DracoModuleCreator<M> creator, Supplier<Item> itemSupplier)
	{
		DracoModuleReference reference = new DracoModuleReference<>(key, gridShape, moduleClass, creator, itemSupplier);
		REFERENCES.put(key, reference);
		return reference;
	}
	
	public static DracoModule create(String key, DracoItem parentItem, NBTTagWrapper tag)
	{
		return REFERENCES.get(key).create().deserialize(tag, parentItem);
	}
	
	public static <M extends DracoModule> M create(DracoModuleReference<M> module, DracoItem parentItem, NBTTagWrapper tag)
	{
		return (M) module.create().deserialize(tag, parentItem);
	}
}
