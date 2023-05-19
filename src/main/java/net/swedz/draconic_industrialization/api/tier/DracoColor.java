package net.swedz.draconic_industrialization.api.tier;

import net.swedz.draconic_industrialization.api.NBTSerializer;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;

public final class DracoColor implements NBTSerializer<DracoColor>
{
	private final DracoTier parentTier;
	
	public float red, green, blue;
	
	private DracoColor(DracoTier parentTier)
	{
		this.parentTier = parentTier;
	}
	
	public static DracoColor from(DracoTier tier, NBTTagWrapper tag)
	{
		DracoColor color = new DracoColor(tier);
		color.deserialize(tag);
		return color;
	}
	
	public static DracoColor from(DracoTier tier)
	{
		return from(tier, new NBTTagWrapper());
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		float[] defaultRGB = parentTier.defaultRGB();
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
