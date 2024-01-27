package dev.velvet.util.packet

import net.minecraft.network.Packet

data class TimedPacket(val packet: Packet<*>, val time: Long)