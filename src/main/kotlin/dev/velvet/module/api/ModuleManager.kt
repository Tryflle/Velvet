package dev.velvet.module.api

import dev.velvet.module.impl.combat.NoHitDelay
import dev.velvet.module.impl.render.Chams
import net.weavemc.loader.api.event.KeyboardEvent
import net.weavemc.loader.api.event.SubscribeEvent

object ModuleManager {
    private val modules = ArrayList<Module>(
        listOf(
            NoHitDelay(),
            Chams()
        )
    )

    fun getModules(): ArrayList<Module> = modules

    fun isModuleEnabled(module: Module): Boolean = module.enabled

    @SubscribeEvent
    fun onKey(e: KeyboardEvent) {
        if (e.keyState)
            modules.forEach { if (it.bind == e.keyCode) it.toggle() }
    }
}