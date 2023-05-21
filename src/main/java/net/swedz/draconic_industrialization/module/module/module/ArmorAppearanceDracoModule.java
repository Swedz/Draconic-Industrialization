package net.swedz.draconic_industrialization.module.module.module;

import net.minecraft.network.chat.Component;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorModelType;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorShieldType;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.util.List;

public final class ArmorAppearanceDracoModule extends DracoModule
{
	public DraconicArmorModelType  model;
	public DraconicArmorShieldType shield;
	
	public ArmorAppearanceDracoModule(DracoModuleReference reference)
	{
		super(reference);
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return item instanceof DraconicArmorItem;
	}
	
	@Override
	public int max()
	{
		return 1;
	}
	
	@Override
	public void appendTooltip(DracoItem item, List<Component> lines)
	{
		// TODO
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		model = tag.getEnumOrDefault(DraconicArmorModelType.class, "Model", DraconicArmorModelType.DEFAULT);
		shield = tag.getEnumOrDefault(DraconicArmorShieldType.class, "Shield", DraconicArmorShieldType.DEFAULT);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.setEnum("Model", model);
		tag.setEnum("Shield", shield);
	}
}
