package dev.velvet.module.impl.movement

import dev.velvet.module.api.*
import dev.velvet.util.game.PlayerUtils
import net.minecraft.client.settings.KeyBinding
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent

private var type: EnumSetting = EnumSetting("Type", "Type", emptyArray(), "Regular", listOf("Regular", "Strafe", "Legit"))
private var speed: SliderSetting = SliderSetting("Speed", "Speed", emptyArray(), 0.1, 10.0, 0.1, 1.0)

class Speed : Module("Speed", "Increases your speed", Category.MOVEMENT, 0, arrayOf(type, speed)) {
    //TODO: Finish all of this.

    private fun isMoveKeyDown(): Boolean {
        return mc.gameSettings.keyBindForward.isKeyDown || mc.gameSettings.keyBindBack.isKeyDown || mc.gameSettings.keyBindLeft.isKeyDown || mc.gameSettings.keyBindRight.isKeyDown
    }

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (!PlayerUtils.isInGame() || !isMoveKeyDown() || mc.thePlayer.inWater) return
        if (type.value == "Legit") {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jumpTicks = 0
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.keyCode, true)
            }
        }
        if (type.value == "Regular") {
            if (mc.thePlayer.onGround) {
                mc.thePlayer.jumpTicks = 0
                mc.thePlayer.motionX *= speed.value/20
                mc.thePlayer.motionZ *= speed.value/20
                mc.thePlayer.jump()
            }
        }
        if (type.value == "Strafe") {
            if (mc.thePlayer.onGround) {
                //TODO
            }
        }
    }
}