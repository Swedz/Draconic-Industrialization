package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ValueModuleOptionWidget<V> extends ModuleOptionWidget
{
	private final Supplier<V> valueGetter;
	private final Consumer<V> valueUpdated;
	
	public ValueModuleOptionWidget(DracoScreen screen, Component label, int height, Supplier<V> valueGetter, Consumer<V> valueUpdated)
	{
		super(screen, label, height);
		this.valueGetter = valueGetter;
		this.valueUpdated = valueUpdated;
	}
	
	protected V clamp(V value)
	{
		return value;
	}
	
	public V getValue()
	{
		return valueGetter.get();
	}
	
	public void setValue(V value)
	{
		valueUpdated.accept(this.clamp(value));
	}
}
