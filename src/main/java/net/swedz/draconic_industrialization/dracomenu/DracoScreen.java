package net.swedz.draconic_industrialization.dracomenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;

public final class DracoScreen extends AbstractContainerScreen<DracoMenu>
{
	private static final ResourceLocation BACKGROUND_TOP_BACKGROUND = DraconicIndustrialization.id("textures/gui/draco_menu/top_background.png");
	private static final ResourceLocation BACKGROUND_TOP_BORDER = DraconicIndustrialization.id("textures/gui/draco_menu/top_border.png");
	private static final ResourceLocation BACKGROUND_BOTTOM = DraconicIndustrialization.id("textures/gui/draco_menu/bottom.png");
	
	private static final ResourceLocation SELECTED_SLOT_OVERLAY = DraconicIndustrialization.id("textures/gui/draco_menu/selected_slot.png");
	
	public DracoScreen(DracoMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		imageWidth = 242;
		imageHeight = 235;
	}
	
	@Override
	protected void renderLabels(PoseStack matrices, int mouseX, int mouseY)
	{
		//font.draw(poseStack, title, (float) titleLabelX, (float) titleLabelY, 4210752);
		//font.draw(matrices, playerInventoryTitle, (float) inventoryLabelX, (float) inventoryLabelY, 4210752);
	}
	
	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.render(matrices, mouseX, mouseY, partialTick);
		
		// TODO
		//  make entity render as if its wearing/holding the selected item or default to the actually worn/hold item
		//  make this fade in when draco item is selected
		int posX = leftPos - 25;
		int posY = topPos + 75;
		InventoryScreen.renderEntityInInventory(posX, posY, 30, (float) posX - mouseX, (float) posY - 50 - mouseY, minecraft.player);
		
		this.renderTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack matrices, float partialTick, int mouseX, int mouseY)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		if(menu.hasSelectedItem())
		{
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BACKGROUND);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
			
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BORDER);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		}
		
		RenderSystem.setShaderTexture(0, BACKGROUND_BOTTOM);
		this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
	
	@Override
	protected void renderSlot(PoseStack matrices, Slot slot)
	{
		super.renderSlot(matrices, slot);
		
		if(menu.getSelectedItem().matches(slot))
		{
			final DracoColor color = menu.getDisplayColor();
			RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
			RenderSystem.setShaderTexture(0, SELECTED_SLOT_OVERLAY);
			blit(matrices, slot.x, slot.y, this.getBlitOffset(), 0, 0, 16, 16, 16, 16);
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}
	}
}
