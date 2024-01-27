package dev.velvet.module.impl.player

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.module.api.TickSetting
import dev.velvet.util.game.PlayerUtils
import net.minecraft.item.ItemBlock
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent

private var delay: SliderSetting = SliderSetting("Delay", "Delay", emptyArray(), 0.0, 5.0, 1.0, 0.0)
private var onlyBlocks: TickSetting = TickSetting("Only Blocks", "Only Blocks", emptyArray(), true)
class FastPlace : Module("FastPlace", "Place blocks faster", Category.PLAYER, 0, arrayOf(delay, onlyBlocks)) {

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (PlayerUtils.isInGame()) {
           if (mc.thePlayer.heldItem.item is ItemBlock && onlyBlocks.value) mc.rightClickDelayTimer = delay.value.toInt()
           else if (!onlyBlocks.value) mc.rightClickDelayTimer = 0
        }
    }
}