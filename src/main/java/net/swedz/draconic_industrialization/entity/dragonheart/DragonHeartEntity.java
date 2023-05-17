package net.swedz.draconic_industrialization.entity.dragonheart;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.swedz.draconic_industrialization.items.DIItems;
import net.swedz.draconic_industrialization.particles.DIParticles;

public final class DragonHeartEntity extends Entity
{
	private static final int FOLLOW_DISTANCE     = 15;
	private static final int FOLLOW_DISTANCE_SQR = FOLLOW_DISTANCE * FOLLOW_DISTANCE;
	
	private final float hoverStart;
	
	public int age;
	
	private Player followingPlayer;
	
	public DragonHeartEntity(EntityType<?> entityType, Level level)
	{
		super(entityType, level);
		this.hoverStart = (float) (Math.random() * Math.PI * 2);
	}
	
	@Override
	protected void playStepSound(BlockPos pos, BlockState state)
	{
		// No, I don't think I will.
	}
	
	@Override
	public void playerTouch(Player player)
	{
		if(!level.isClientSide)
		{
			final Item item = DIItems.DRAGON_HEART;
			if(player.getInventory().add(new ItemStack(item)))
			{
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
		
		if(!this.followNearbyPlayer() && this.isFloating())
		{
			this.setDeltaMovement(new Vec3(0, -0.1, 0));
		}
		
		this.move(MoverType.SELF, this.getDeltaMovement());
		
		float friction = 0.98F;
		this.setDeltaMovement(this.getDeltaMovement().multiply(friction, friction, friction));
		
		if(level.isClientSide)
		{
			this.spawnParticles();
		}
	}
	
	private boolean followNearbyPlayer()
	{
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
			this.moveTowards(followingPlayer.position().add(0, followingPlayer.getEyeHeight() / 2, 0));
			return true;
		}
		return false;
	}
	
	private void moveTowards(Vec3 pos)
	{
		Vec3 motion = this.position().vectorTo(pos);
		double distance = motion.lengthSqr();
		if(distance < FOLLOW_DISTANCE_SQR)
		{
			this.setDeltaMovement(this.getDeltaMovement().add(motion.normalize().scale(0.005)));
		}
	}
	
	private void scanForEntities()
	{
		if(followingPlayer == null || followingPlayer.distanceToSqr(this) > FOLLOW_DISTANCE_SQR)
		{
			followingPlayer = level.getNearestPlayer(this, FOLLOW_DISTANCE);
		}
	}
	
	private boolean isFloating()
	{
		final BlockPos pos = this.blockPosition();
		return level.getBlockState(pos).isAir() &&
				level.getBlockState(pos.below()).isAir() &&
				level.getBlockState(pos.below(2)).isAir();
	}
	
	private void spawnParticles()
	{
		double x = this.getX(), y = this.getY() + 0.75, z = this.getZ();
		float cycles = 2;
		double offset = 0.4;
		for(int i = 0; i < cycles; i++)
		{
			double ox = x + random.nextDouble() * (offset + offset) - offset;
			double oy = y + random.nextDouble() * (offset + offset) - offset;
			double oz = z + random.nextDouble() * (offset + offset) - offset;
			double oxs = random.nextDouble() * (0.05 + 0.05) - 0.05;
			double ozs = random.nextDouble() * (0.05 + 0.05) - 0.05;
			level.addParticle(DIParticles.HEART_SPARKLE, ox, oy, oz, oxs, 0.1, ozs);
		}
	}
	
	float rotation(float partialTick)
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
