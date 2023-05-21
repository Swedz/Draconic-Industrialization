package net.swedz.draconic_industrialization.dracomenu.menu.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.DracoMenuGridHelper;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.items.item.DracoModuleItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.module.grid.DracoGridEntry;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.InsertModuleDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.StartConfigureModuleDracoMenuPacket;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.TakeModuleDracoMenuPacket;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.Optional;

public final class MainDracoScreen extends DracoScreen<MainDracoMenu>
{
	private static final Color TILE_COLOR_0     = new Color(63, 63, 63, 255);
	private static final Color TILE_COLOR_1     = new Color(40, 40, 40, 255);
	private static final Color TILE_HOVER_COLOR = new Color(255, 255, 255, 128);
	
	public MainDracoScreen(MainDracoMenu menu, Inventory inventory, Component title)
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
			else if(rightClick)
			{
				StartConfigureModuleDracoMenuPacket packet = DIPacketChannels.Serverbound.DRACO_MENU_START_CONFIGURE_MODULE.createPacket();
				packet.slotX = slotX;
				packet.slotY = slotY;
				packet.send();
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
		super.renderBg(matrices, partialTick, mouseX, mouseY);
		
		if(menu.hasSelectedItem())
		{
			this.renderGrid(matrices, mouseX, mouseY, menu.getDisplayColor(), menu.getSelectedItemConfiguration());
		}
	}
}
