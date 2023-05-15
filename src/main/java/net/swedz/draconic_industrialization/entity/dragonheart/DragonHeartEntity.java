package net.swedz.draconic_industrialization.entity.dragonheart;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.items.DIItems;

public final class DragonHeartEntity extends Entity
{
	private final float hoverStart;
	
	public int age;
	
	private Player followingPlayer;
	
	public DragonHeartEntity(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
		this.hoverStart = (float) (Math.random() * Math.PI * 2);
	}
	
	@Override
	public void playerTouch(Player player)
	{
		if(!this.level.isClientSide)
		{
			final Item item = DIItems.DRAGON_HEART;
			if(player.getInventory().add(new ItemStack(item)))
			{
				player.take(this, 1);
				this.discard();
				player.awardStat(Stats.ITEM_PICKED_UP.get(item), 1);
			}
		}
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		age++;
		
		if(tickCount % 20 == 1)
		{
			this.scanForEntities();
		}
		if(followingPlayer != null && (followingPlayer.isSpectator() || followingPlayer.isDeadOrDying()))
		{
			followingPlayer = null;
		}
		if(followingPlayer != null)
		{
			Vec3 motion = new Vec3(
					followingPlayer.getX() - this.getX(),
					followingPlayer.getY() + (double) followingPlayer.getEyeHeight() / 2 - this.getY(),
					followingPlayer.getZ() - this.getZ()
			);
			double distance = motion.lengthSqr();
			if(distance < 64)
			{
				this.setDeltaMovement(this.getDeltaMovement().add(motion.normalize().scale(0.1)));
			}
		}
		else
		{
			double height = level.getBlockFloorHeight(this.blockPosition());
			DraconicIndustrialization.LOGGER.info("height: " + height);
			if(height > 2)
			{
				Vec3 motion = new Vec3(0, -0.1, 0);
				this.setDeltaMovement(motion);
			}
		}
		
		this.move(MoverType.SELF, this.getDeltaMovement());
		
		if(level.isClientSide)
		{
			double x = this.getX(), y = this.getY() + 0.5, z = this.getZ();
			Vec3 motion = this.getDeltaMovement();
			Vec3 norm = motion.normalize().multiply(0.5, 0.5, 0.5);
			float extra = (float) motion.length();
			float cycles = 2;
			for(int i = 0; i < cycles; i++)
			{
				double lerpX = Mth.lerp(i / cycles, x - motion.x, x);
				double lerpY = Mth.lerp(i / cycles, y - motion.y, y);
				double lerpZ = Mth.lerp(i / cycles, z - motion.z, z);
				//CommonParticleEffects.spawnSpiritParticles(level, lerpX, lerpY, lerpZ, 0.55f + extra, norm, color, endColor);
			}
		}
	}
	
	private void scanForEntities()
	{
		if(this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64)
		{
			this.followingPlayer = this.level.getNearestPlayer(this, 10);
		}
	}
	
	public float rotation(float partialTick)
	{
		return ((float) age + partialTick) / 20f + hoverStart;
	}
	
	@Override
	protected void defineSynchedData()
	{
	
	}
	
	@Override
	protected void readAdditionalSaveData(CompoundTag tag)
	{
		age = tag.getInt("age");
	}
	
	@Override
	protected void addAdditionalSaveData(CompoundTag tag)
	{
		tag.putInt("age", age);
	}
	
	@Override
	public Packet<?> getAddEntityPacket()
	{
		return new ClientboundAddEntityPacket(this);
	}
}
