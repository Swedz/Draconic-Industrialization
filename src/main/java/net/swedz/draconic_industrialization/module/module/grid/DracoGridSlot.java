package net.swedz.draconic_industrialization.module.module.grid;

import java.util.Objects;

public class DracoGridSlot
{
	protected int x, y;
	
	public DracoGridSlot(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	DracoGridSlot()
	{
	}
	
	public int x()
	{
		return x;
	}
	
	public int y()
	{
		return y;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof DracoGridSlot otherSlot &&
				x == otherSlot.x &&
				y == otherSlot.y;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(x, y);
	}
}
