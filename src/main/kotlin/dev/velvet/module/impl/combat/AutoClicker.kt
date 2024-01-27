package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.util.math.TimerUtils
import net.minecraft.client.settings.KeyBinding
import net.weavemc.loader.api.event.RenderHandEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.input.Mouse
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random


private var maxCPS: SliderSetting = SliderSetting("MaxCPS", "Maximum CPS", emptyArray(), 1.0, 20.0, 1.0, 12.0)
private var minCPS: SliderSetting = SliderSetting("MinCPS", "Minimum CPS", emptyArray(), 1.0, 20.0, 1.0, 9.0)
private var clicked: Boolean = false

private val t: TimerUtils = TimerUtils()

private var maxCps: Int = 0
private var minCps: Int = 0
private var cps: Int = 0


class AutoClicker : Module("AutoClicker", "Automatically clicks", Category.COMBAT, 0, arrayOf(maxCPS, minCPS)) {
    //TODO: Finish this: Add better random, add break blocks and some conditionals.

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
        val upRange: Int = randomInt(1, 4)
        val downRange: Int = randomInt(2, 5)
        minCps = minCPS.value.toInt() + downRange
        maxCps = maxCPS.value.toInt() + upRange
        cps = if (minCps == maxCps) minCps else randomInt(minCps, maxCps)
    }

    private fun clickMouse() {
        clicked = !clicked
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindAttack.keyCode, clicked)
        KeyBinding.onTick(mc.gameSettings.keyBindAttack.keyCode)
    }

    private fun randomInt(min: Int, max: Int): Int {
        return ThreadLocalRandom.current().nextInt(min, max)
    }
}