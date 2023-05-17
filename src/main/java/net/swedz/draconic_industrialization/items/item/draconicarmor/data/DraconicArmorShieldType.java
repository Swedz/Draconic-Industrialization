package net.swedz.draconic_industrialization.items.item.draconicarmor.data;

public enum DraconicArmorShieldType
{
	BARRIER("Barrier"),
	BLOBBY("Blobby");
	
	public static final DraconicArmorShieldType DEFAULT = BARRIER;
	
	private final String englishName;
	
	DraconicArmorShieldType(String englishName)
	{
		this.englishName = englishName;
	}
	
	public String id()
	{
		return this.name().toLowerCase();
	}
	
	public String englishName()
	{
		return englishName;
	}
	
	public String translationKey()
	{
		return "draconic_armor.draconic_industrialization.shield_type.%s".formatted(this.id());
	}
	
	public String texture()
	{
		return "textures/draconic_armor_shield/%s.png".formatted(this.id());
	}
}
