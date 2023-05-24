package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.awt.Color;

public final class LabelModuleOptionWidget extends ModuleOptionWidget
{
	private final int color;
	
	public LabelModuleOptionWidget(DracoScreen screen, Component label, int color)
	{
		super(screen, label, 7);
		this.color = color;
	}
	
	@Override
	public Color color()
	{
		return new Color(color);
	}
	
	@Override
	public void click(int mouseX, int mouseY)
	{
	}
	
	@Override
	public void render(PoseStack matrices, int x, int y, int mouseX, int mouseY)
	{
		final Font font = Minecraft.getInstance().font;
		
		GuiComponent.drawString(matrices, font, label, x, y, color);
	}
}
