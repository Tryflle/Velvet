package dev.velvet.module.impl.movement

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.util.game.PlayerUtils
import net.minecraft.client.settings.KeyBinding
import net.weavemc.loader.api.event.SubscribeEvent
import net.weavemc.loader.api.event.TickEvent

class Sprint : Module("Sprint", "Automatically sprints", Category.MOVEMENT, 0, emptyArray()) {

    @SubscribeEvent
    fun onTick(e: TickEvent) {
        if (PlayerUtils.isInGame()) KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.keyCode, true)
    }
}