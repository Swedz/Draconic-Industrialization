package net.swedz.draconic_industrialization.items.item.draconicarmor;

import net.swedz.draconic_industrialization.api.DracoTier;

public enum DraconicArmorModelType
{
	STRAP,
	NULL(false);
	
	private final boolean shouldRender;
	
	DraconicArmorModelType(boolean shouldRender)
	{
		this.shouldRender = shouldRender;
	}
	
	DraconicArmorModelType()
	{
		this(true);
	}
	
	public String id()
	{
		return this.name().toLowerCase();
	}
	
	public boolean shouldRender()
	{
		return shouldRender;
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
