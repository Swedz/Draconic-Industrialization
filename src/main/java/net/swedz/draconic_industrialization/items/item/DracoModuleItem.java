package net.swedz.draconic_industrialization.items.item;

import net.minecraft.world.item.Item;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

public final class DracoModuleItem<M extends DracoModule> extends Item
{
	public static final String PARENT_KEY = "DracoModule";
	
	private final DracoModuleReference<M> moduleReference;
	
	public DracoModuleItem(DracoModuleReference<M> moduleReference, Properties properties)
	{
		super(properties);
		this.moduleReference = moduleReference;
	}
	
	public DracoModuleReference<M> moduleReference()
	{
		return moduleReference;
	}
}
