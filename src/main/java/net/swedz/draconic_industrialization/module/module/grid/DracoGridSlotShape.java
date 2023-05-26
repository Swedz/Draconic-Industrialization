package net.swedz.draconic_industrialization.module.module.grid;

import com.google.common.collect.Lists;

import java.util.List;

public final class DracoGridSlotShape
{
	private final int width, height;
	private final List<DracoGridSlot> slots;
	
	private DracoGridSlotShape(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.slots = Lists.newArrayList();
	}
	
	public static DracoGridSlotShape of(int width, int height)
	{
		DracoGridSlotShape shape = new DracoGridSlotShape(width, height);
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				shape.with(x, y);
			}
		}
		return shape;
	}
	
	public static DracoGridSlotShape single()
	{
		return of(1, 1);
	}
	
	private DracoGridSlotShape with(int x, int y)
	{
		slots.add(new DracoGridSlot(x, y));
		return this;
	}
	
	public int width()
	{
		return width;
	}
	
	public int height()
	{
		return height;
	}
	
	public List<DracoGridSlot> slots()
	{
		return List.copyOf(slots);
	}
}
