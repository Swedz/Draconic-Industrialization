package net.swedz.draconic_industrialization.api;

import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

import java.util.function.Consumer;

public abstract class MCThingBuilder<M, S, T extends MCThingBuilder>
{
	protected String id;
	protected String englishName;
	
	protected S settings = this.defaultSettings();
	
	protected Creator<M, S> creator = this.defaultCreator();
	
	public T identifiable(String id, String englishName)
	{
		this.id = id;
		this.englishName = englishName;
		return (T) this;
	}
	
	public ResourceLocation encloseId()
	{
		return DraconicIndustrialization.id(id);
	}
	
	protected S defaultSettings()
	{
		return null;
	}
	
	public T withSettings(S settings)
	{
		this.settings = settings;
		return (T) this;
	}
	
	public T withSettings(Consumer<S> settingsConsumer)
	{
		settingsConsumer.accept(settings);
		return (T) this;
	}
	
	protected abstract Creator<M, S> defaultCreator();
	
	public T creator(Creator<M, S> creator)
	{
		this.creator = creator;
		return (T) this;
	}
	
	protected abstract void commonRegister(M main, S settings);
	
	public M build()
	{
		M main = creator.create(settings);
		this.commonRegister(main, settings);
		return main;
	}
	
	public interface Creator<M, S>
	{
		M create(S settings);
	}
}
