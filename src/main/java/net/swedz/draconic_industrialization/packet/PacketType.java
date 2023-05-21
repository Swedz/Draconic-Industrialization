package net.swedz.draconic_industrialization.packet;

public enum PacketType
{
	CLIENTBOUND(DIPacketChannels.Clientbound::init),
	SERVERBOUND(DIPacketChannels.Serverbound::init);
	
	private final Runnable classLoader;
	
	PacketType(Runnable classLoader)
	{
		this.classLoader = classLoader;
	}
	
	public void init()
	{
		classLoader.run();
	}
}
