package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenu;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option.ModuleOptionWidget;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import net.swedz.draconic_industrialization.packet.serverbound.dracomenu.PostModuleConfigChangesDracoMenuPacket;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public final class ModuleConfigDracoScreen extends DracoScreen
{
	private static final int CONTENT_XOFF   = 94;
	private static final int CONTENT_YOFF   = 5;
	public static final  int CONTENT_WIDTH  = 106;
	public static final  int CONTENT_HEIGHT = 93;
	
	private final int moduleSlotX, moduleSlotY;
	private final DracoModule module;
	
	private final List<ModuleOptionWidget> widgets = Lists.newArrayList();
	
	public ModuleConfigDracoScreen(DracoMenu menu, Inventory inventory, Component title, int moduleSlotX, int moduleSlotY, DracoModule module)
	{
		super(menu, inventory, title);
		
		this.moduleSlotX = moduleSlotX;
		this.moduleSlotY = moduleSlotY;
		this.module = module;
		module.appendWidgets(this, widgets);
		
		int optionsY = 0;
		for(ModuleOptionWidget widget : widgets)
		{
			widget.init(optionsY);
			optionsY += widget.height() + 4;
		}
	}
	
	public void postChanges()
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
	
	private int contentY()
	{
		return topPos + CONTENT_YOFF;
	}
	
	@Override
	public boolean mouseClicked(double mx, double my, int button)
	{
		if(mx >= this.contentX() && mx < this.contentX() + CONTENT_WIDTH && my >= this.contentY() && my < this.contentY() + CONTENT_HEIGHT)
		{
			int relativeMouseX = (int) (mx - this.contentX());
			int relativeMouseY = (int) (my - this.contentY());
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
	
	private void renderWidgets(PoseStack matrices, int mouseX, int mouseY)
	{
		for(ModuleOptionWidget widget : widgets)
		{
			widget.render(matrices, this.contentX(), this.contentY() + widget.relativeY(), mouseX - this.contentX(), mouseY - this.contentY() - widget.relativeY());
		}
	}
	
	@Override
	protected void renderLate(PoseStack matrices, int mouseX, int mouseY, float partialTick)
	{
		super.renderLate(matrices, mouseX, mouseY, partialTick);
		
		this.renderWidgets(matrices, mouseX, mouseY);
	}
	
	@Override
	public void onClose()
	{
		this.postChanges();
		super.onClose();
	}
}
