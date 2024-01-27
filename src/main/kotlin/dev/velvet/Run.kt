package dev.velvet

import dev.velvet.command.api.CommandManager
import dev.velvet.module.api.ModuleManager
import net.weavemc.loader.api.ModInitializer
import net.weavemc.loader.api.event.EventBus
import net.weavemc.loader.api.event.StartGameEvent

class Run: ModInitializer {
    override fun preInit() {
        EventBus.subscribe(StartGameEvent::class.java) {
            init()
        }
    }
    private fun init() {
        EventBus.subscribe(ModuleManager)
        EventBus.subscribe(CommandManager)
    }
}