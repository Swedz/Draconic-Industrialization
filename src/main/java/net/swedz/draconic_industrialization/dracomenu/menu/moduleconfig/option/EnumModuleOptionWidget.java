package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class EnumModuleOptionWidget<E extends Enum<E>> extends LeftRightValueModuleOptionWidget<E>
{
	private final Class<E> enumClass;
	
	private final Function<E, Component> textGetter;
	
	public EnumModuleOptionWidget(DracoMenu menu, Component label, Class<E> enumClass, Supplier<E> valueGetter, Consumer<E> valueUpdated, Function<E, Component> textGetter)
	{
		super(menu, label, 7, valueGetter, valueUpdated);
		this.enumClass = enumClass;
		this.textGetter = textGetter;
	}
	
	@Override
	protected int valueWidth()
	{
		return 50;
	}
	
	@Override
	protected ResourceLocation leftButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/left.png");
	}
	
	@Override
	protected ResourceLocation rightButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/right.png");
	}
	
	@Override
	protected E leftValue()
	{
		return enumClass.getEnumConstants()[(this.getValue().ordinal() - 1 + enumClass.getEnumConstants().length) % enumClass.getEnumConstants().length];
	}
	
	@Override
	protected E rightValue()
	{
		return enumClass.getEnumConstants()[(this.getValue().ordinal() + 1) % enumClass.getEnumConstants().length];
	}
	
	@Override
	protected Component toText(E value)
	{
		return textGetter.apply(value);
	}
	
	@Override
	public Color color()
	{
		return new Color(200, 200, 200, 255);
	}
}
