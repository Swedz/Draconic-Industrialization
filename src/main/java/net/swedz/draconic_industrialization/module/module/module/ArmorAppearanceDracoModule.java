package net.swedz.draconic_industrialization.module.module.module;

import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;

public final class ArmorAppearanceDracoModule extends DracoModule
{
	public DraconicArmorModelType  model;
	public DraconicArmorShieldType shield;
	
	public ArmorAppearanceDracoModule(String key, DracoItem parentItem)
	{
		super(key, parentItem);
	}
	
	@Override
	public boolean applies()
	{
		return parentItem instanceof DraconicArmorItem;
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		super.read(tag);
		model = tag.getEnumOrDefault(DraconicArmorModelType.class, "Model", DraconicArmorModelType.DEFAULT);
		shield = tag.getEnumOrDefault(DraconicArmorShieldType.class, "Shield", DraconicArmorShieldType.DEFAULT);
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		super.write(tag);
		tag.setEnum("Model", model);
		tag.setEnum("Shield", shield);
	}
}
