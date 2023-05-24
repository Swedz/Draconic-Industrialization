package net.swedz.draconic_industrialization.api.attributes;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public record AttributeWrapper(Attribute attribute, UUID id, String name, AttributeModifier.Operation operation, EquipmentSlot slot)
{
	public AttributeWrapper(Attribute attribute, UUID id, String name, AttributeModifier.Operation operation)
	{
		this(attribute, id, name, operation, null);
	}
	
	public void apply(ItemStack stack, double value)
	{
		stack.addAttributeModifier(attribute, new AttributeModifier(id, name, value, operation), slot);
	}
}
