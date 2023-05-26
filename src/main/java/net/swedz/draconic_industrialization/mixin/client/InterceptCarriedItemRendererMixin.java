package net.swedz.draconic_industrialization.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.dracomenu.menu.main.MainDracoScreen;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractContainerScreen.class)
public abstract class InterceptCarriedItemRendererMixin
{
	@WrapOperation(
			method = "render",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderFloatingItem(Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", ordinal = 0)
	)
	private void renderCarriedItemCheck(AbstractContainerScreen instance, ItemStack stack, int x, int y, String altText, Operation<Void> operation)
	{
		if(!(instance instanceof MainDracoScreen && stack.getItem() instanceof DracoModuleItem))
		{
			operation.call(instance, stack, x, y, altText);
		}
	}
}
