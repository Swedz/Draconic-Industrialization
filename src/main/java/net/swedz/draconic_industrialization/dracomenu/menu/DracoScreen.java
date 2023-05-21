package net.swedz.draconic_industrialization.dracomenu.menu;

import com.google.common.collect.Lists;
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
import net.swedz.draconic_industrialization.dracomenu.DracoItemStack;
import net.swedz.draconic_industrialization.dracomenu.DracoMenuGridHelper;
import net.swedz.draconic_industrialization.dracomenu.render.DracoDummyPlayer;
import net.swedz.draconic_industrialization.dracomenu.render.DracoScreenParticle;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;

import java.util.List;
import java.util.Random;

public abstract class DracoScreen<M extends DracoMenu> extends AbstractContainerScreen<M>
{
	public static final ResourceLocation BACKGROUND_TOP_BACKGROUND = DraconicIndustrialization.id("textures/gui/draco_menu/top_background.png");
	public static final ResourceLocation BACKGROUND_TOP_BORDER     = DraconicIndustrialization.id("textures/gui/draco_menu/top_border.png");
	public static final ResourceLocation BACKGROUND_BOTTOM         = DraconicIndustrialization.id("textures/gui/draco_menu/bottom.png");
	public static final ResourceLocation SELECTED_SLOT_OVERLAY = DraconicIndustrialization.id("textures/gui/draco_menu/selected_slot.png");
	
	protected long tick;
	
	private final List<DracoScreenParticle> particles = Lists.newArrayList();
	
	private DracoDummyPlayer playerRender;
	
	public DracoScreen(M menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
		
		menu.setDummyPlayerUpdater((from, to) -> playerRender.updateDueToSelectedItemChange(to));
		
		imageWidth = 242;
		imageHeight = 235;
		
		playerRender = new DracoDummyPlayer(Minecraft.getInstance().player);
		playerRender.updateDueToSelectedItemChange(menu.getSelectedItem()); // we have to call this because dummyPlayerUpdater only just now got initialized and this is initialized AFTER the menu is initialized
	}
	
	protected DracoMenuGridHelper gridHelper()
	{
		return new DracoMenuGridHelper(menu.getSelectedItem().item().gridSize(), leftPos, topPos, 16);
	}
	
	// Tick handling
	
	@Override
	protected void containerTick()
	{
		tick++;
		playerRender.tickCount++;
		
		if(menu.hasSelectedItem())
		{
			if(menu.getSelectedItem().stack().isEmpty() || !(menu.getSelectedItem().stack().getItem() instanceof DracoItem))
			{
				menu.setSelectedItem(DracoItemStack.EMPTY);
				DraconicIndustrialization.LOGGER.error("Somehow someone changed our selected item... that wasn't very kind of you!");
				return;
			}
			
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
	
	// Our rendering
	
	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.render(matrices, mouseX, mouseY, partialTick);
		
		if(menu.hasSelectedItem())
		{
			this.renderParticles(matrices, partialTick);
			
			this.renderPlayerPreview(mouseX, mouseY);
		}
		
		this.renderTooltip(matrices, mouseX, mouseY);
	}
	
	private void renderParticles(PoseStack matrices, float partialTick)
	{
		particles.forEach((p) -> p.render(this, matrices, partialTick, leftPos, topPos));
	}
	
	private void renderPlayerPreview(int mouseX, int mouseY)
	{
		int previewPosX = leftPos + 60;
		int previewPosY = topPos + 88;
		InventoryScreen.renderEntityInInventory(previewPosX, previewPosY, 30, (float) previewPosX - mouseX, (float) previewPosY - 50 - mouseY, playerRender);
	}
	
	@Override
	protected void renderTooltip(PoseStack matrices, int mouseX, int mouseY)
	{
		super.renderTooltip(matrices, mouseX, mouseY);
		
		this.renderGridTooltip(matrices, mouseX, mouseY);
	}
	
	private void renderGridTooltip(PoseStack matrices, int mouseX, int mouseY)
	{
		if(menu.hasSelectedItem() && menu.getCarried().isEmpty())
		{
			final DracoMenuGridHelper gridHelper = this.gridHelper();
			if(gridHelper.contains(mouseX, mouseY))
			{
				final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
				
				itemConfiguration.grid().get(gridHelper.slotXAt(mouseX), gridHelper.slotYAt(mouseY)).ifPresent((entry) ->
						this.renderComponentTooltip(matrices, entry.module().tooltip(itemConfiguration.item()), mouseX, mouseY));
			}
		}
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
	
	// Vanilla overrides, we don't want this stuff doing stuff
	
	@Override
	protected void renderLabels(PoseStack matrices, int mouseX, int mouseY)
	{
		// No, thank you
	}
}
