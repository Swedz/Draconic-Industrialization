package net.swedz.draconic_industrialization.module;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleBatteryItem;

public final class DracoItemEnergy extends ItemComponent
{
	private boolean valid;
	
	private long energy;
	
	public DracoItemEnergy(ItemStack stack)
	{
		super(stack);
	}
	
	private DracoItemConfiguration itemConfiguration()
	{
		return ((DracoItem) stack.getItem()).dracoConfiguration(stack);
	}
	
	public EnergyStorage createStorage(ContainerItemContext context)
	{
		DracoItemConfiguration itemConfiguration = this.itemConfiguration();
		return SimpleBatteryItem.createStorage(context, itemConfiguration.energyCapacity(), itemConfiguration.energyMaxIO(), itemConfiguration.energyMaxIO());
	}
	
	public EnergyStorage storage(ContainerItemContext context)
	{
		return context.find(EnergyStorage.ITEM);
	}
	
	public long energy()
	{
		if(!valid)
		{
			CompoundTag tag = stack.getTag();
			energy = tag == null ? 0 : (tag.contains(SimpleBatteryItem.ENERGY_KEY, CompoundTag.TAG_LONG) ? tag.getLong(SimpleBatteryItem.ENERGY_KEY) : 0);
			valid = true;
		}
		return energy;
	}
	
	public long capacity()
	{
		return this.itemConfiguration().energyCapacity();
	}
	
	public long maxIO()
	{
		return this.itemConfiguration().energyMaxIO();
	}
	
	public long insert(long amount)
	{
		long energy = this.energy();
		long newEnergy = Math.min(this.capacity(), energy + amount);
		long amountInserted = newEnergy - energy;
		this.energy = newEnergy;
		return amountInserted;
	}
	
	public long extract(long amount)
	{
		long energy = this.energy();
		long newEnergy = Math.max(0, energy - amount);
		long amountExtracted = energy - newEnergy;
		this.energy = newEnergy;
		return amountExtracted;
	}
	
	public void save()
	{
		long energy = this.energy();
		if(energy > 0)
		{
			CompoundTag tag = stack.getOrCreateTag();
			tag.putLong(SimpleBatteryItem.ENERGY_KEY, energy);
			//stack.save(new CompoundTag());
		}
		else if(stack.hasTag())
		{
			stack.getTag().remove(SimpleBatteryItem.ENERGY_KEY);
		}
	}
	
	@Deprecated
	@Override
	public void onTagInvalidated()
	{
		super.onTagInvalidated();
		valid = false;
	}
}
