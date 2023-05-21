package net.swedz.draconic_industrialization.dracomenu;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.grid.DracoMenuGridHelper;
import net.swedz.draconic_industrialization.dracomenu.render.DracoDummyPlayer;
import net.swedz.draconic_industrialization.dracomenu.render.DracoScreenParticle;
import net.swedz.draconic_industrialization.items.item.dracomodule.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridEntry;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public final class DracoScreen extends AbstractContainerScreen<DracoMenu>
{
	private static final ResourceLocation BACKGROUND_TOP_BACKGROUND = DraconicIndustrialization.id("textures/gui/draco_menu/top_background.png");
	private static final ResourceLocation BACKGROUND_TOP_BORDER     = DraconicIndustrialization.id("textures/gui/draco_menu/top_border.png");
	private static final ResourceLocation BACKGROUND_BOTTOM         = DraconicIndustrialization.id("textures/gui/draco_menu/bottom.png");
	
	private static final Color TILE_COLOR_0 = new Color(63, 63, 63, 255);
	private static final Color TILE_COLOR_1 = new Color(40, 40, 40, 255);
	private static final Color TILE_HOVER_COLOR = new Color(255, 255, 255, 128);
	
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
		playerRender.updateDueToSelectedItemChange(menu.getSelectedItem()); // we have to call this because SELECTED_ITEM_CHANGED_SCREEN_CALLBACK only just now got initialized and this is initialized AFTER the menu is initialized
	}
	
	private DracoMenuGridHelper gridHelper()
	{
		return new DracoMenuGridHelper(menu.getSelectedItem().item().gridSize(), leftPos, topPos, 16);
	}
	
	private boolean clickedDracoGridSlot(DracoMenuGridHelper gridHelper, int slotX, int slotY, Optional<DracoGridEntry> optionalEntry)
	{
		boolean hasCarried = !menu.getCarried().isEmpty();
		if(hasCarried && menu.getCarried().getItem() instanceof DracoModuleItem moduleItem && optionalEntry.isEmpty())
		{
			FriendlyByteBuf packet = PacketByteBufs.create();
			packet.writeInt(slotX);
			packet.writeInt(slotY);
			//packet.writeItem(menu.getCarried()); // this is likely unnecessary, since the server knows what your carried item is
			ClientPlayNetworking.send(DIPacketChannels.ClientToServer.DRACO_MENU_INSERT_ITEM, packet);
			return true;
		}
		else if(!hasCarried && optionalEntry.isPresent())
		{
			FriendlyByteBuf packet = PacketByteBufs.create();
			packet.writeInt(slotX);
			packet.writeInt(slotY);
			ClientPlayNetworking.send(DIPacketChannels.ClientToServer.DRACO_MENU_TAKE_ITEM, packet);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int button)
	{
		if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && menu.hasSelectedItem())
		{
			int mouseX = (int) mx;
			int mouseY = (int) my;
			
			final DracoMenuGridHelper gridHelper = this.gridHelper();
			if(gridHelper.contains(mouseX, mouseY))
			{
				DracoItemConfiguration configuration = menu.getSelectedItemConfiguration();
				int slotX = gridHelper.slotXAt(mouseX);
				int slotY = gridHelper.slotYAt(mouseY);
				return this.clickedDracoGridSlot(gridHelper, slotX, slotY, configuration.grid().get(slotX, slotY));
			}
		}
		return super.mouseClicked(mx, my, button);
	}
	
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
	
	@Override
	protected void renderLabels(PoseStack matrices, int mouseX, int mouseY)
	{
		// No, thank you
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
		
		if(menu.hasSelectedItem() && menu.getCarried().isEmpty())
		{
			final DracoMenuGridHelper gridHelper = this.gridHelper();
			final DracoItemConfiguration itemConfiguration = menu.getSelectedItemConfiguration();
			
			itemConfiguration.grid().get(gridHelper.slotXAt(mouseX), gridHelper.slotYAt(mouseY)).ifPresent((entry) ->
					this.renderComponentTooltip(matrices, entry.module().tooltip(itemConfiguration.item()), mouseX, mouseY));
		}
	}
	
	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.render(matrices, mouseX, mouseY, partialTick);
		
		if(menu.hasSelectedItem())
		{
			particles.forEach((p) -> p.render(this, matrices, partialTick, leftPos, topPos));
			
			this.renderPlayerPreview(mouseX, mouseY);
		}
		
		this.renderTooltip(matrices, mouseX, mouseY);
	}
	
	private void renderGrid(PoseStack matrices, int mouseX, int mouseY, DracoColor color, DracoItemConfiguration itemConfiguration)
	{
		RenderSystem.setShaderColor(1, 1, 1, 1);
		
		final DracoMenuGridHelper gridHelper = this.gridHelper();
		
		fill(matrices, gridHelper.renderStartX() - 1, gridHelper.renderStartY() - 1, gridHelper.renderEndX() + 1, gridHelper.renderEndY() + 1, color.toRGB());
		
		for(int coordX = 0; coordX < gridHelper.size().width(); coordX++)
		{
			for(int coordY = 0; coordY < gridHelper.size().height(); coordY++)
			{
				int x = gridHelper.slotStartX(coordX, coordY);
				int mx = gridHelper.slotEndX(coordX, coordY);
				int y = gridHelper.slotStartY(coordX, coordY);
				int my = gridHelper.slotEndY(coordX, coordY);
				
				fill(matrices, x, y, mx, my, (gridHelper.shouldUseAltTile(coordX, coordY) ? TILE_COLOR_0 : TILE_COLOR_1).getRGB());
				
				itemConfiguration.grid().getExactly(coordX, coordY).ifPresent((entry) ->
						itemRenderer.renderGuiItem(new ItemStack(entry.module().reference().item()), x, y));
				
				if(gridHelper.isHovering(coordX, coordY, mouseX, mouseY))
				{
					renderSlotHighlight(matrices, x, y, this.getBlitOffset());
				}
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
			
			this.renderGrid(matrices, mouseX, mouseY, color, menu.getSelectedItemConfiguration());
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
