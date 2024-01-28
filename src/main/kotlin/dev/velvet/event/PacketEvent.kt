package dev.velvet.event

import net.weavemc.loader.api.event.CancellableEvent
//Slightly useless event atm, when I get everything packet related done I'll see if I'll have to keep it or not.

class PacketEvent(val packet: Any, val direction: PacketDirection): CancellableEvent() {
}

enum class PacketDirection {
    INCOMING,
    OUTGOING
}