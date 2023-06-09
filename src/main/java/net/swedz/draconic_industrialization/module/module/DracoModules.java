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
import net.swedz.draconic_industrialization.module.module.module.EnergyDracoModule;
import net.swedz.draconic_industrialization.module.module.module.FlightDracoModule;
import net.swedz.draconic_industrialization.module.module.module.JumpDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ShieldDracoModule;
import net.swedz.draconic_industrialization.module.module.module.SpeedDracoModule;

import java.util.Map;
import java.util.function.Supplier;

public final class DracoModules
{
	private static final Map<String, DracoModuleReference> REFERENCES = Maps.newHashMap();
	
	public static final DracoModuleReference<ColorizerDracoModule>       COLORIZER       = register(
			"colorizer", "Colorizer", DracoTier.WYVERN, DracoGridSlotShape.single(),
			ColorizerDracoModule.class, ColorizerDracoModule::new,
			() -> DIItems.MODULE_COLORIZER
	);
	public static final DracoModuleReference<ArmorAppearanceDracoModule> ARMOR_APPERANCE = register(
			"armor_appearance", "ArmorAppearance", DracoTier.WYVERN, DracoGridSlotShape.single(),
			ArmorAppearanceDracoModule.class, ArmorAppearanceDracoModule::new,
			() -> DIItems.MODULE_ARMOR_APPEARNCE
	);
	
	public static final DracoModuleReference<SpeedDracoModule> SPEED_WYVERN   = register(
			"speed", "SpeedWyvern", DracoTier.WYVERN, DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, 10),
			() -> DIItems.MODULE_SPEED_WYVERN
	);
	public static final DracoModuleReference<SpeedDracoModule> SPEED_DRACONIC = register(
			"speed", "SpeedDraconic", DracoTier.DRACONIC, DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, 25),
			() -> DIItems.MODULE_SPEED_DRACONIC
	);
	public static final DracoModuleReference<SpeedDracoModule> SPEED_CHAOTIC  = register(
			"speed", "SpeedChaotic", DracoTier.CHAOTIC, DracoGridSlotShape.of(2, 2),
			SpeedDracoModule.class, (r) -> new SpeedDracoModule(r, 50),
			() -> DIItems.MODULE_SPEED_CHAOTIC
	);
	
	public static final DracoModuleReference<JumpDracoModule> JUMP_WYVERN   = register(
			"jump", "JumpWyvern", DracoTier.WYVERN, DracoGridSlotShape.of(2, 1),
			JumpDracoModule.class, (r) -> new JumpDracoModule(r, 25),
			() -> DIItems.MODULE_JUMP_WYVERN
	);
	public static final DracoModuleReference<JumpDracoModule> JUMP_DRACONIC = register(
			"jump", "JumpDraconic", DracoTier.DRACONIC, DracoGridSlotShape.of(2, 1),
			JumpDracoModule.class, (r) -> new JumpDracoModule(r, 75),
			() -> DIItems.MODULE_JUMP_DRACONIC
	);
	public static final DracoModuleReference<JumpDracoModule> JUMP_CHAOTIC  = register(
			"jump", "JumpChaotic", DracoTier.CHAOTIC, DracoGridSlotShape.of(2, 1),
			JumpDracoModule.class, (r) -> new JumpDracoModule(r, 150),
			() -> DIItems.MODULE_JUMP_CHAOTIC
	);
	
	public static final DracoModuleReference<FlightDracoModule> FLIGHT = register(
			"flight", "Flight", DracoTier.WYVERN, DracoGridSlotShape.of(2, 2),
			FlightDracoModule.class, FlightDracoModule::new,
			() -> DIItems.MODULE_FLIGHT
	);
	
	public static final DracoModuleReference<EnergyDracoModule> ENERGY_WYVERN   = register(
			"energy", "EnergyWyvern", DracoTier.WYVERN, DracoGridSlotShape.single(),
			EnergyDracoModule.class, (r) -> new EnergyDracoModule(r, 4000000, 64000),
			() -> DIItems.MODULE_ENERGY_WYVERN
	);
	public static final DracoModuleReference<EnergyDracoModule> ENERGY_DRACONIC = register(
			"energy", "EnergyDraconic", DracoTier.DRACONIC, DracoGridSlotShape.single(),
			EnergyDracoModule.class, (r) -> new EnergyDracoModule(r, 16000000, 256000),
			() -> DIItems.MODULE_ENERGY_DRACONIC
	);
	public static final DracoModuleReference<EnergyDracoModule> ENERGY_CHAOTIC  = register(
			"energy", "EnergyChaotic", DracoTier.CHAOTIC, DracoGridSlotShape.single(),
			EnergyDracoModule.class, (r) -> new EnergyDracoModule(r, 64000000, 1000000),
			() -> DIItems.MODULE_ENERGY_CHAOTIC
	);
	
	public static final DracoModuleReference<ShieldDracoModule> SHIELD_WYVERN = register(
			"shield", "ShieldWyvern", DracoTier.WYVERN, DracoGridSlotShape.single(),
			ShieldDracoModule.class, (r) -> new ShieldDracoModule(r, 20, 20, 1, 25000, 10 * 20),
			() -> DIItems.MODULE_SHIELD_WYVERN
	);
	public static final DracoModuleReference<ShieldDracoModule> SHIELD_DRACONIC = register(
			"shield", "ShieldDraconic", DracoTier.DRACONIC, DracoGridSlotShape.of(2, 1),
			ShieldDracoModule.class, (r) -> new ShieldDracoModule(r, 50, 10, 1, 25000, 5 * 20),
			() -> DIItems.MODULE_SHIELD_DRACONIC
	);
	public static final DracoModuleReference<ShieldDracoModule> SHIELD_CHAOTIC = register(
			"shield", "ShieldChaotic", DracoTier.CHAOTIC, DracoGridSlotShape.of(2, 2),
			ShieldDracoModule.class, (r) -> new ShieldDracoModule(r, 100, 2, 1, 25000, 20),
			() -> DIItems.MODULE_SHIELD_CHAOTIC
	);
	
	private static <M extends DracoModule> DracoModuleReference<M> register(String id, String key, DracoTier tier, DracoGridSlotShape gridShape, Class<M> moduleClass, DracoModuleCreator<M> creator, Supplier<Item> itemSupplier)
	{
		DracoModuleReference reference = new DracoModuleReference<>(id, key, tier, gridShape, moduleClass, creator, itemSupplier);
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
