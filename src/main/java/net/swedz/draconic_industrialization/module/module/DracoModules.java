package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Maps;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.module.ColorizerDracoModule;
import net.swedz.draconic_industrialization.module.module.module.ArmorAppearanceDracoModule;

import java.util.Map;

public final class DracoModules
{
	private static final Map<String, DracoModuleCreator>   CREATORS_BY_KEY   = Maps.newHashMap();
	private static final Map<Class<?>, String>             CREATORS_TO_KEY   = Maps.newHashMap();
	private static final Map<Class<?>, DracoModuleCreator> CREATORS_BY_CLASS = Maps.newHashMap();
	
	public static final Class<ColorizerDracoModule>       COLORIZER       = register(ColorizerDracoModule.class, "Colorizer", ColorizerDracoModule::new);
	public static final Class<ArmorAppearanceDracoModule> ARMOR_APPERANCE = register(ArmorAppearanceDracoModule.class, "ArmorAppearance", ArmorAppearanceDracoModule::new);
	
	private static <M extends DracoModule> Class<M> register(Class<M> moduleClass, String key, DracoModuleCreator<M> creator)
	{
		CREATORS_BY_KEY.put(key, creator);
		CREATORS_TO_KEY.put(moduleClass, key);
		CREATORS_BY_CLASS.put(moduleClass, creator);
		return moduleClass;
	}
	
	public static DracoModule create(String key, DracoItem parentItem)
	{
		return CREATORS_BY_KEY.get(key).create(key, parentItem);
	}
	
	public static <M extends DracoModule> M create(Class<M> moduleClass, DracoItem parentItem)
	{
		return (M) CREATORS_BY_CLASS.get(moduleClass).create(CREATORS_TO_KEY.get(moduleClass), parentItem);
	}
}
