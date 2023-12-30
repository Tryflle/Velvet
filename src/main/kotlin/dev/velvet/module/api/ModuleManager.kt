package dev.velvet.module.api

import dev.velvet.module.impl.combat.NoHitDelay
import dev.velvet.module.impl.movement.NoJumpDelay
import dev.velvet.module.impl.player.NoItemRelease
import dev.velvet.module.impl.render.Chams
import dev.velvet.module.impl.render.ClickGUI
import dev.velvet.util.game.PlayerUtils
import net.minecraft.client.Minecraft
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent

object ModuleManager {
    private val modules = ArrayList<Module>(
        listOf(
            NoHitDelay(),
            Chams(),
            NoItemRelease(),
            NoJumpDelay(),
            ClickGUI()
        )
    )

    fun getModules(): ArrayList<Module> = modules

    fun isModuleEnabled(module: Module): Boolean = module.enabled

    @SubscribeEvent
    fun onKey(e: KeyboardEvent) {
        if (e.keyState && PlayerUtils.inGame() && Minecraft.getMinecraft().currentScreen == null)
            modules.forEach { if (it.bind == e.keyCode) it.toggle() }
    }
}