package dev.velvet.module.impl.player

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.weavemc.loader.api.event.PacketEvent
import net.weavemc.loader.api.event.SubscribeEvent

class NoItemRelease: Module("NoItemRelease", "Prevents you from dropping items", Category.PLAYER, 0, emptyArray()) {

    @SubscribeEvent
    fun onPacket(e: PacketEvent.Send) {
        if (e.packet is C07PacketPlayerDigging && (e.packet as C07PacketPlayerDigging).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM)
            e.cancelled = true
    }
}