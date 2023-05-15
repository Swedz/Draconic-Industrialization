package net.swedz.draconic_industrialization.items.item.draconicarmor;

import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public final class DraconicArmorRenderer extends GeoArmorRenderer<DraconicArmorItem>
{
	public DraconicArmorRenderer()
	{
		super(new DraconicArmorModel());
		
		this.bodyBone = "armorBody";
	}
}
