package net.swedz.draconic_industrialization.api.tier;

import net.swedz.draconic_industrialization.api.nbt.NBTSerializer;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;

import java.util.Objects;

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
	
	public static DracoColor interpolate(DracoColor a, DracoColor b, float fractionFromA)
	{
		DracoColor interpolated = new DracoColor(null);
		
		float fractionFromB = 1 - fractionFromA;
		interpolated.red = a.red * fractionFromB + b.red * fractionFromB;
		interpolated.green = a.green * fractionFromB + b.green * fractionFromB;
		interpolated.blue = a.blue * fractionFromB + b.blue * fractionFromB;
		
		return interpolated;
	}
	
	public int toRGB()
	{
		return ((0xFF) << 24) |
				(((int) (red * 255) & 0xFF) << 16) |
				(((int) (green * 255) & 0xFF) << 8)  |
				(((int) (blue * 255) & 0xFF));
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
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof DracoColor otherColor &&
				red == otherColor.red &&
				green == otherColor.green &&
				blue == otherColor.blue;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(red, green, blue);
	}
}
