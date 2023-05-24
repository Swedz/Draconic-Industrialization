package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.awt.Color;

public final class SpacingModuleOptionWidget extends ModuleOptionWidget
{
	public SpacingModuleOptionWidget(DracoScreen screen, int spacing)
	{
		super(screen, Component.empty(), spacing);
	}
	
	@Override
	public Color color()
	{
		return Color.BLACK;
	}
	
	@Override
	public void click(int mouseX, int mouseY)
	{
		// Nothing
	}
	
	@Override
	public void render(PoseStack matrices, int x, int y, int mouseX, int mouseY)
	{
		// Nothing
	}
}
