package net.swedz.draconic_industrialization.module.module;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.api.attributes.AccumulatedAttributeWrappers;
import net.swedz.draconic_industrialization.api.nbt.NBTSerializerWithParam;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.DracoItemEnergy;
import net.swedz.draconic_industrialization.module.DracoModuleTick;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;

import java.util.List;

public abstract class DracoModule implements NBTSerializerWithParam<DracoModule, DracoItem>
{
	protected final DracoModuleReference reference;
	
	public DracoModule(DracoModuleReference reference)
	{
		this.reference = reference;
	}
	
	public DracoModuleReference reference()
	{
		return reference;
	}
	
	public String key()
	{
		return reference.key();
	}
	
	public DracoTier tier()
	{
		return reference.tier();
	}
	
	public DracoGridSlotShape gridShape()
	{
		return reference.gridShape();
	}
	
	public boolean applies(DracoItem item)
	{
		return item.tier().greaterThanOrEqualTo(this.tier());
	}
	
	public int max()
	{
		return Integer.MAX_VALUE;
	}
	
	public MutableComponent title()
	{
		return reference.item().getName(reference.item().getDefaultInstance()).copy();
	}
	
	public void appendTooltip(DracoItem item, List<Component> lines)
	{
	}
	
	public List<Component> tooltip(DracoItem item)
	{
		List<Component> lines = Lists.newArrayList();
		lines.add(this.title().withStyle(DracoMenuStylesheet.HEADER));
		this.appendTooltip(item, lines);
		return lines;
	}
	
	public boolean hasStuffToConfigure()
	{
		return true;
	}
	
	public void appendWidgets(DracoScreen screen, List<ModuleOptionWidget> widgets)
	{
	}
	
	public void applyAttributes(AccumulatedAttributeWrappers attributes, DracoItem item)
	{
	}
	
	public void tick(DracoModuleTick tick, ItemStack stack, DracoItemConfiguration itemConfiguration, DracoItemEnergy itemEnergy, Level level, Player player)
	{
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		tag.setString("Key", this.key());
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		tag.setString("Key", this.key());
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
