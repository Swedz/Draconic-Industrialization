package net.swedz.draconic_industrialization.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.swedz.draconic_industrialization.tags.DITags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public class PreventEnchantmentsForDraconicArmor
{
	@ModifyReturnValue(
			method = "canEnchant",
			at = @At("RETURN")
	)
	private boolean checkIfNotDraconic(boolean original, ItemStack itemStack)
	{
		return original && !itemStack.is(DITags.Items.DRACONIC_ARMOR);
	}
}
