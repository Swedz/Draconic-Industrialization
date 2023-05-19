package net.swedz.draconic_industrialization.api.dummy.level;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;

public class DummyClientPlayerEntity extends LocalPlayer
{
	protected final LocalPlayer parent;
	
	private final Map<EquipmentSlot, ItemStack> equipment = Maps.newHashMap();
	
	public DummyClientPlayerEntity(LocalPlayer fromPlayer)
	{
		super(Minecraft.getInstance(), Minecraft.getInstance().level, DummyClientPacketListener.getInstance(), null, null, false, false);
		this.parent = fromPlayer;
		this.setUUID(UUID.randomUUID());
		for(EquipmentSlot slot : EquipmentSlot.values())
		{
			equipment.put(slot, fromPlayer.getItemBySlot(slot));
		}
	}
	
	public void resetEquipmentSlot(EquipmentSlot slot)
	{
		equipment.put(slot, parent.getItemBySlot(slot));
	}
	
	public void setEquipment(EquipmentSlot slot, ItemStack itemStack)
	{
		equipment.put(slot, itemStack == null ? ItemStack.EMPTY : itemStack);
	}
	
	@Override
	public boolean isModelPartShown(PlayerModelPart part)
	{
		return parent.isModelPartShown(part);
	}
	
	@Override
	protected PlayerInfo getPlayerInfo()
	{
		return null;
	}
	
	@Override
	public boolean isSpectator()
	{
		return false;
	}
	
	@Override
	public boolean isCreative()
	{
		return true;
	}
	
	@Override
	public ItemStack getItemBySlot(EquipmentSlot slot)
	{
		return equipment.get(slot);
	}
	
	@Override
	public boolean isSkinLoaded()
	{
		return parent.isSkinLoaded();
	}
	
	@Override
	public ResourceLocation getSkinTextureLocation()
	{
		return parent.getSkinTextureLocation();
	}
	
	@Override
	public String getModelName()
	{
		return parent.getModelName();
	}
}
