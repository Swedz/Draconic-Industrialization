package net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.option;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoMenuStylesheet;
import net.swedz.draconic_industrialization.dracomenu.menu.DracoScreen;

import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.swedz.draconic_industrialization.dracomenu.menu.moduleconfig.ModuleConfigDracoScreen.*;

public final class HexFromClipboardModuleOptionWidget extends ValueModuleOptionWidget<String>
{
	private static final Pattern HEX_PATTERN = Pattern.compile("^(#?)([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$");
	
	public HexFromClipboardModuleOptionWidget(DracoScreen screen, Component label, int height, Supplier<String> valueGetter, Consumer<String> valueUpdated)
	{
		super(screen, label, height, valueGetter, valueUpdated);
	}
	
	private int valueTextX()
	{
		return this.clipboardButtonX() - 30;
	}
	
	private int clipboardButtonX()
	{
		return CONTENT_WIDTH - 7;
	}
	
	private boolean isHoveringClipboardButton(int mouseX, int mouseY)
	{
		return this.isHoveringButton(this.clipboardButtonX(), mouseX, mouseY);
	}
	
	private ResourceLocation clipboardButtonAsset()
	{
		return DraconicIndustrialization.id("textures/gui/draco_menu/clipboard.png");
	}
	
	@Override
	public Color color()
	{
		return DracoMenuStylesheet.COLOR_CONTENT;
	}
	
	@Override
	protected String clamp(String value)
	{
		Matcher matcher = HEX_PATTERN.matcher(value);
		if(matcher.matches())
		{
			return matcher.group(2);
		}
		else
		{
			return this.getValue();
		}
	}
	
	@Override
	public void click(int mouseX, int mouseY)
	{
		if(this.isHoveringClipboardButton(mouseX, mouseY))
		{
			this.setValue(Minecraft.getInstance().keyboardHandler.getClipboard());
		}
	}
	
	@Override
	public void render(PoseStack matrices, int x, int y, int mouseX, int mouseY)
	{
		final Font font = Minecraft.getInstance().font;
		
		GuiComponent.drawString(matrices, font, label, x, y, this.color().getRGB());
		
		Component valueText = Component.literal("#" + this.getValue());
		GuiComponent.drawCenteredString(matrices, font, valueText, x + this.valueTextX(), y, this.color().getRGB());
		
		this.renderButton(matrices, x, y, mouseX, mouseY, this.clipboardButtonX(), this.clipboardButtonAsset(), this.color());
		
		if(this.isHoveringClipboardButton(mouseX, mouseY))
		{
			final Color menuColor = screen.getMenu().getDisplayColor().toColor();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(menuColor.getRed() / 255f, menuColor.getGreen() / 255f, menuColor.getBlue() / 255f, 1);
			screen.renderTooltip(matrices, Component.translatable("draco_menu.module.generic.clipboard").withStyle(DracoMenuStylesheet.CONTENT), x + mouseX, y + mouseY + 9);
		}
	}
}
