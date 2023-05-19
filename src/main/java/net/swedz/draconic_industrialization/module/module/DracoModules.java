package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Maps;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.module.ColorizerDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ArmorAppearanceDracoModule;

import java.util.Map;

public final class DracoModules
{
	private static final Map<String, DracoModuleCreator>   CREATORS_BY_KEY   = Maps.newHashMap();
	private static final Map<Class<?>, String>             CREATORS_TO_KEY   = Maps.newHashMap();
	private static final Map<Class<?>, DracoModuleCreator> CREATORS_BY_CLASS = Maps.newHashMap();
	
	public static final ClassReference<ColorizerDracoModule>       COLORIZER       = register(ColorizerDracoModule.class, "Colorizer", ColorizerDracoModule::new);
	public static final ClassReference<ArmorAppearanceDracoModule> ARMOR_APPERANCE = register(ArmorAppearanceDracoModule.class, "ArmorAppearance", ArmorAppearanceDracoModule::new);
	
	private static <M extends DracoModule> ClassReference<M> register(Class<M> moduleClass, String key, DracoModuleCreator<M> creator)
	{
		CREATORS_BY_KEY.put(key, creator);
		CREATORS_TO_KEY.put(moduleClass, key);
		CREATORS_BY_CLASS.put(moduleClass, creator);
		return new ClassReference<>(moduleClass);
	}
	
	public static DracoModule create(String key, DracoItem parentItem, NBTTagWrapper tag)
	{
		return CREATORS_BY_KEY.get(key)
				.create(key, parentItem)
				.deserialize(tag);
	}
	
	public static <M extends DracoModule> M create(ClassReference<M> module, DracoItem parentItem, NBTTagWrapper tag)
	{
		return (M) CREATORS_BY_CLASS.get(module.reference)
				.create(CREATORS_TO_KEY.get(module.reference), parentItem)
				.deserialize(tag);
	}
	
	public record ClassReference<M extends DracoModule>(Class<M> reference)
	{
	}
}
