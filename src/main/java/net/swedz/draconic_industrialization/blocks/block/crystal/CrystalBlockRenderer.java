package net.swedz.draconic_industrialization.blocks.block.crystal;

import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CrystalBlockRenderer extends GeoBlockRenderer<CrystalBlockEntity>
{
	public CrystalBlockRenderer()
	{
		super(new CrystalBlockModel());
	}
}
