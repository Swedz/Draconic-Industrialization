package net.swedz.draconic_industrialization.mixin.client;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.swedz.draconic_industrialization.api.dummy.level.DummyClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Mixin(TitleScreen.class)
public abstract class DragonFliesThroughMainMenuMixin extends Screen
{
	protected DragonFliesThroughMainMenuMixin(Component component)
	{
		super(component);
	}
	
	private static final long ROAR_SHAKE_AMOUNT = 4;
	private static final long ROAR_TIME         = 5 * 20;
	private static final long ROAR_DURATION     = 3 * 20;
	private static final long ROAR_END_TIME     = ROAR_TIME + ROAR_DURATION;
	
	@Unique
	private final Random random = new Random();
	
	@Unique
	private boolean shouldRoar;
	
	@Shadow
	private String splash;
	
	@Shadow public abstract boolean shouldCloseOnEsc();
	
	@Unique
	private DummyClientLevel dummyLevel;
	
	@Unique
	private EnderDragon dragon;
	
	@Unique
	private long ticks;
	
	@Inject(
			method = "init",
			at = @At("RETURN")
	)
	private void postInit(CallbackInfo callback)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		shouldRoar = now.get(Calendar.DAY_OF_MONTH) == 1 && now.get(Calendar.MONTH) == Calendar.APRIL;
		
		if(shouldRoar)
		{
			dummyLevel = DummyClientLevel.getInstance();
			dragon = new EnderDragon(EntityType.ENDER_DRAGON, dummyLevel);
			splash = "ROAAAARRRR!!!!!!";
		}
	}
	
	@Inject(
			method = "tick",
			at = @At("RETURN")
	)
	private void tick(CallbackInfo callback)
	{
		if(shouldRoar)
		{
			ticks++;
			
			if(ticks == ROAR_TIME)
			{
				Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.ENDER_DRAGON_GROWL, 1));
			}
		}
	}
	
	@Unique
	private boolean isRoaring()
	{
		return ticks > ROAR_TIME && ticks < ROAR_END_TIME;
	}
	
	@Unique
	private double randomShake()
	{
		return random.nextDouble() * (ROAR_SHAKE_AMOUNT + ROAR_SHAKE_AMOUNT) - ROAR_SHAKE_AMOUNT;
	}
	
	@Inject(
			method = "render",
			at = @At("HEAD")
	)
	private void preRender(PoseStack matrices, int mouseX, int mouseY, float partialTick, CallbackInfo callback)
	{
		if(shouldRoar && this.isRoaring())
		{
			matrices.translate(this.randomShake(), this.randomShake(), 0);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Unique
	private void renderDragon(int x, int y, int scale)
	{
		final float xRotation = -20;
		final float yRotation = 0; // this seems to not effect the dragon model rotation... why???
		
		PoseStack poseStack = RenderSystem.getModelViewStack();
		poseStack.pushPose();
		poseStack.translate(x, y, 1050);
		poseStack.scale(1f, 1f, -1f);
		RenderSystem.applyModelViewMatrix();
		PoseStack poseStack2 = new PoseStack();
		poseStack2.translate(0, 0, 1000);
		poseStack2.scale((float) scale, (float) scale, (float) scale);
		Quaternion quaternion = Vector3f.ZP.rotationDegrees(180);
		Quaternion quaternion2 = Vector3f.XP.rotationDegrees(xRotation);
		quaternion.mul(quaternion2);
		poseStack2.mulPose(quaternion);
		float originalYBodyRot = dragon.yBodyRot;
		float originalYRot = dragon.getYRot();
		float originalXRot = dragon.getXRot();
		float originalYHeadRot0 = dragon.yHeadRotO;
		float originalYHeadRot = dragon.yHeadRot;
		dragon.yBodyRot = yRotation;
		dragon.setYRot(yRotation);
		dragon.setXRot(-xRotation);
		dragon.yHeadRot = dragon.getYRot();
		dragon.yHeadRotO = dragon.getYRot();
		Lighting.setupForEntityInInventory();
		EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		quaternion2.conj();
		entityRenderDispatcher.overrideCameraOrientation(quaternion2);
		entityRenderDispatcher.setRenderShadow(false);
		MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(dragon, 0, 0, 0, 0, 1, poseStack2, bufferSource, 15728880));
		bufferSource.endBatch();
		entityRenderDispatcher.setRenderShadow(true);
		dragon.yBodyRot = originalYBodyRot;
		dragon.setYRot(originalYRot);
		dragon.setXRot(originalXRot);
		dragon.yHeadRotO = originalYHeadRot0;
		dragon.yHeadRot = originalYHeadRot;
		poseStack.popPose();
		RenderSystem.applyModelViewMatrix();
		Lighting.setupFor3DItems();
	}
	
	@Inject(
			method = "render",
			at = @At("RETURN")
	)
	private void postRender(PoseStack matrices, int mouseX, int mouseY, float partialTick, CallbackInfo callback)
	{
		if(shouldRoar && this.isRoaring())
		{
			float speed = 8;
			float ticksOfRoaring = (ticks + partialTick) - ROAR_TIME;
			float range = height * speed;
			int y = (int) (((ticksOfRoaring / ROAR_DURATION) * range) - range / 2);
			this.renderDragon((int) (this.width / 2 - dragon.getEyePosition().x), y, 60);
		}
	}
}
