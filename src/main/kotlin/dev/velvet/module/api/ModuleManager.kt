package dev.velvet.module.api

import dev.velvet.module.impl.combat.AutoClicker
import dev.velvet.module.impl.combat.Backtrack
import dev.velvet.module.impl.combat.NoHitDelay
import dev.velvet.module.impl.combat.Velocity
import dev.velvet.module.impl.movement.NoJumpDelay
import dev.velvet.module.impl.movement.Sprint
import dev.velvet.module.impl.player.Blink
import dev.velvet.module.impl.player.FastPlace
import dev.velvet.module.impl.player.NoItemRelease
import dev.velvet.module.impl.render.Chams
import dev.velvet.module.impl.render.ClickGUI
import dev.velvet.module.impl.render.Overlay
import net.minecraft.client.Minecraft
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent

object ModuleManager {

    private val modules: HashMap<String, Module> = hashMapOf(
        NoHitDelay().name to NoHitDelay(),
        AutoClicker().name to AutoClicker(),
        NoItemRelease().name to NoItemRelease(),
        NoJumpDelay().name to NoJumpDelay(),
        ClickGUI().name to ClickGUI(),
        Overlay().name to Overlay(),
        Sprint().name to Sprint(),
        Backtrack().name to Backtrack(),
        Velocity().name to Velocity(),
        FastPlace().name to FastPlace(),
        Blink().name to Blink()
    )

    fun getModules(): HashMap<String, Module> = modules

    fun isModuleEnabled(module: Module): Boolean = module.enabled

    @SubscribeEvent
    fun onKey(e: KeyboardEvent) {
        if (e.keyState && Minecraft.getMinecraft().currentScreen == null)
            modules.values.find { it.bind == e.keyCode }?.toggle() ?: return
    }

    //TODO: Module ideas:
    // Render - ESP
    // Combat - Aura, AimAssist, RightClicker, Reach, ClickAssist, Critical
    // Player - BedBreaker, Timer, FakeLag, Blink, LagRange, FastPlace, NoRotate, Manager, Stealer, SafeWalk
    // Movement - Speed, Flight, KeepSprint, NoSlow, InventoryMove
    // Misc - AutoRespawn
}