package net.swedz.draconic_industrialization.module.module.grid;

import net.swedz.draconic_industrialization.api.nbt.NBTSerializerWithParam;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModules;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public final class DracoGridEntry extends DracoGridSlot implements NBTSerializerWithParam<DracoGridEntry, DracoItem>
{
	private DracoModule module;
	
	private DracoGridSlotShape shape;
	
	public DracoGridEntry(int x, int y, DracoModule module)
	{
		super(x, y);
		this.module = module;
		this.shape = module.gridShape();
	}
	
	private DracoGridEntry(int x, int y, DracoGridSlotShape shape)
	{
		super(x, y);
		this.module = null;
		this.shape = module.gridShape();
	}
	
	DracoGridEntry()
	{
	}
	
	public static DracoGridEntry dummy(int x, int y, DracoGridSlotShape shape)
	{
		return new DracoGridEntry(x, y, shape);
	}
	
	public boolean isDummy()
	{
		return module == null;
	}
	
	public DracoModule module()
	{
		return module;
	}
	
	public DracoGridSlotShape shape()
	{
		return shape;
	}
	
	public List<DracoGridSlot> occupiedSlots()
	{
		List<DracoGridSlot> slots = Lists.newArrayList();
		for(DracoGridSlot shapeSlot : shape.slots())
		{
			int slotX = x + shapeSlot.x();
			int slotY = y + shapeSlot.y();
			slots.add(new DracoGridSlot(slotX, slotY));
		}
		return List.copyOf(slots);
	}
	
	public boolean contains(DracoGridSlot slot)
	{
		return this.occupiedSlots().contains(slot);
	}
	
	public boolean contains(int x, int y)
	{
		return this.contains(new DracoGridSlot(x, y));
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		x = tag.getInt("X");
		y = tag.getInt("Y");
		NBTTagWrapper moduleTag = tag.getOrEmpty("Module");
		module = DracoModules.create(moduleTag.getString("Key"), item, moduleTag);
		shape = module.gridShape();
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		tag.setInt("X", x);
		tag.setInt("Y", y);
		tag.set("Module", module.serialize());
	}
}
