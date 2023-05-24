package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen.*;

public abstract class LeftRightValueModuleOptionWidget<V> extends ValueModuleOptionWidget<V>
{
	public LeftRightValueModuleOptionWidget(DracoScreen screen, Component label, int height, Supplier<V> valueGetter, Consumer<V> valueUpdated)
	{
		super(screen, label, height, valueGetter, valueUpdated);
	}
	
	protected abstract int valueWidth();
	
	protected int leftButtonX()
	{
		return this.rightButtonX() - this.valueWidth() - 7;
	}
	
	protected int valueTextX()
	{
		return this.rightButtonX() - this.valueWidth() / 2;
	}
	
	protected int rightButtonX()
	{
		return CONTENT_WIDTH - 7;
	}
	
	protected boolean isHoveringLeftButton(int mouseX, int mouseY)
	{
		return this.isHoveringButton(this.leftButtonX(), mouseX, mouseY);
	}
	
	protected boolean isHoveringRightButton(int mouseX, int mouseY)
	{
		return this.isHoveringButton(this.rightButtonX(), mouseX, mouseY);
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
	
	@Override
	public void click(int mouseX, int mouseY)
	{
		if(this.isLeftEnabled() && this.isHoveringLeftButton(mouseX, mouseY))
		{
			this.setValue(this.clamp(this.leftValue()));
		}
		else if(this.isRightEnabled() && this.isHoveringRightButton(mouseX, mouseY))
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
			this.renderButton(matrices, x, y, mouseX, mouseY, this.leftButtonX(), this.leftButtonAsset(), this.color());
		}
		
		Component valueText = this.toText(this.getValue());
		GuiComponent.drawCenteredString(matrices, font, valueText, x + this.valueTextX(), y, this.color().getRGB());
		
		if(this.isRightEnabled())
		{
			this.renderButton(matrices, x, y, mouseX, mouseY, this.rightButtonX(), this.rightButtonAsset(), this.color());
		}
	}
}
