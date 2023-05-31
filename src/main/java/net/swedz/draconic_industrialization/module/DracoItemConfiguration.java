package net.swedz.draconic_industrialization.module;

import dev.onyxstudios.cca.api.v3.item.CcaNbtType;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrializationCardinalComponents;
import net.swedz.draconic_industrialization.api.attributes.AccumulatedAttributeWrappers;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridEntry;
import net.swedz.draconic_industrialization.module.module.grid.DracoModuleGrid;
import net.swedz.draconic_industrialization.module.module.module.EnergyDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ShieldDracoModule;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class DracoItemConfiguration extends ItemComponent
{
	private boolean valid;
	
	private final DracoItem item;
	
	private final DracoTier tier;
	
	private DracoModuleGrid grid;
	
	private long energyCapacity, energyMaxIO;
	
	private List<ShieldDracoModule> shieldModules;
	private long shields, maxShields;
	
	public DracoItemConfiguration(ItemStack itemStack)
	{
		super(itemStack, DraconicIndustrializationCardinalComponents.DRACO);
		this.item = (DracoItem) itemStack.getItem();
		this.tier = item.tier();
	}
	
	public DracoItem item()
	{
		return item;
	}
	
	public DracoTier tier()
	{
		return tier;
	}
	
	public DracoModuleGrid grid()
	{
		if(!valid)
		{
			CompoundTag gridTag = this.hasTag("Grid", CcaNbtType.COMPOUND) ? this.getTag("Grid", CcaNbtType.COMPOUND) : new CompoundTag();
			grid = new DracoModuleGrid(item).deserialize(gridTag);
			
			List<EnergyDracoModule> energyModules = grid.getModules(EnergyDracoModule.class);
			energyCapacity = energyModules.stream().mapToLong(EnergyDracoModule::capacity).sum();
			energyMaxIO = energyModules.stream().mapToLong(EnergyDracoModule::io).sum();
			
			shieldModules = grid.getModules(ShieldDracoModule.class);
			shields = shieldModules.stream().mapToLong(ShieldDracoModule::shields).sum();
			maxShields = shieldModules.stream().mapToLong(ShieldDracoModule::maxShields).sum();
			
			valid = true;
		}
		return grid;
	}
	
	public Stream<DracoModule> modules()
	{
		return this.grid().entries().stream().map(DracoGridEntry::module);
	}
	
	private <M extends DracoModule> Stream<M> moduleStream(Class<M> module)
	{
		return this.modules()
				.filter((m) -> module.isAssignableFrom(m.getClass()))
				.map((m) -> (M) m);
	}
	
	private <M extends DracoModule> Stream<M> moduleStream(DracoModuleReference<M> module)
	{
		return this.moduleStream(module.reference());
	}
	
	public <M extends DracoModule> List<M> getModules(DracoModuleReference<M> module)
	{
		return this.moduleStream(module).toList();
	}
	
	public <M extends DracoModule> long countModules(DracoModuleReference<M> module)
	{
		return this.moduleStream(module).count();
	}
	
	public <M extends DracoModule> Optional<M> getModule(DracoModuleReference<M> module)
	{
		return this.moduleStream(module).findAny();
	}
	
	public <M extends DracoModule> M getModuleOrCreate(DracoModuleReference<M> module)
	{
		return this.getModule(module).orElseGet(() -> DracoModules.create(module, item, new NBTTagWrapper()));
	}
	
	public long energyCapacity()
	{
		this.grid();
		return energyCapacity;
	}
	
	public long energyMaxIO()
	{
		this.grid();
		return energyMaxIO;
	}
	
	public List<ShieldDracoModule> shieldModules()
	{
		this.grid();
		return List.copyOf(shieldModules);
	}
	
	public long shields()
	{
		this.grid();
		return shields;
	}
	
	public long maxShields()
	{
		this.grid();
		return maxShields;
	}
	
	public void save()
	{
		CompoundTag tag = new CompoundTag();
		CompoundTag dracoTag = this.getOrCreateRootTag();
		dracoTag.put("Grid", this.grid().serialize());
		tag.put(this.getRootTagKey(), dracoTag);
		stack.getOrCreateTag().merge(tag);
		stack.save(new CompoundTag());
		
		stack.getOrCreateTag().remove("AttributeModifiers");
		AccumulatedAttributeWrappers attributes = new AccumulatedAttributeWrappers();
		this.modules().forEach((m) -> m.applyAttributes(attributes, item));
		attributes.apply(stack);
	}
	
	@Deprecated
	@Override
	public void onTagInvalidated()
	{
		super.onTagInvalidated();
		valid = false;
	}
}
