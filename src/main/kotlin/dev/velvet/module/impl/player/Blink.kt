package dev.velvet.module.impl.player

import dev.velvet.event.WindowResizeEvent
import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.TickSetting
import dev.velvet.util.game.PlayerUtils
import dev.velvet.util.packet.PacketUtils
import dev.velvet.util.packet.TimedPacket
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.weavemc.loader.api.event.*
import java.util.concurrent.ConcurrentLinkedQueue


private var outBound: TickSetting = TickSetting("OutBound", "OutBound", emptyArray(), true)
private var inBound: TickSetting = TickSetting("InBound", "InBound", emptyArray(), false)
private var indicator: TickSetting = TickSetting("Indicator", "Indicator", emptyArray(), true)

//I've had so many issues with types, so I've just resorted to the one thing that works, TimedPacket, for some reason.
private var outgoingPackets = ConcurrentLinkedQueue<TimedPacket>()
private var incomingPackets = ConcurrentLinkedQueue<TimedPacket>()

private var sh: Float = 0F

class Blink : Module("Blink", "Blinks", Category.PLAYER, 0, arrayOf(inBound, outBound, indicator)) {
    //TODO: Fix kick, fix indicator being in the wrong position, fix TimedPacket stupidity

    @SubscribeEvent
    fun onPacketReceive(e: PacketEvent.Receive) {
        if (PlayerUtils.isInGame()) {
            if (inBound.value) e.cancelled = true
            incomingPackets.add(TimedPacket(e.packet, System.currentTimeMillis()))
        }
    }

    @SubscribeEvent
    fun onPacketSend(e: PacketEvent.Send) {
        if (PlayerUtils.isInGame()) {
            if (outBound.value && e.packet.javaClass.canonicalName.startsWith("net.minecraft.network.play.client")) {
                e.cancelled = true
                outgoingPackets.add(TimedPacket(e.packet, System.currentTimeMillis()))
            }
        }
    }


    @SubscribeEvent
    fun onRender(e: RenderGameOverlayEvent) {
        if (!PlayerUtils.isInGame()) this.toggle()
        if (indicator.value) {
            if (outgoingPackets.size > 0 || incomingPackets.size > 0) {
                val sw = mc.fontRendererObj.getStringWidth("Blinking")
                mc.fontRendererObj.drawStringWithShadow("Blinking", sh, sw.toFloat(), 0xf7a2f7)
            }
        }
    }

    @SubscribeEvent
    fun onWindowResize(e: WindowResizeEvent) {
        val sr = ScaledResolution(Minecraft.getMinecraft())
        sh = sr.scaledHeight.toFloat() / 2
    }

    override fun onEnable() {
        outgoingPackets.clear()
        incomingPackets.clear()
    }

    override fun onDisable() {
        if (!outgoingPackets.isEmpty() && outBound.value) {
            try {
                outgoingPackets.forEach { timedPacket ->
                    PacketUtils.send(timedPacket.packet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (!incomingPackets.isEmpty() && inBound.value) {
            try {
                incomingPackets.forEach { timedPacket ->
                    PacketUtils.handle(timedPacket.packet, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            outgoingPackets.clear()
            incomingPackets.clear()
        }

        fun onShutdown(e: ShutdownEvent) {
            this.toggle()
        }

        fun onGameStart(e: StartGameEvent) {
            this.toggle()
        }

        fun onWorldLoad(e: WorldEvent) {
            this.toggle()
        }

        fun onTick(e: TickEvent.Pre) {
            if (!PlayerUtils.isInGame()) this.toggle()
        }
    }
}