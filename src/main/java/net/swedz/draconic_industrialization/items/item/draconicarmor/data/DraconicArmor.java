package net.swedz.draconic_industrialization.items.item.draconicarmor.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.DracoTier;
import net.swedz.draconic_industrialization.api.NBTSerializer;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;

public final class DraconicArmor implements NBTSerializer
{
	private static final String PARENT_KEY = "DraconicArmorData";
	
	public final DracoTier tier;
	
	public DraconicArmorModelType  modelType;
	public DraconicArmorShieldType shieldType;
	public Color                   color;
	
	private DraconicArmor(DracoTier tier)
	{
		this.tier = tier;
	}
	
	public static DraconicArmor fromItemStack(ItemStack itemStack)
	{
		DraconicArmor draconicArmor = new DraconicArmor(((DraconicArmorItem) itemStack.getItem()).tier());
		CompoundTag parentTag = itemStack.getOrCreateTag();
		CompoundTag tag = parentTag.contains(PARENT_KEY) ? parentTag.getCompound(PARENT_KEY) : new CompoundTag();
		draconicArmor.deserialize(tag);
		return draconicArmor;
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		modelType = tag.getEnumOrDefault(DraconicArmorModelType.class, "ModelType", DraconicArmorModelType.DEFAULT);
		shieldType = tag.getEnumOrDefault(DraconicArmorShieldType.class, "ShieldType", DraconicArmorShieldType.DEFAULT);
		
		color = new Color();
		color.deserialize(tag.getOrEmpty("Color"));
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.setEnum("ModelType", modelType);
		tag.setEnum("ShieldType", shieldType);
		tag.set("Color", color.serialize());
	}
	
	public void writeToItemStack(ItemStack itemStack)
	{
		CompoundTag parentTag = itemStack.getOrCreateTag();
		CompoundTag tag = this.serialize();
		parentTag.put(PARENT_KEY, tag);
	}
	
	public final class Color implements NBTSerializer
	{
		public float red, green, blue;
		
		private Color()
		{
		}
		
		@Override
		public void read(NBTTagWrapper tag)
		{
			float[] defaultRGB = DraconicArmor.this.tier.defaultRGB();
			red = tag.getFloatOrDefault("r", defaultRGB[0]);
			green = tag.getFloatOrDefault("g", defaultRGB[1]);
			blue = tag.getFloatOrDefault("b", defaultRGB[2]);
		}
		
		@Override
		public void write(NBTTagWrapper tag)
		{
			tag.setFloat("r", red);
			tag.setFloat("g", green);
			tag.setFloat("b", blue);
		}
	}
}
