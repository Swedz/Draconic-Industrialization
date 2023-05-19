package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.swedz.draconic_industrialization.api.tier.DracoTier;

public enum DraconicArmorModelType
{
	STRAP("Strap"),
	NULL("Null", false);
	
	public static final DraconicArmorModelType DEFAULT = STRAP;
	
	private final String  englishName;
	private final boolean shouldRender;
	
	DraconicArmorModelType(String englishName, boolean shouldRender)
	{
		this.englishName = englishName;
		this.shouldRender = shouldRender;
	}
	
	DraconicArmorModelType(String englishName)
	{
		this(englishName, true);
	}
	
	public String id()
	{
		return this.name().toLowerCase();
	}
	
	public String englishName()
	{
		return englishName;
	}
	
	public boolean shouldRender()
	{
		return shouldRender;
	}
	
	public String translationKey()
	{
		return "draconic_armor.draconic_industrialization.model_type.%s".formatted(this.id());
	}
	
	public String model()
	{
		return "geo/draconic_armor/%s.geo.json".formatted(this.id());
	}
	
	public String texture(DracoTier tier)
	{
		return "textures/item/draconic_armor/%s/%s.png".formatted(this.id(), tier.id());
	}
	
	public String animation()
	{
		return "animations/draconic_armor/%s.animation.json".formatted(this.id());
	}
}
