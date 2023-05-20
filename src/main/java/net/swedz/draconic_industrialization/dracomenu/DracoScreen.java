package net.swedz.draconic_industrialization.dracomenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSize;
import org.apache.commons.compress.utils.Lists;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public final class DracoScreen extends AbstractContainerScreen<DracoMenu>
{
	private static final ResourceLocation BACKGROUND_TOP_BACKGROUND = DraconicIndustrialization.id("textures/gui/draco_menu/top_background.png");
	private static final ResourceLocation BACKGROUND_TOP_BORDER     = DraconicIndustrialization.id("textures/gui/draco_menu/top_border.png");
	private static final ResourceLocation BACKGROUND_BOTTOM         = DraconicIndustrialization.id("textures/gui/draco_menu/bottom.png");
	
	private static final Color TILE_COLOR_0 = new Color(63, 63, 63, 255);
	private static final Color TILE_COLOR_1 = new Color(40, 40, 40, 255);
	
	private static final ResourceLocation SELECTED_SLOT_OVERLAY = DraconicIndustrialization.id("textures/gui/draco_menu/selected_slot.png");
	
	private long tick;
	
	private final List<DracoScreenParticle> particles = Lists.newArrayList();
	
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
		
		if(menu.hasSelectedItem())
		{
			if(tick % 10 == 0)
			{
				float x = 36 + (new Random().nextFloat(42));
				particles.add(new DracoScreenParticle(menu, x, -7));
			}
			
			particles.forEach(DracoScreenParticle::tick);
			
			particles.removeIf((p) -> p.yoff >= 117);
		}
		else if(!particles.isEmpty())
		{
			particles.clear();
		}
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
			particles.forEach((p) -> p.render(this, matrices, partialTick, leftPos, topPos));
			
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
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1, 1, 1, 1);
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BACKGROUND);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
			
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
			RenderSystem.setShaderTexture(0, BACKGROUND_TOP_BORDER);
			this.blit(matrices, leftPos, topPos, 0, 0, imageWidth, imageHeight);
			
			{
				RenderSystem.setShaderColor(1, 1, 1, 1);
				
				final DracoGridSize gridSize = menu.getSelectedItem().item().gridSize();
				
				final int gridMinXPos = leftPos + 87;
				final int gridMinYPos = topPos + 4;
				final int gridMaxWidth = 118;
				final int gridMaxHeight = 110;
				final int gridWidth = gridSize.width() * 16;
				final int gridHeight = gridSize.height() * 16;
				
				final int gridStartX = gridMinXPos + gridMaxWidth / 2 - gridWidth / 2;
				final int gridStartY = gridMinYPos + gridMaxHeight / 2 - gridHeight / 2;
				
				GuiComponent.fill(matrices, gridStartX - 1, gridStartY - 1, gridStartX + gridWidth + 1, gridStartY + gridHeight + 1, color.toRGB());
				
				for(int coordX = 0; coordX < gridSize.width(); coordX++)
				{
					for(int coordY = 0; coordY < gridSize.height(); coordY++)
					{
						int x = gridStartX + coordX * 16;
						int y = gridStartY + coordY * 16;
						boolean useAltTile = (coordX + (coordY % 2 == 0 ? 0 : 1)) % 2 == 0;
						GuiComponent.fill(matrices, x, y, x + 16, y + 16, (useAltTile ? TILE_COLOR_0 : TILE_COLOR_1).getRGB());
					}
				}
			}
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
