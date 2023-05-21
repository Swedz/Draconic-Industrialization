package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.nbt.NBTSerializerWithParam;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.dracomodule.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.util.List;

public abstract class DracoModule implements NBTSerializerWithParam<DracoModule, DracoItem>
{
	protected final DracoModuleReference reference;
	
	protected final String             key;
	protected final DracoGridSlotShape gridShape;
	
	public DracoModule(DracoModuleReference reference)
	{
		this.reference = reference;
		this.key = reference.key();
		this.gridShape = reference.gridShape();
	}
	
	public DracoModuleReference reference()
	{
		return reference;
	}
	
	public String key()
	{
		return key;
	}
	
	public DracoGridSlotShape gridShape()
	{
		return gridShape;
	}
	
	public boolean applies(DracoItem item)
	{
		return true;
	}
	
	public int max()
	{
		return Integer.MAX_VALUE;
	}
	
	public abstract void appendTooltip(DracoItem item, List<Component> lines);
	
	public List<Component> tooltip(DracoItem item)
	{
		List<Component> lines = Lists.newArrayList();
		lines.add(reference.item().getName(reference.item().getDefaultInstance()));
		this.appendTooltip(item, lines);
		return lines;
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		tag.setString("Key", key);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		tag.setString("Key", key);
	}
	
	public ItemStack itemize(DracoItem parent)
	{
		ItemStack itemStack = new ItemStack(reference.item());
		
		CompoundTag tag = new CompoundTag();
		
		CompoundTag defaultModuleTag = reference.create().deserialize(new CompoundTag(), parent).serialize(parent);
		CompoundTag moduleTag = this.serialize(parent);
		
		if(!defaultModuleTag.equals(moduleTag))
		{
			tag.put(DracoModuleItem.PARENT_KEY, this.serialize(parent));
			itemStack.getOrCreateTag().merge(tag);
			itemStack.save(new CompoundTag());
		}
		
		return itemStack;
	}
}
