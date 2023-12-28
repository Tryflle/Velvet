package dev.velvet

import dev.velvet.module.api.ModuleManager
import net.weavemc.loader.api.ModInitializer
import net.weavemc.loader.api.event.EventBus

class Run: ModInitializer {
    override fun preInit() {
        EventBus.subscribe(ModuleManager)
    }
}