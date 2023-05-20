package net.swedz.draconic_industrialization.module;

import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.nbt.NBTSerializer;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridEntry;
import net.swedz.draconic_industrialization.module.module.grid.DracoModuleGrid;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.Optional;

public class DracoItemConfiguration implements NBTSerializer<DracoItemConfiguration>
{
	static final String PARENT_KEY = "DracoItemConfiguration";
	
	private final DracoItem item;
	private final ItemStack itemStack;
	
	private final DracoTier tier;
	
	private DracoModuleGrid grid;
	
	DracoItemConfiguration(DracoItem item, ItemStack itemStack)
	{
		this.item = item;
		this.itemStack = itemStack;
		this.tier = item.tier();
	}
	
	public DracoItem item()
	{
		return item;
	}
	
	public ItemStack itemStack()
	{
		return itemStack;
	}
	
	public DracoTier tier()
	{
		return tier;
	}
	
	public <M extends DracoModule> Optional<M> getModule(DracoModuleReference<M> module)
	{
		return grid.entries().stream()
				.map(DracoGridEntry::module)
				.filter((m) -> module.reference().isAssignableFrom(m.getClass()))
				.map((m) -> (M) m)
				.findAny();
	}
	
	public <M extends DracoModule> M getModuleOrCreate(DracoModuleReference<M> module)
	{
		return this.getModule(module).orElseGet(() -> DracoModules.create(module, item, new NBTTagWrapper()));
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		grid = new DracoModuleGrid(item).deserialize(tag.getOrEmpty("Grid"));
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.set("Grid", grid.serialize());
	}
}
