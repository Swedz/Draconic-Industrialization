package net.swedz.draconic_industrialization.dracomenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
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
	
	private static final ResourceLocation TOP_GRID_TILE0 = DraconicIndustrialization.id("textures/gui/draco_menu/tile0.png");
	private static final ResourceLocation TOP_GRID_TILE1 = DraconicIndustrialization.id("textures/gui/draco_menu/tile1.png");
	
	private static final ResourceLocation SELECTED_SLOT_OVERLAY = DraconicIndustrialization.id("textures/gui/draco_menu/selected_slot.png");
	
	private long tick;
	
	private DracoDummyPlayer playerRender;
	
	public DracoScreen(DracoMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		
		DracoMenu.SELECTED_ITEM_CHANGED_SCREEN_CALLBACK = (from, to) -> playerRender.updateDueToSelectedItemChange(to);
		
		imageWidth = 242;
		imageHeight = 235;
		
		playerRender = new DracoDummyPlayer(Minecraft.getInstance().player);
		playerRender.pickDefaultSelectedItem(menu).ifPresent(menu::setSelectedItem);
	}
	
	@Override
	protected void containerTick()
	{
		tick++;
		playerRender.tickCount++;
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
		
		if(menu.hasSelectedItem())
		{
			int posX = leftPos + 60;
			int posY = topPos + 88;
			InventoryScreen.renderEntityInInventory(posX, posY, 30, (float) posX - mouseX, (float) posY - 50 - mouseY, playerRender);
		}
		
		this.renderTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack matrices, float partialTick, int mouseX, int mouseY)
	{
		this.fillGradient(matrices, 0, 0, width, height, -1072689136, -804253680);
		
		if(menu.hasSelectedItem())
		{
			final DracoColor color = menu.getDisplayColor();
			
			/*{
				float minX = leftPos + 35;
				float minY = topPos + 2;
				float maxX = minX + 172;
				float maxY = minY + 115;
				
				RenderSystem.enableBlend();
				RenderSystem.disableTexture();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShader(GameRenderer::getRendertypeEndPortalShader);
				RenderSystem.setShaderTexture(0, TheEndPortalRenderer.END_PORTAL_LOCATION);
				Matrix4f matrix = matrices.last().pose();
				MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
				BufferBuilder buffer = (BufferBuilder) bufferSource.getBuffer(RenderType.endPortal());
				buffer.vertex(matrix, minX, maxY, 0f).endVertex();
				buffer.vertex(matrix, maxX, maxY, 0f).endVertex();
				buffer.vertex(matrix, maxX, minY, 0f).endVertex();
				buffer.vertex(matrix, minX, minY, 0f).endVertex();
				BufferUploader.drawWithShader(buffer.end());
				RenderSystem.enableTexture();
				RenderSystem.disableBlend();
			}*/
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1, 1, 1, 1);
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BACKGROUND);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BORDER);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
		}
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.setShaderTexture(0, BACKGROUND_BOTTOM);
		this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
	}
	
	@Override
	protected void renderSlot(PoseStack matrices, Slot slot)
	{
		super.renderSlot(matrices, slot);
		
		if(menu.getSelectedItem().matches(slot))
		{
			final int ticksPerCycle = 2;
			final DracoColor color = menu.getDisplayColor();
			RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
			RenderSystem.setShaderTexture(0, SELECTED_SLOT_OVERLAY);
			blit(matrices, slot.x, slot.y, this.getBlitOffset(), 0, (int) ((tick / ticksPerCycle) % 4) * 16, 16, 16, 16, 64);
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}
	}
}
