package net.swedz.draconic_industrialization.items.item.draconicarmor;

public enum DraconicArmorTier
{
	WYVERN(new float[]{0.47f, 0.29f, 0.64f}, "textures/item/wyvern_armor.png"),
	DRACONIC(new float[]{0.98f, 0.36f, 0.19f}, "textures/item/draconic_armor.png"),
	CHAOTIC(new float[]{1f, 0f, 0f}, "textures/item/chaotic_armor.png");
	
	private final float[] defaultRGB;
	private final String asset;
	
	DraconicArmorTier(float[] defaultRGB, String asset)
	{
		this.defaultRGB = defaultRGB;
		this.asset = asset;
	}
	
	public float[] defaultRGB()
	{
		return defaultRGB;
	}
	
	public String asset()
	{
		return asset;
	}
}
