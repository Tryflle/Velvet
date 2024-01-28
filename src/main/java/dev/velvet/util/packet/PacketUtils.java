package dev.velvet.util.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;

@SuppressWarnings("unused")
public class PacketUtils {
    //I hate to do it, but I had many issues with types with this class in Kotlin, so I just used Java instead where it works fine.

    public static void send(Packet packet) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(packet);
    }
    public static void handle(Packet packet, boolean outgoing) {
        if (outgoing) {
            Minecraft.getMinecraft().getNetHandler().netManager.sendPacket(packet);
        } else {
            if (Minecraft.getMinecraft().getNetHandler().netManager.channel.isOpen()) {
                try {
                    packet.processPacket(Minecraft.getMinecraft().getNetHandler());
                }
                catch (final ThreadQuickExitException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}