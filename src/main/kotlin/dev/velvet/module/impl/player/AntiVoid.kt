package dev.velvet.module.impl.player

import dev.velvet.module.api.Category
import dev.velvet.module.api.EnumSetting
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.util.game.PlayerUtils
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent

private var type: EnumSetting = EnumSetting("Type", "Type", emptyArray(), "NCP", listOf("NCP", "Karhu"))
private var distance: SliderSetting = SliderSetting("Distance", "Distance", emptyArray(), 1.0, 60.0, 1.0, 7.0)

class AntiVoid : Module("AntiVoid", "Prevents you from falling into the void", Category.PLAYER, 0, arrayOf(type, distance)) {

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (PlayerUtils.isInGame() && mc.thePlayer.fallDistance > distance.value.toFloat()) {
            if (type.value == "NCP") {
                mc.thePlayer.motionY = 0.0
                mc.thePlayer.fallDistance = 0.0f
            } else if (type.value == "Karhu") {
                mc.thePlayer.motionY = -0.09800000190735147
            }
        }
    }
}