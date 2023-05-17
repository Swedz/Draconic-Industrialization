package net.swedz.draconic_industrialization.items.item.draconicarmor.render.shield;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.item.draconicarmor.data.DraconicArmorShieldType;

import java.util.Map;

import static net.minecraft.client.renderer.RenderStateShard.*;

public final class ShieldRenderTypes
{
	private static final Map<DraconicArmorShieldType, ShieldRenderWrapper> RENDERERS = Maps.newHashMap();
	
	public static VertexConsumer getVertexConsumer(DraconicArmorShieldType shieldType, MultiBufferSource buffer, float tick)
	{
		return RENDERERS.get(shieldType).get(buffer, tick);
	}
	
	private static void include(DraconicArmorShieldType type, TextureOffsetCalculator textureOffsetCalculator, RenderTypeCreator renderTypeCreator)
	{
		RENDERERS.put(type, new ShieldRenderWrapper(type, textureOffsetCalculator, renderTypeCreator));
	}
	
	public static final TextureOffsetCalculator OFFSET_DIAGONAL = (tick) ->
	{
		float speed = 0.005f;
		return new TextureOffset(
				Mth.cos(tick * speed) * 0.5F % 1.0F,
				tick * speed * 0.75F % 1.0F
		);
	};
	
	public static final RenderTypeCreator RENDERTYPE_EXPANDED = (textureOffset, shieldType) ->
			RenderType.create(
					"draconic_armor__expanded__%s".formatted(shieldType.id()),
					DefaultVertexFormat.NEW_ENTITY,
					VertexFormat.Mode.QUADS,
					256,
					false,
					true,
					RenderType.CompositeState.builder()
							.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
							.setTextureState(new TextureStateShard(DraconicIndustrialization.id(shieldType.texture()), false, false))
							.setTexturingState(textureOffset.shard())
							.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
							.setCullState(NO_CULL)
							.setLightmapState(LIGHTMAP)
							.setOverlayState(OVERLAY)
							.createCompositeState(false)
			);
	
	public static final RenderTypeCreator RENDERTYPE_FLAT = (textureOffset, shieldType) ->
			RenderType.create(
					"draconic_armor__flat__%s".formatted(shieldType.id()),
					DefaultVertexFormat.NEW_ENTITY,
					VertexFormat.Mode.QUADS,
					256,
					false,
					true,
					RenderType.CompositeState.builder()
							.setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
							.setTextureState(new TextureStateShard(DraconicIndustrialization.id(shieldType.texture()), false, false))
							.setTexturingState(textureOffset.shard())
							.setTransparencyState(ADDITIVE_TRANSPARENCY)
							.setCullState(NO_CULL)
							.setDepthTestState(EQUAL_DEPTH_TEST)
							.setLightmapState(LIGHTMAP)
							.setOverlayState(OVERLAY)
							.createCompositeState(false)
			);
	
	static
	{
		include(DraconicArmorShieldType.BARRIER, OFFSET_DIAGONAL, RENDERTYPE_EXPANDED);
		include(DraconicArmorShieldType.BLOBBY, OFFSET_DIAGONAL, RENDERTYPE_FLAT);
	}
	
	public record TextureOffset(float x, float y)
	{
		public OffsetTexturingStateShard shard()
		{
			return new OffsetTexturingStateShard(x, y);
		}
	}
	
	public interface TextureOffsetCalculator
	{
		TextureOffset calc(float tick);
	}
	
	public interface RenderTypeCreator
	{
		RenderType renderType(TextureOffset textureOffset, DraconicArmorShieldType shieldType);
	}
	
	public record ShieldRenderWrapper(
			DraconicArmorShieldType shieldType,
			TextureOffsetCalculator textureOffsetCalculator, RenderTypeCreator renderTypeCreator
	)
	{
		public VertexConsumer get(MultiBufferSource buffer, float tick)
		{
			return buffer.getBuffer(renderTypeCreator.renderType(textureOffsetCalculator.calc(tick), shieldType));
		}
	}
}
