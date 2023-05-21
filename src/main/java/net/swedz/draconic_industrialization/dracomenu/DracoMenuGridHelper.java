package net.swedz.draconic_industrialization.dracomenu;

import net.swedz.draconic_industrialization.module.module.grid.DracoGridSize;

public final class DracoMenuGridHelper
{
	private static final int OFFSET_X = 87, OFFSET_Y = 4;
	private static final int MAX_WIDTH = 118, MAX_HEIGHT = 110;
	
	private final DracoGridSize size;
	
	private final int slotSize;
	private final int minX, minY, width, height;
	private final int renderStartX, renderStartY;
	
	public DracoMenuGridHelper(DracoGridSize size, int leftPos, int topPos, int slotSize)
	{
		this.size = size;
		this.minX = leftPos + OFFSET_X;
		this.minY = topPos + OFFSET_Y;
		this.slotSize = slotSize;
		this.width = size.width() * slotSize;
		this.height = size.height() * slotSize;
		this.renderStartX = minX + MAX_WIDTH / 2 - width / 2;
		this.renderStartY = minY + MAX_HEIGHT / 2 - height / 2;
	}
	
	public DracoGridSize size()
	{
		return size;
	}
	
	public int minX()
	{
		return minX;
	}
	
	public int minY()
	{
		return minY;
	}
	
	public int width()
	{
		return width;
	}
	
	public int height()
	{
		return height;
	}
	
	public int renderStartX()
	{
		return renderStartX;
	}
	
	public int renderStartY()
	{
		return renderStartY;
	}
	
	public int renderEndX()
	{
		return renderStartX + width;
	}
	
	public int renderEndY()
	{
		return renderStartY + height;
	}
	
	public int slotStartX(int x, int y)
	{
		return renderStartX + x * 16;
	}
	
	public int slotStartY(int x, int y)
	{
		return renderStartY + y * 16;
	}
	
	public int slotEndX(int x, int y)
	{
		return this.slotStartX(x, y) + slotSize;
	}
	
	public int slotEndY(int x, int y)
	{
		return this.slotStartY(x, y) + slotSize;
	}
	
	public boolean shouldUseAltTile(int x, int y)
	{
		return (x + (y % 2 == 0 ? 0 : 1)) % 2 == 0;
	}
	
	public boolean isHovering(int x, int y, int mouseX, int mouseY)
	{
		return mouseX >= this.slotStartX(x, y) && mouseX < this.slotEndX(x, y) &&
				mouseY >= this.slotStartY(x, y) && mouseY < this.slotEndY(x, y);
	}
	
	public boolean contains(int mouseX, int mouseY)
	{
		return mouseX >= renderStartX && mouseX <= this.renderEndX() &&
				mouseY >= renderStartY && mouseY <= this.renderEndY();
	}
	
	public int slotXAt(int mouseX)
	{
		int relativeX = mouseX - renderStartX;
		return relativeX / slotSize;
	}
	
	public int slotYAt(int mouseY)
	{
		int relativeY = mouseY - renderStartY;
		return relativeY / slotSize;
	}
}
