package dev.velvet.module.impl.movement

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent
import dev.velvet.util.game.isinGame

class NoJumpDelay: Module("NoJumpDelay", "Removes delay between jumps", Category.MOVEMENT, 0, emptyArray()) {

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (mc.isInGame() && mc.thePlayer.onGround) mc.thePlayer.jumpTicks = 0
    }
}
