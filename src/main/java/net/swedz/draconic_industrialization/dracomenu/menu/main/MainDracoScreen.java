package net.swedz.draconic_industrialization.dracomenu.menu.main;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.DracoMenuGridHelper;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridEntry;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlot;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridSlotShape;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.InsertModuleDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.TakeModuleDracoMenuPacket;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.Optional;

public final class MainDracoScreen extends DracoScreen
{
	private static final Color TILE_COLOR_0     = new Color(63, 63, 63, 255);
	private static final Color TILE_COLOR_1     = new Color(40, 40, 40, 255);
	private static final Color TILE_HOVER_COLOR = new Color(255, 255, 255, 128);
	
	public MainDracoScreen(DracoMenu menu, Inventory inventory, Component title)
	{
		super(menu, inventory, title);
	}
	
	private boolean clickedDracoGridSlot(DracoMenuGridHelper gridHelper, int slotX, int slotY, int button, Optional<DracoGridEntry> optionalEntry)
	{
		boolean leftClick = button == GLFW.GLFW_MOUSE_BUTTON_LEFT;
		boolean rightClick = button == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
		boolean hasCarried = !menu.getCarried().isEmpty();
		// Left clicking an empty grid slot with a module item
		if(leftClick && hasCarried && menu.getCarried().getItem() instanceof DracoModuleItem moduleItem && optionalEntry.isEmpty())
		{
			InsertModuleDracoMenuPacket packet = DIPacketChannels.Serverbound.DRACO_MENU_INSERT_MODULE.createPacket();
			packet.slotX = slotX;
			packet.slotY = slotY;
			packet.send();
			return true;
		}
		// Clicking a filled grid slot with an empty cursor
		else if(!hasCarried && optionalEntry.isPresent())
		{
			final DracoGridEntry entry = optionalEntry.get();
			// Extract the module from the slot
			if(leftClick)
			{
				TakeModuleDracoMenuPacket packet = DIPacketChannels.Serverbound.DRACO_MENU_TAKE_MODULE.createPacket();
				packet.slotX = slotX;
				packet.slotY = slotY;
				packet.send();
				return true;
			}
			// Open the module's config menu
			else if(rightClick && entry.module().hasStuffToConfigure())
			{
				menu.setSlotLocked(true);
				Minecraft.getInstance().setScreen(new ModuleConfigDracoScreen(menu, Minecraft.getInstance().player.getInventory(), title, entry.x(), entry.y(), entry.module()));
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int button)
	{
		if(menu.hasSelectedItem())
		{
			int mouseX = (int) mx;
			int mouseY = (int) my;
			
			final DracoMenuGridHelper gridHelper = this.gridHelper();
			if(gridHelper.contains(mouseX, mouseY))
			{
				DracoItemConfiguration configuration = menu.getSelectedItemConfiguration();
				int slotX = gridHelper.slotXAt(mouseX);
				int slotY = gridHelper.slotYAt(mouseY);
				return this.clickedDracoGridSlot(gridHelper, slotX, slotY, button, configuration.grid().get(slotX, slotY));
			}
		}
		return super.mouseClicked(mx, my, button);
	}
	
	@SuppressWarnings("deprecation")
	private void renderModuleItem(ItemStack stack, int x, int y, boolean scale)
	{
		stack = stack.copy();
		if(scale)
		{
			stack.getOrCreateTag().putBoolean(DraconicIndustrialization.id("in_draco_gui").toString(), true);
		}
		
		final DracoModuleItem moduleItem = (DracoModuleItem) stack.getItem();
		
		final DracoGridSlotShape shape = moduleItem.moduleReference().gridShape();
		final float xScale = scale ? shape.width() : 1;
		final float yScale = scale ? shape.height() : 1;
		
		this.setBlitOffset(200);
		itemRenderer.blitOffset = 200;
		
		BakedModel model = itemRenderer.getModel(stack, null, null, 0);
		
		minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
		RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1, 1, 1, 1);
		PoseStack matrices = RenderSystem.getModelViewStack();
		matrices.pushPose();
		matrices.translate(x, y, this.getBlitOffset() + 100);
		matrices.translate(8 * xScale, 8 * yScale, 0);
		matrices.scale(1, -1, 1);
		matrices.scale(16, 16, 16);
		matrices.scale(xScale, yScale, 1);
		RenderSystem.applyModelViewMatrix();
		PoseStack matrices2 = new PoseStack();
		MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		boolean doesntUseBlockLight = !model.usesBlockLight();
		if(doesntUseBlockLight)
		{
			Lighting.setupForFlatItems();
		}
		
		itemRenderer.render(stack, ItemTransforms.TransformType.GUI, false, matrices2, bufferSource, 15728880, OverlayTexture.NO_OVERLAY, model);
		bufferSource.endBatch();
		RenderSystem.enableDepthTest();
		if(doesntUseBlockLight)
		{
			Lighting.setupFor3DItems();
		}
		
		matrices.popPose();
		RenderSystem.applyModelViewMatrix();
		
		this.setBlitOffset(0);
		itemRenderer.blitOffset = 0;
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
				
				Optional<DracoGridEntry> optionalEntry = itemConfiguration.grid().get(coordX, coordY);
				if(optionalEntry.isPresent())
				{
					DracoGridEntry entry = optionalEntry.get();
					if(entry.x() == coordX && entry.y() == coordY)
					{
						this.renderModuleItem(new ItemStack(entry.module().reference().item()), x, y, true);
					}
					if(gridHelper.isHovering(coordX, coordY, mouseX, mouseY))
					{
						for(DracoGridSlot slot : entry.occupiedSlots())
						{
							renderSlotHighlight(
									matrices,
									gridHelper.slotStartX(slot.x(), slot.y()),
									gridHelper.slotStartY(slot.x(), slot.y()),
									this.getBlitOffset()
							);
						}
					}
				}
				else if(gridHelper.isHovering(coordX, coordY, mouseX, mouseY))
				{
					renderSlotHighlight(matrices, x, y, this.getBlitOffset());
				}
			}
		}
	}
	
	@Override
	protected void renderBg(PoseStack matrices, float partialTick, int mouseX, int mouseY)
	{
		super.renderBg(matrices, partialTick, mouseX, mouseY);
		
		if(menu.hasSelectedItem())
		{
			this.renderGrid(matrices, mouseX, mouseY, menu.getDisplayColor(), menu.getSelectedItemConfiguration());
		}
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
				{
					final Color color = menu.getDisplayColor().toColor();
					RenderSystem.setShader(GameRenderer::getPositionTexShader);
					RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1);
					this.renderComponentTooltip(matrices, entry.module().tooltip(itemConfiguration.item()), mouseX, mouseY);
				});
			}
		}
	}
	
	private void renderCarriedItem(ItemStack itemStack, int mouseX, int mouseY)
	{
		boolean scale = mouseY > topPos && mouseY < topPos + 116 &&
				mouseX > leftPos + 34 && mouseX < leftPos + 209;
		this.renderModuleItem(itemStack, mouseX - 8, mouseY - 8, scale);
	}
	
	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.render(matrices, mouseX, mouseY, partialTick);
		
		ItemStack itemStack = draggingItem.isEmpty() ? menu.getCarried() : draggingItem;
		if(!itemStack.isEmpty() && itemStack.getItem() instanceof DracoModuleItem)
		{
			this.renderCarriedItem(itemStack, mouseX, mouseY);
		}
	}
}
