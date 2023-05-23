package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.tier.DracoColor;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.main.MainDracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.PostModuleConfigChangesDracoMenuPacket;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public final class ModuleConfigDracoScreen extends DracoScreen<DracoMenu>
{
	private static final ResourceLocation RETURN = DraconicIndustrialization.id("textures/gui/draco_menu/return.png");
	
	private static final int CONTENT_XOFF = 94;
	private static final int RETURN_YOFF  = 5;
	private static final int OPTIONS_YOFF = 20;
	
	public static final int OPTIONS_WIDTH  = 106;
	public static final int OPTIONS_HEIGHT = 93;
	
	private final int moduleSlotX, moduleSlotY;
	private final DracoModule module;
	
	private final List<ModuleOptionWidget> widgets = Lists.newArrayList();
	
	public ModuleConfigDracoScreen(DracoMenu menu, Inventory inventory, Component title, int moduleSlotX, int moduleSlotY, DracoModule module)
	{
		super(menu, inventory, title);
		
		this.moduleSlotX = moduleSlotX;
		this.moduleSlotY = moduleSlotY;
		this.module = module;
		module.appendWidgets(menu, widgets);
		
		int optionsY = 0;
		for(ModuleOptionWidget widget : widgets)
		{
			widget.init(optionsY);
			optionsY += widget.height() + 4;
		}
	}
	
	private void postChanges()
	{
		PostModuleConfigChangesDracoMenuPacket packet = DIPacketChannels.Serverbound.DRACO_MENU_POST_MODULE_CONFIG_CHANGES.createPacket();
		packet.slotX = moduleSlotX;
		packet.slotY = moduleSlotY;
		packet.module = module;
		packet.send();
	}
	
	private int contentX()
	{
		return leftPos + CONTENT_XOFF;
	}
	
	private int optionsY()
	{
		return topPos + OPTIONS_YOFF;
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int button)
	{
		if(mx >= this.contentX() && mx < this.contentX() + 7 && my >= topPos + RETURN_YOFF && my < topPos + RETURN_YOFF + 7)
		{
			this.postChanges();
			menu.setSlotLocked(false);
			Minecraft.getInstance().setScreen(new MainDracoScreen(menu, Minecraft.getInstance().player.getInventory(), title));
			return true;
		}
		if(mx >= this.contentX() && mx < this.contentX() + OPTIONS_WIDTH && my >= this.optionsY() && my < this.optionsY() + OPTIONS_HEIGHT)
		{
			int relativeMouseX = (int) (mx - this.contentX());
			int relativeMouseY = (int) (my - this.optionsY());
			for(ModuleOptionWidget widget : widgets)
			{
				if(widget.contains(relativeMouseY))
				{
					widget.click(relativeMouseX, relativeMouseY - widget.relativeY());
					break;
				}
			}
			return true;
		}
		return super.mouseClicked(mx, my, button);
	}
	
	@Override
	public void render(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.render(matrices, mouseX, mouseY, partialTick);
		
		final DracoColor color = menu.getDisplayColor();
		
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
		RenderSystem.setShaderTexture(0, RETURN);
		blit(matrices, this.contentX(), topPos + RETURN_YOFF, this.getBlitOffset(), 0, 0, 7, 7, 7, 7);
		
		for(ModuleOptionWidget widget : widgets)
		{
			widget.render(matrices, this.contentX(), this.optionsY() + widget.relativeY(), mouseX - this.contentX(), mouseY - this.optionsY() - widget.relativeY());
		}
	}
	
	@Override
	public void onClose()
	{
		this.postChanges();
		super.onClose();
	}
}
