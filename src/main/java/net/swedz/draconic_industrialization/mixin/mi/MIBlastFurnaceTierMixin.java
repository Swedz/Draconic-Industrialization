package net.swedz.draconic_industrialization.mixin.mi;

import aztech.modern_industrialization.machines.blockentities.multiblocks.ElectricBlastFurnaceBlockEntity;
import net.swedz.draconic_industrialization.mi.DIBlastFurnaceTiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(ElectricBlastFurnaceBlockEntity.class)
public class MIBlastFurnaceTierMixin
{
	@ModifyVariable(
			method = "<clinit>",
			at = @At(value = "INVOKE", target = "Ljava/util/List;sort(Ljava/util/Comparator;)V"),
			remap = false
	)
	private static List clinit(List value)
	{
		value.addAll(DIBlastFurnaceTiers.all());
		return value;
	}
}
