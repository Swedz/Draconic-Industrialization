package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.main.MainDracoScreen;
import net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

import static net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen.*;

public final class TopStuffsModuleOptionWidget extends ModuleOptionWidget
{
	private final List<FormattedCharSequence> infoTooltip;
	
	public TopStuffsModuleOptionWidget(DracoScreen screen, List<Component> infoTooltip)
	{
		super(screen, Component.empty(), 15);
		
		this.infoTooltip = infoTooltip.stream()
				.map((c) -> Minecraft.getInstance().font.split(c, Math.max(screen.width / 2, 200)))
				.flatMap(Collection::stream)
				.toList();
	}
	
	private int returnButtonX()
	{
		return 0;
	}
	
	private boolean isHoveringReturnButton(int mouseX, int mouseY)
	{
		return this.isHoveringButton(this.returnButtonX(), mouseX, mouseY);
	}
	
	private ResourceLocation returnButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/return.png");
	}
	
	private int infoIconX()
	{
		return CONTENT_WIDTH - 7;
	}
	
	private boolean isHoveringInfoIcon(int mouseX, int mouseY)
	{
		return this.isHoveringButton(this.infoIconX(), mouseX, mouseY);
	}
	
	private ResourceLocation infoIconAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/info.png");
	}
	
	@Override
	public Color color()
	{
		return screen.getMenu().getDisplayColor().toColor();
	}
	
	@Override
	public void click(int mouseX, int mouseY)
	{
		if(this.isHoveringReturnButton(mouseX, mouseY) && screen instanceof ModuleConfigDracoScreen moduleScreen)
		{
			moduleScreen.postChanges();
			moduleScreen.getMenu().setSlotLocked(false);
			Minecraft.getInstance().setScreen(new MainDracoScreen(moduleScreen.getMenu(), Minecraft.getInstance().player.getInventory(), moduleScreen.getTitle()));
		}
	}
	
	@Override
	public void render(PoseStack matrices, int x, int y, int mouseX, int mouseY)
	{
		this.renderButton(matrices, x, y, mouseX, mouseY, this.returnButtonX(), this.returnButtonAsset(), this.color());
		
		if(!infoTooltip.isEmpty())
		{
			this.renderButton(matrices, x, y, mouseX, mouseY, this.infoIconX(), this.infoIconAsset(), this.color());
			if(this.isHoveringInfoIcon(mouseX, mouseY))
			{
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(this.color().getRed() / 255f, this.color().getGreen() / 255f, this.color().getBlue() / 255f, 1);
				screen.renderTooltip(matrices, infoTooltip, x + mouseX, y + mouseY + 9);
			}
		}
	}
}
