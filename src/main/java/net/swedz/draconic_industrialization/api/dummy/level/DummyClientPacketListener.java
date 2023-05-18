package net.swedz.draconic_industrialization.api.dummy.level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;

public class DummyClientPacketListener extends ClientPacketListener
{
	private static DummyClientPacketListener instance;
	
	public static DummyClientPacketListener getInstance()
	{
		if(instance == null)
		{
			instance = new DummyClientPacketListener();
		}
		return instance;
	}
	
	private DummyClientPacketListener()
	{
		super(
				Minecraft.getInstance(),
				null,
				new Connection(PacketFlow.CLIENTBOUND),
				Minecraft.getInstance().getUser().getGameProfile(),
				Minecraft.getInstance().createTelemetryManager()
		);
	}
}
