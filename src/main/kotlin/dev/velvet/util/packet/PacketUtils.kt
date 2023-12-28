package dev.velvet.util.packet

import net.minecraft.client.Minecraft
import net.minecraft.network.INetHandler
import net.minecraft.network.Packet
import net.minecraft.network.ThreadQuickExitException
import java.lang.RuntimeException

private val net = Minecraft.getMinecraft().netHandler.netManager

@Suppress("unused")
object PacketUtils {
    fun send(packet: Packet<INetHandler>) {
        Minecraft.getMinecraft().netHandler.addToSendQueue(packet)
    }
    fun handle(packet: Packet<INetHandler>, outgoing: Boolean) {
        if (outgoing) {
            net.sendPacket(packet)
        } else {
            if (net.channel.isOpen) {
                try {
                    packet.processPacket(net.packetListener)
                } catch (ex: ThreadQuickExitException) {
                    throw RuntimeException(ex)
                }
            }
        }
    }
}