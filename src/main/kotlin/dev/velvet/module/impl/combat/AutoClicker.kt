package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.util.math.TimerUtils
import net.minecraft.client.settings.KeyBinding
import net.weavemc.loader.api.event.RenderHandEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.input.Mouse
import java.util.Random


private var maxCPS: SliderSetting = SliderSetting("MaxCPS", "Maximum CPS", emptyArray(), 1.0, 20.0, 1.0, 12.0)
private var minCPS: SliderSetting = SliderSetting("MinCPS", "Minimum CPS", emptyArray(), 1.0, 20.0, 1.0, 9.0)
private var clicked: Boolean = false

private val t: TimerUtils = TimerUtils()

private var maxCps: Double = 0.0
private var minCps: Double = 0.0
private var cps: Double = 0.0


class AutoClicker : Module("AutoClicker", "Automatically clicks", Category.COMBAT, 0, arrayOf(maxCPS, minCPS)) {
    //TODO: Add break blocks and some conditionals.

    @SubscribeEvent
    fun onRenderHand(e: RenderHandEvent) {
        if (Mouse.isButtonDown(0) && mc.currentScreen == null) {
            if (maxCPS.value < minCPS.value) {
                maxCPS.value = minCPS.value
                return
            }
            setCPS()
            if (t.hasTimePassed(1000 / cps.toLong())) {
                t.resetTimer()
                clickMouse()
            }

        }
    }

    private fun setCPS() {
        val dropChance: Double = randomDouble(1.0, 70.0)
        val upRange: Double = randomDouble(1.0, 4.0)
        val downRange: Double = randomDouble(2.0, 5.0)
        minCps = minCPS.value.toInt() - downRange
        maxCps = maxCPS.value.toInt() + upRange
        cps = if (minCps == maxCps) minCps else randomDouble(minCps, maxCps)
        if (dropChance <= 2) cps -= randomDouble(2.0, 5.0)
    }

    private fun clickMouse() {
        clicked = !clicked
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, clicked)
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.keyCode)
    }

    private fun randomDouble(min: Double, max: Double): Double {
        val random = Random()
        val mean = min + (max - min) / 2.0
        val stdDev = (max - min) / 6.0
        val result = mean + stdDev * random.nextGaussian()
        return if (result in min..max) result else randomDouble(min, max)
    }
}