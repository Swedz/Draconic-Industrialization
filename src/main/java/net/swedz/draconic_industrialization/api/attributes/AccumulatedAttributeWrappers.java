package net.swedz.draconic_industrialization.api.attributes;

import com.google.common.collect.Maps;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;

public final class AccumulatedAttributeWrappers
{
	private final Map<AttributeWrapper, Double> map = Maps.newHashMap();
	
	public void add(Attribute attribute, UUID id, String name, AttributeModifier.Operation operation, EquipmentSlot slot, double value)
	{
		AttributeWrapper wrapper = new AttributeWrapper(attribute, id, name, operation, slot);
		double currentValue = map.getOrDefault(wrapper, 0D);
		map.put(wrapper, currentValue + value);
	}
	
	public void add(Attribute attribute, UUID id, String name, AttributeModifier.Operation operation, double value)
	{
		this.add(attribute, id, name, operation, null, value);
	}
	
	public void apply(ItemStack stack)
	{
		map.forEach((key, value) -> key.apply(stack, value));
	}
}
