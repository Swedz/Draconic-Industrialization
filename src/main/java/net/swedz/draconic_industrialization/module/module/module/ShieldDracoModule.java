package net.swedz.draconic_industrialization.module.module.module;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.swedz.draconic_industrialization.api.nbt.NBTTagWrapper;
import net.swedz.draconic_industrialization.items.item.draconicarmor.DraconicArmorItem;
import net.swedz.draconic_industrialization.listener.LivingEntityHurtListener;
import net.swedz.draconic_industrialization.mixinducks.LivingEntityDracoShieldImmunityMixinDuck;
import net.swedz.draconic_industrialization.module.DracoItem;
import net.swedz.draconic_industrialization.module.DracoItemConfiguration;
import net.swedz.draconic_industrialization.module.DracoItemEnergy;
import net.swedz.draconic_industrialization.module.DracoModuleTick;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

import java.awt.Color;
import java.util.List;

public final class ShieldDracoModule extends DracoModule
{
	private final long             maxShields;
	private final RegenerationRate regenerationRate;
	
	private long shields, cooldown;
	
	public ShieldDracoModule(DracoModuleReference reference, long maxShields, RegenerationRate regenerationRate)
	{
		super(reference);
		this.maxShields = maxShields;
		this.regenerationRate = regenerationRate;
	}
	
	public ShieldDracoModule(DracoModuleReference reference, long maxShields, long tickInterval, long amountToGenerate, long euCost, long cooldownTicks)
	{
		this(reference, maxShields, new RegenerationRate(tickInterval, amountToGenerate, euCost, cooldownTicks));
	}
	
	@Override
	public boolean applies(DracoItem item)
	{
		return super.applies(item) &&
				item instanceof DraconicArmorItem;
	}
	
	@Override
	public boolean hasStuffToConfigure()
	{
		return false;
	}
	
	@Override
	public void tick(DracoModuleTick tick, ItemStack stack, DracoItemConfiguration itemConfiguration, DracoItemEnergy itemEnergy, Level level, Player player)
	{
		if(cooldown > 0)
		{
			cooldown--;
			return;
		}
		
		if(shields < maxShields && tick.tick % regenerationRate.tickInterval() == 0)
		{
			long newShields = Math.min(maxShields, shields + regenerationRate.amountToGenerate());
			long amountAdded = newShields - shields;
			long euCost = (long) (((double) amountAdded / regenerationRate.amountToGenerate()) * regenerationRate.euCost());
			if(itemEnergy.energy() >= euCost)
			{
				tick.euCost += euCost;
				shields = newShields;
				tick.needsItemConfigurationUpdate();
			}
		}
	}
	
	@Override
	public void read(NBTTagWrapper tag, DracoItem item)
	{
		super.read(tag, item);
		shields = tag.getLongOrDefault("Shields", 0);
	}
	
	@Override
	public void write(NBTTagWrapper tag, DracoItem item)
	{
		super.write(tag, item);
		tag.setLong("Shields", shields);
	}
	
	public long maxShields()
	{
		return maxShields;
	}
	
	public long shields()
	{
		return shields;
	}
	
	public void resetCooldown()
	{
		cooldown = regenerationRate.cooldownTicks();
	}
	
	public record RegenerationRate(long tickInterval, long amountToGenerate, long euCost, long cooldownTicks)
	{
	}
	
	private static long extractShields(long shields, List<ShieldDracoModule> shieldModules, long amount)
	{
		long extracted = 0;
		for(ShieldDracoModule shieldModule : shieldModules)
		{
			if(shields <= 0 || amount <= 0)
			{
				break;
			}
			if(shieldModule.shields > 0)
			{
				long originalShields = shieldModule.shields;
				shieldModule.shields = Math.max(0, shieldModule.shields - amount);
				long amountConsumed = originalShields - shieldModule.shields;
				shields -= amountConsumed;
				amount -= amountConsumed;
				extracted += amountConsumed;
			}
		}
		return extracted;
	}
	
	public static void initializeListener()
	{
		LivingEntityHurtListener.BEFORE_HURT.register((entity, source, damage) ->
		{
			ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.CHEST);
			if(chestplate.getItem() instanceof DraconicArmorItem dracoChestplate)
			{
				DracoItemConfiguration itemConfiguration = dracoChestplate.dracoConfiguration(chestplate);
				List<ShieldDracoModule> shieldModules = itemConfiguration.shieldModules();
				if(shieldModules.size() > 0)
				{
					shieldModules.forEach(ShieldDracoModule::resetCooldown);
					
					LivingEntityDracoShieldImmunityMixinDuck entityMixin = (LivingEntityDracoShieldImmunityMixinDuck) entity;
					if(entityMixin.draconicShieldImmunity() > 0)
					{
						return 0;
					}
					
					long shields = itemConfiguration.shields();
					if(shields >= damage || shields > 0)
					{
						damage -= extractShields(shields, shieldModules, (long) Math.ceil(damage));
						itemConfiguration.save();
					}
					
					entityMixin.draconicShieldImmunity(5);
				}
			}
			return damage;
		});
	}
	
	public static final class HudRenderer implements HudRenderCallback
	{
		@Override
		public void onHudRender(PoseStack matrices, float partialTick)
		{
			final Player player = Minecraft.getInstance().player;
			final Font font = Minecraft.getInstance().font;
			
			ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
			if(chestplate.getItem() instanceof DraconicArmorItem dracoChestplate)
			{
				DracoItemConfiguration itemConfiguration = dracoChestplate.dracoConfiguration(chestplate);
				List<ShieldDracoModule> shieldModules = itemConfiguration.shieldModules();
				if(shieldModules.size() > 0)
				{
					long shields = itemConfiguration.shields();
					long maxShields = itemConfiguration.maxShields();
					font.drawShadow(matrices, "Shields: %d / %d".formatted(shields, maxShields), 0, 0, Color.WHITE.getRGB());
				}
			}
		}
	}
}
