package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.util.math.MathUtils
import dev.velvet.util.math.TimerUtils
import net.minecraft.client.settings.KeyBinding
import net.weavemc.loader.api.event.RenderHandEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.input.Mouse


private val maxCPS: SliderSetting = SliderSetting("MaxCPS", "Maximum CPS", emptyArray(), 1, 20, 1, 12)
private val minCPS: SliderSetting = SliderSetting("MinCPS", "Minimum CPS", emptyArray(), 1, 20, 1, 9)
private var clicked: Boolean = false
private val t: TimerUtils = TimerUtils()

class AutoClicker : Module("AutoClicker", "Automatically clicks", Category.COMBAT, 0, arrayOf(maxCPS, minCPS)) {
    //TODO: Finish this: Add better random, add break blocks and some conditionals.

    @SubscribeEvent
    fun onRenderHand(e: RenderHandEvent) {
        if (Mouse.isButtonDown(0) && mc.currentScreen == null) {
            if (maxCPS.value < minCPS.value) {
                maxCPS.value = minCPS.value
                return
            }
            val cps = (MathUtils.randDouble(minCPS.value.toDouble(), maxCPS.value.toDouble()) + MathUtils.randDouble(-3.0, 3.0)).toLong()
            if (t.hasTimePassed(1000 / cps)) {
                t.resetTimer()
                clickMouse()
            }

        }
    }

    private fun clickMouse() {
        clicked = !clicked
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, clicked)
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.keyCode)
    }
}