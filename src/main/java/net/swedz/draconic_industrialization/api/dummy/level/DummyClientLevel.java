package net.swedz.draconic_industrialization.api.dummy.level;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.swedz.draconic_industrialization.DraconicIndustrialization;

public class DummyClientLevel extends ClientLevel
{
	private static DummyClientLevel instance;
	
	public static DummyClientLevel getInstance()
	{
		if(instance == null)
		{
			instance = new DummyClientLevel();
		}
		return instance;
	}
	
	private DummyClientLevel()
	{
		super(
				DummyClientPacketListener.getInstance(),
				new ClientLevelData(Difficulty.EASY, false, true),
				ResourceKey.create(Registry.DIMENSION_REGISTRY, DraconicIndustrialization.id("dummy")),
				BuiltinRegistries.DIMENSION_TYPE.getHolder(BuiltinDimensionTypes.OVERWORLD).orElseThrow(() -> new IllegalStateException("My hungry ass could never be left alone with the overworld dimension type")),
				0, 0, () -> null, null, false, 0
		);
	}
}
