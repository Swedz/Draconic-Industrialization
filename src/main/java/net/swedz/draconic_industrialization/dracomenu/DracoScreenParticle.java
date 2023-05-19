package net.swedz.draconic_industrialization.dracomenu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.swedz.draconic_industrialization.api.tier.DracoColor;

import java.util.Random;

public final class DracoScreenParticle
{
	public final DracoMenu menu;
	
	public final ResourceLocation asset;
	
	public long tick;
	
	public float xoff, yoff;
	
	public DracoScreenParticle(DracoMenu menu, float xoff, float yoff)
	{
		this.menu = menu;
		this.asset = new ResourceLocation("textures/particle/glitter_%d.png".formatted(new Random().nextInt(8)));
		this.xoff = xoff;
		this.yoff = yoff;
	}
	
	public void tick()
	{
		tick++;
		
		yoff += 1;
	}
	
	public void render(GuiComponent gui, PoseStack matrices, float partialTick, float x, float y)
	{
		matrices.pushPose();
		
		int vOffset = Mth.clamp((int) (2 - yoff), 0, 8);
		int vHeight = (int) ((8 - Math.max(yoff + 8 - 117, 0)) - vOffset);
		
		matrices.translate(x + xoff, y + yoff + vOffset, 0);
		
		final DracoColor color = menu.getDisplayColor();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(color.red, color.green, color.blue, 1);
		RenderSystem.setShaderTexture(0, asset);
		GuiComponent.blit(matrices, 0, 0, gui.getBlitOffset(), 0, vOffset, 8, vHeight, 8, 8);
		
		matrices.popPose();
	}
}
