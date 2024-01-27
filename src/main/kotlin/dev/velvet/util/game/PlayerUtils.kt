package dev.velvet.util.game

import net.minecraft.client.Minecraft

object PlayerUtils {

    fun isInGame(): Boolean {
        return Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null
    }
}
