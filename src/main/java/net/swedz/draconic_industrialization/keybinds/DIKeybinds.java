package net.swedz.draconic_industrialization.keybinds;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.swedz.draconic_industrialization.packet.DIPacketChannels;
import org.lwjgl.glfw.GLFW;

import java.util.Set;
import java.util.function.Consumer;

public final class DIKeybinds
{
	private static final Set<DIKeybind> KEYBINDS = Sets.newHashSet();
	
	public static final KeyMapping OPEN_DRACO_MENU = keyboard("open_draco_menu", "Open Draco Menu", GLFW.GLFW_KEY_G, true, (client) ->
			ClientPlayNetworking.send(DIPacketChannels.ClientToServer.REQUEST_DRACO_MENU, PacketByteBufs.empty()));
	
	public static Set<DIKeybind> all()
	{
		return Set.copyOf(KEYBINDS);
	}
	
	private static KeyMapping register(String id, String englishName, InputConstants.Type keyType, int keyCode, boolean singlePress, Consumer<Minecraft> pressAction)
	{
		KeyMapping keyMapping = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key.draconic_industrialization.%s".formatted(id),
				keyType,
				GLFW.GLFW_KEY_G,
				"category.draconic_industrialization.draconic_industrialization"
		));
		ClientTickEvents.END_CLIENT_TICK.register((client) ->
		{
			while(keyMapping.isDown())
			{
				pressAction.accept(client);
				if(singlePress)
				{
					break;
				}
			}
		});
		KEYBINDS.add(new DIKeybind(id, englishName, keyMapping));
		return keyMapping;
	}
	
	private static KeyMapping keyboard(String id, String englishName, int keyCode, boolean singlePress, Consumer<Minecraft> pressAction)
	{
		return register(id, englishName, InputConstants.Type.KEYSYM, keyCode, singlePress, pressAction);
	}
	
	private static KeyMapping mouse(String id, String englishName, int keyCode, boolean singlePress, Consumer<Minecraft> pressAction)
	{
		return register(id, englishName, InputConstants.Type.MOUSE, keyCode, singlePress, pressAction);
	}
	
	public static void initClient()
	{
		// Load the class
	}
}
