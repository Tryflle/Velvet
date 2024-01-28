package dev.velvet.mixin;

import dev.velvet.event.WindowResizeEvent;
import dev.velvet.module.impl.combat.NoHitDelay;
import net.minecraft.client.Minecraft;
import net.weavemc.loader.api.event.EventBus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    public int leftClickCounter, displayWidth, displayHeight;

    @Inject(method = "clickMouse", at = @At("HEAD"))
    public void clickMouseAfter(final CallbackInfo ci) {
        if (NoHitDelay.Companion.getEnabled()) leftClickCounter = 0;
    }

    @Inject(method = "updateFramebufferSize", at = @At("TAIL"))
    private void updateFramebufferSize(CallbackInfo ci) {
        EventBus.callEvent(new WindowResizeEvent(displayWidth, displayHeight));
    }
}