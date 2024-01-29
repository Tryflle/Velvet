package dev.velvet.module.impl.player

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.Setting
import dev.velvet.module.api.TickSetting
import dev.velvet.util.game.PlayerUtils
import net.minecraft.item.ItemBow
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.weavemc.loader.api.event.PacketEvent
import net.weavemc.loader.api.event.SubscribeEvent

private var blacklistBow = TickSetting("Blacklist Bow", "Prevents you from dropping bows", emptyArray<Setting>(), true)

class NoItemRelease: Module("NoItemRelease", "Prevents you from dropping items", Category.PLAYER, 0, arrayOf(blacklistBow)) {

    @SubscribeEvent
    fun onPacket(e: PacketEvent.Send) {
        if (PlayerUtils.isInGame()) {
            if (mc?.thePlayer?.heldItem?.item is ItemBow? && blacklistBow.value) return
            if (e.packet is C07PacketPlayerDigging && (e.packet as C07PacketPlayerDigging).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM)
                e.cancelled = true
        }
    }
}