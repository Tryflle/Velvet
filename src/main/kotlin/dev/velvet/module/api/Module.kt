package dev.velvet.module.api

import net.minecraft.client.Minecraft
import net.weavemc.loader.api.event.EventBus

open class Module(
    val name: String,
    val description: String,
    val category: Category,
    var bind: Int,
    val settings: Array<Setting>
) {
    var enabled = false
    val mc = Minecraft.getMinecraft()

    open fun onEnable() {}
    open fun onDisable() {}

    fun toggle() {
        enabled = if (enabled) {
            EventBus.unsubscribe(this)
            onDisable()
            false
        } else {
            EventBus.subscribe(this)
            onEnable()
            true
        }
    }
}