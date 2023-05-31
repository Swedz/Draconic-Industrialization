package net.swedz.draconic_industrialization.module.module.grid;

import com.google.common.collect.Lists;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.nbt.NBTSerializer;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;

import java.util.List;
import java.util.Optional;

public final class DracoModuleGrid implements NBTSerializer<DracoModuleGrid>
{
	private final DracoItem parentItem;
	
	private final List<DracoGridEntry> entries = Lists.newArrayList();
	
	private final int width, height;
	
	public DracoModuleGrid(DracoItem parentItem, int width, int height)
	{
		this.parentItem = parentItem;
		this.width = width;
		this.height = height;
	}
	
	public DracoModuleGrid(DracoItem parentItem)
	{
		this.parentItem = parentItem;
		DracoGridSize size = parentItem.gridSize();
		this.width = size.width();
		this.height = size.height();
	}
	
	public DracoItem parentItem()
	{
		return parentItem;
	}
	
	public int width()
	{
		return width;
	}
	
	public int height()
	{
		return height;
	}
	
	public List<DracoGridEntry> entries()
	{
		return List.copyOf(entries);
	}
	
	public boolean isWithinBounds(DracoGridSlot slot)
	{
		return slot.x() >= 0 && slot.x() < width &&
				slot.y() >= 0 && slot.y() < height;
	}
	
	public boolean isWithinBounds(int x, int y)
	{
		return this.isWithinBounds(new DracoGridSlot(x, y));
	}
	
	public boolean isFree(DracoGridSlot slot)
	{
		return this.isWithinBounds(slot) && entries.stream().noneMatch((e) -> e.contains(slot));
	}
	
	public boolean isFree(int x, int y)
	{
		return this.isFree(new DracoGridSlot(x, y));
	}
	
	public boolean canFit(DracoGridEntry entry)
	{
		return entry.occupiedSlots().stream().allMatch(this::isFree);
	}
	
	public boolean canFit(int x, int y, DracoGridSlotShape shape)
	{
		return this.canFit(DracoGridEntry.dummy(x, y, shape));
	}
	
	public boolean valid(DracoGridEntry entry)
	{
		return this.isWithinBounds(entry) && this.canFit(entry);
	}
	
	public boolean valid(int x, int y, DracoGridSlotShape shape)
	{
		return this.valid(DracoGridEntry.dummy(x, y, shape));
	}
	
	public boolean add(DracoGridEntry entry)
	{
		if(entry.isDummy())
		{
			throw new IllegalArgumentException("Cannot insert dummy entry into grid");
		}
		if(this.valid(entry))
		{
			entries.add(entry);
			return true;
		}
		DraconicIndustrialization.LOGGER.info("Failed to insert entry into grid");
		return false;
	}
	
	public boolean add(int x, int y, DracoModule module)
	{
		return this.add(new DracoGridEntry(x, y, module));
	}
	
	public void remove(DracoGridEntry entry)
	{
		entries.remove(entry);
	}
	
	public Optional<DracoGridEntry> getExactly(int x, int y)
	{
		return entries.stream().filter((e) -> e.x() == x && e.y() == y).findFirst();
	}
	
	public Optional<DracoGridEntry> get(int x, int y)
	{
		return entries.stream().filter((e) -> e.contains(x, y)).findFirst();
	}
	
	public Optional<DracoGridEntry> pull(int x, int y)
	{
		Optional<DracoGridEntry> entry = this.get(x, y);
		entry.ifPresent(this::remove);
		return entry;
	}
	
	public <M extends DracoModule> List<M> getModules(Class<M> module)
	{
		return this.entries().stream()
				.map(DracoGridEntry::module)
				.filter((m) -> module.isAssignableFrom(m.getClass()))
				.map((m) -> (M) m)
				.toList();
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		tag.getList("Entries").stream()
				.map((nbt) -> new DracoGridEntry().deserialize(nbt, parentItem))
				.forEachOrdered(this::add);
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.setList("Entries", entries.stream()
				.map((entry) -> entry.serialize(parentItem))
				.map(NBTTagWrapper::new)
				.toList());
	}
}
