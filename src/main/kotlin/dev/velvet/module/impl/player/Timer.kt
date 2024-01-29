package dev.velvet.module.impl.player

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent

var speed: SliderSetting = SliderSetting("Amount", "Speed", emptyArray(), 0.5, 5.0, 0.1, 1.0)

class Timer : Module("Timer", "Changes the game speed", Category.PLAYER, 0, arrayOf(speed)) {

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        mc.timer.timerSpeed = speed.value.toFloat()
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1.0f
    }
}