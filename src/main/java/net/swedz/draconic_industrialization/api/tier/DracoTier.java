package net.swedz.draconic_industrialization.api.tier;

public enum DracoTier
{
	WYVERN(0.47f, 0.29f, 0.64f),
	DRACONIC(0.98f, 0.36f, 0.19f),
	CHAOTIC(1f, 0f, 0f);
	
	private final float[] defaultRGB;
	
	DracoTier(float red, float green, float blue)
	{
		this.defaultRGB = new float[]{red, green, blue};
	}
	
	public String id()
	{
		return this.name().toLowerCase();
	}
	
	public float[] defaultRGB()
	{
		return defaultRGB;
	}
	
	public boolean greaterThanOrEqualTo(DracoTier tier)
	{
		return this.ordinal() >= tier.ordinal();
	}
}
