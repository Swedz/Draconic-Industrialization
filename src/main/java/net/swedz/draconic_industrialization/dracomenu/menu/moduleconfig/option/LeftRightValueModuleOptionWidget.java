package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen.*;

public abstract class LeftRightValueModuleOptionWidget<V> extends ValueModuleOptionWidget<V>
{
	public LeftRightValueModuleOptionWidget(DracoMenu menu, Component label, int height, Supplier<V> valueGetter, Consumer<V> valueUpdated)
	{
		super(menu, label, height, valueGetter, valueUpdated);
	}
	
	protected abstract int valueWidth();
	
	protected int leftButtonX()
	{
		return OPTIONS_WIDTH - 7 - this.valueWidth() - 7;
	}
	
	protected int valueTextX()
	{
		return OPTIONS_WIDTH - 7 - this.valueWidth() / 2;
	}
	
	protected int rightButtonX()
	{
		return OPTIONS_WIDTH - 7;
	}
	
	protected boolean hoveringLeftButton(int mouseX, int mouseY)
	{
		return mouseX >= this.leftButtonX() && mouseX < this.leftButtonX() + 7 &&
				mouseY >= 0 && mouseY < height;
	}
	
	protected boolean hoveringRightButton(int mouseX, int mouseY)
	{
		return mouseX >= this.rightButtonX() && mouseX < this.rightButtonX() + 7 &&
				mouseY >= 0 && mouseY < height;
	}
	
	protected boolean isLeftEnabled()
	{
		return true;
	}
	
	protected boolean isRightEnabled()
	{
		return true;
	}
	
	protected abstract ResourceLocation leftButtonAsset();
	
	protected abstract ResourceLocation rightButtonAsset();
	
	protected abstract V leftValue();
	
	protected abstract V rightValue();
	
	protected abstract Component toText(V value);
	
	protected V clamp(V value)
	{
		return value;
	}
	
	@Override
	public void click(int mouseX, int mouseY)
	{
		if(this.isLeftEnabled() && this.hoveringLeftButton(mouseX, mouseY))
		{
			this.setValue(this.clamp(this.leftValue()));
		}
		else if(this.isRightEnabled() && this.hoveringRightButton(mouseX, mouseY))
		{
			this.setValue(this.clamp(this.rightValue()));
		}
	}
	
	@Override
	public void render(PoseStack matrices, int x, int y, int mouseX, int mouseY)
	{
		final Font font = Minecraft.getInstance().font;
		
		GuiComponent.drawString(matrices, font, label, x, y, this.color().getRGB());
		
		if(this.isLeftEnabled())
		{
			int minusX = x + this.leftButtonX();
			
			if(this.hoveringLeftButton(mouseX, mouseY))
			{
				GuiComponent.fill(matrices, minusX - 1, y - 1, minusX + 8, y + 8, this.color().getRGB());
				GuiComponent.fill(matrices, minusX, y, minusX + 7, y + 7, Color.BLACK.getRGB());
			}
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(this.color().getRed() / 255f, this.color().getGreen() / 255f, this.color().getBlue() / 255f, 1);
			RenderSystem.setShaderTexture(0, this.leftButtonAsset());
			GuiComponent.blit(matrices, minusX, y, 0, 0, 0, 7, 7, 7, 7);
		}
		
		Component valueText = this.toText(this.getValue());
		GuiComponent.drawCenteredString(matrices, font, valueText, x + this.valueTextX(), y, this.color().getRGB());
		
		if(this.isRightEnabled())
		{
			int plusX = x + this.rightButtonX();
			
			if(this.hoveringRightButton(mouseX, mouseY))
			{
				GuiComponent.fill(matrices, plusX - 1, y - 1, plusX + 8, y + 8, this.color().getRGB());
				GuiComponent.fill(matrices, plusX, y, plusX + 7, y + 7, Color.BLACK.getRGB());
			}
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(this.color().getRed() / 255f, this.color().getGreen() / 255f, this.color().getBlue() / 255f, 1);
			RenderSystem.setShaderTexture(0, this.rightButtonAsset());
			GuiComponent.blit(matrices, plusX, y, 0, 0, 0, 7, 7, 7, 7);
		}
	}
}
