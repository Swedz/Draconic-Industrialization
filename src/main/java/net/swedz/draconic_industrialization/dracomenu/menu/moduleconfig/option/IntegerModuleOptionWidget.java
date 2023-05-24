package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class IntegerModuleOptionWidget extends LeftRightValueModuleOptionWidget<Integer>
{
	private final int minimumValue, maximumValue;
	
	public IntegerModuleOptionWidget(DracoScreen screen, Component label, int height, int minimumValue, int maximumValue, Supplier<Integer> valueGetter, Consumer<Integer> valueUpdated)
	{
		super(screen, label, height, valueGetter, valueUpdated);
		this.minimumValue = minimumValue;
		this.maximumValue = maximumValue;
	}
	
	@Override
	protected int valueWidth()
	{
		return 40;
	}
	
	@Override
	protected ResourceLocation leftButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/minus.png");
	}
	
	@Override
	protected ResourceLocation rightButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/plus.png");
	}
	
	@Override
	protected boolean isLeftEnabled()
	{
		return this.getValue() > minimumValue;
	}
	
	@Override
	protected boolean isRightEnabled()
	{
		return this.getValue() < maximumValue;
	}
	
	private boolean isHoldingShift()
	{
		return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
	}
	
	@Override
	protected Integer leftValue()
	{
		return this.getValue() - (this.isHoldingShift() ? 16 : 1);
	}
	
	@Override
	protected Integer rightValue()
	{
		return this.getValue() + (this.isHoldingShift() ? 16 : 1);
	}
	
	@Override
	protected Integer clamp(Integer value)
	{
		return Mth.clamp(value, minimumValue, maximumValue);
	}
	
	@Override
	protected Component toText(Integer value)
	{
		return Component.literal(Integer.toString(value));
	}
	
	@Override
	public Color color()
	{
		return DracoMenuStylesheet.COLOR_CONTENT;
	}
}
