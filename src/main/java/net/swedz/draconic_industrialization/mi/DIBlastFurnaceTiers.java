package net.swedz.draconic_industrialization.mi;

import aztech.modern_industrialization.machines.blockentities.multiblocks.ElectricBlastFurnaceBlockEntity.Tier;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

import java.util.Set;

public final class DIBlastFurnaceTiers
{
	private static final Set<Tier> TIERS = Sets.newHashSet();
	
	public static final Tier ADAMANTINE = create("Adamantine", 192, "adamantine_coil");
	
	public static Set<Tier> all()
	{
		return Set.copyOf(TIERS);
	}
	
	private static Tier create(String englishName, int maxBaseEu, ResourceLocation coilBlock)
	{
		Tier tier = new Tier(coilBlock, maxBaseEu, englishName);
		TIERS.add(tier);
		return tier;
	}
	
	private static Tier create(String englishName, int maxBaseEu, String coilBlock)
	{
		return create(englishName, maxBaseEu, DraconicIndustrialization.id(coilBlock));
	}
}
