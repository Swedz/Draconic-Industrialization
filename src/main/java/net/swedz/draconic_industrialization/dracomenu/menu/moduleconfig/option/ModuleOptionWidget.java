package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.awt.Color;

public abstract class ModuleOptionWidget
{
	protected final DracoScreen screen;
	
	protected final Component label;
	protected final int       height;
	
	protected int relativeY;
	
	public ModuleOptionWidget(DracoScreen screen, Component label, int height)
	{
		this.screen = screen;
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
	
	protected boolean isHoveringButton(int xOffset, int mouseX, int mouseY)
	{
		return mouseX >= xOffset && mouseX < xOffset + 7 &&
				mouseY >= 0 && mouseY < 7;
	}
	
	protected void renderButton(PoseStack matrices, int x, int y, int mouseX, int mouseY,
								int xOffset, ResourceLocation asset, Color color)
	{
		x += xOffset;
		
		if(this.isHoveringButton(xOffset, mouseX, mouseY))
		{
			RenderSystem.setShaderColor(1, 1, 1, 1);
			GuiComponent.fill(matrices, x - 1, y - 1, x + 8, y + 8, color.getRGB());
			GuiComponent.fill(matrices, x, y, x + 7, y + 7, Color.BLACK.getRGB());
		}
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1);
		RenderSystem.setShaderTexture(0, asset);
		GuiComponent.blit(matrices, x, y, 0, 0, 0, 7, 7, 7, 7);
		
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}
	
	public abstract Color color();
	
	public abstract void click(int mouseX, int mouseY);
	
	public abstract void render(PoseStack matrices, int x, int y, int mouseX, int mouseY);
}
