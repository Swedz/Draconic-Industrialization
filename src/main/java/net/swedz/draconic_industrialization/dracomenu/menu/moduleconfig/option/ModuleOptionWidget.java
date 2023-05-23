package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;

import java.awt.Color;

public abstract class ModuleOptionWidget
{
	protected final DracoMenu menu;
	protected final Component label;
	protected final int       height;
	
	protected int relativeY;
	
	public ModuleOptionWidget(DracoMenu menu, Component label, int height)
	{
		this.menu = menu;
		this.label = label;
		this.height = height;
	}
	
	public Component label()
	{
		return label;
	}
	
	public int height()
	{
		return height;
	}
	
	public void init(int y)
	{
		this.relativeY = y;
	}
	
	public int relativeY()
	{
		return relativeY;
	}
	
	public boolean contains(int mouseY)
	{
		return mouseY >= relativeY && mouseY < relativeY + height;
	}
	
	public abstract void click(int mouseX, int mouseY);
	
	public abstract Color color();
	
	public abstract void render(PoseStack matrices, int x, int y, int mouseX, int mouseY);
}
