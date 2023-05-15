package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public final class DraconicArmorMaterial implements ArmorMaterial
{
	@Override
	public int getDurabilityForSlot(EquipmentSlot slot)
	{
		return 0;
	}
	
	@Override
	public int getDefenseForSlot(EquipmentSlot slot)
	{
		return 0;
	}
	
	@Override
	public int getEnchantmentValue()
	{
		return 0;
	}
	
	@Override
	public SoundEvent getEquipSound()
	{
		return SoundEvents.ARMOR_EQUIP_NETHERITE;
	}
	
	@Override
	public Ingredient getRepairIngredient()
	{
		return null;
	}
	
	@Override
	public String getName()
	{
		return "draconic";
	}
	
	@Override
	public float getToughness()
	{
		return 0;
	}
	
	@Override
	public float getKnockbackResistance()
	{
		return 0;
	}
}
