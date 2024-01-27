package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.module.api.TickSetting
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.weavemc.loader.api.event.PacketEvent
import net.weavemc.loader.api.event.SubscribeEvent

var horizontal: SliderSetting = SliderSetting("Horizontal", "Horizontal", emptyArray(), 0.0, 100.0, 1.0, 100.0)
var vertical: SliderSetting = SliderSetting("Vertical", "Vertical", emptyArray(), 0.0, 100.0, 1.0, 100.0)
var negativeH: TickSetting = TickSetting("Negative Horizontal", "Negative", emptyArray(), false)
var negativeV: TickSetting = TickSetting("Negative Vertical", "Negative", emptyArray(), false)
class Velocity : Module("Velocity", "Take less kb!", Category.COMBAT, 0, arrayOf(horizontal, vertical, negativeH, negativeV)) {

    private fun getHorizontal(): Double {
        return if (negativeH.value) -horizontal.value else horizontal.value
    }

    private fun getVertical(): Double {
        return if (negativeV.value) -vertical.value else vertical.value
    }

    @SubscribeEvent
    fun onPacket(e: PacketEvent.Receive) {
        if (e.packet is S12PacketEntityVelocity) {
            val s12 = e.packet as S12PacketEntityVelocity
            if (s12.getEntityID() == mc.thePlayer.entityId) {
                val motionX = s12.motionX.toDouble() * (getHorizontal() / 100)
                val motionY = s12.motionY.toDouble() * (getVertical() / 100)
                val motionZ = s12.motionZ.toDouble() * (getHorizontal() / 100)

                e.cancelled = true
                mc.thePlayer.setVelocity(motionX / 8000, motionY / 8000.toDouble(), motionZ / 8000)
            }
        }
    }
}