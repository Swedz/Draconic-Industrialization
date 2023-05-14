package net.swedz.draconic_industrialization.blocks;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record DIBlock(Block block, Item item, DIBlockProperties properties)
{
	public ResourceLocation location()
	{
		return Registry.BLOCK.getKey(block);
	}
	
	public String modId()
	{
		return this.location().getNamespace();
	}
	
	public String id(boolean modid)
	{
		return modid ? this.location().toString() : this.location().getPath();
	}
}
