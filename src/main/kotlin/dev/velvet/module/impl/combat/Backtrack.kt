package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting
import dev.velvet.module.api.TickSetting
import dev.velvet.util.game.PlayerUtils
import dev.velvet.util.packet.PacketUtils
import dev.velvet.util.packet.TimedPacket
import dev.velvet.util.render.RenderUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.network.play.server.S18PacketEntityTeleport
import net.minecraft.util.AxisAlignedBB
import net.weavemc.loader.api.event.*
import java.awt.Color
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


private var realz: Int = 0
private var realy: Int = 0
private var realx: Int = 0

private val maxRange: SliderSetting = SliderSetting("Range", "Range", emptyArray(),0.0, 10.0, 0.1, 4.0)
private val minRange: SliderSetting = SliderSetting("MinRange", "Range", emptyArray(), 0.0, 10.0, 0.1, 0.0)
private val delay: SliderSetting = SliderSetting("Delay", "Delay", emptyArray(), 0.0, 1000.0, 1.0, 100.0)
private var esp: TickSetting = TickSetting("Show real location", "ESP", emptyArray(), false)
private var noRange = TickSetting("No range", "No range", emptyArray(), false)

private var target: Optional<EntityPlayer> = Optional.empty()
private var incomingPackets = ConcurrentLinkedQueue<TimedPacket>()
class Backtrack : Module("Backtrack", "Hit players from previous locations", Category.COMBAT, 0, arrayOf(maxRange, minRange, delay, esp, noRange)) {
    //TODO: Fix skidded ESP lol

    @SubscribeEvent
    fun setTarget(e: TickEvent.Pre) {
        if (minRange.value > maxRange.value) {
            minRange.value = maxRange.value
            return
        }
        if (PlayerUtils.isInGame()) {
            target = if (mc.theWorld != null
            ) mc.theWorld.playerEntities.stream()
                .filter { player: EntityPlayer ->
                    player.getEntityId() != mc.thePlayer.getEntityId() &&
                            player.getDistanceToEntity(mc.thePlayer) <= maxRange.value &&
                            player.getDistanceToEntity(mc.thePlayer) >= minRange.value
                }
                .findFirst() else Optional.empty()
        }
    }

    private fun getTime(): Int {
        return if (target.isPresent || noRange.value) delay.value.toInt()
        else 0
    }

    @SubscribeEvent
    fun onPacket(e: PacketEvent.Receive) {
        if (PlayerUtils.isInGame()) {
            if (!e.packet::class.qualifiedName!!.startsWith("net.minecraft.network.play.server")) return
            if (e.packet is S18PacketEntityTeleport) {
                val packet = e.packet as S18PacketEntityTeleport
                if (target.isPresent && packet.entityId == target.get().getEntityId()) {
                    realx = packet.x
                    realy = packet.y
                    realz = packet.z
                }
            }
            incomingPackets.add(TimedPacket(e.packet, System.currentTimeMillis()))
            e.cancelled = true
        }
    }

    @SubscribeEvent
    fun packetHandler(e: RenderHandEvent) {
        if (!PlayerUtils.isInGame()) {
            this.toggle()
            return
        }
        incomingPackets.removeIf { timedPacket ->
            if (System.currentTimeMillis() - timedPacket.time > getTime()) {
                PacketUtils.handle(timedPacket.packet, false)
                true
            } else false
        }
    }

    @SubscribeEvent
    fun onRender3D(e: RenderLivingEvent) {
        //Stolen from LiquidBounce
        target.run {
            if (target.isPresent && esp.value) {
                val x =
                    realx - mc.renderManager.renderPosX
                val y =
                    realy - mc.renderManager.renderPosY
                val z =
                    realz - mc.renderManager.renderPosZ

                val axisAlignedBB =
                    target.get().boundingBox.offset(-target.get().posX, -target.get().posY, -target.get().posZ)
                        .offset(x, y, z)

                RenderUtils.drawBacktrackBox(
                    AxisAlignedBB.fromBounds(
                        axisAlignedBB.minX,
                        axisAlignedBB.minY,
                        axisAlignedBB.minZ,
                        axisAlignedBB.maxX,
                        axisAlignedBB.maxY,
                        axisAlignedBB.maxZ
                    ), Color.GREEN
                )
            }
        }
    }

    override fun onEnable() {
        incomingPackets.clear()
    }

    override fun onDisable() {
        if (PlayerUtils.isInGame()) {
            try {
                incomingPackets.removeIf { timedPacket ->
                    PacketUtils.handle(timedPacket.packet, false)
                    true
                }
            } catch (ignored: Exception) {
            }
        }
        incomingPackets.clear()
    }
}