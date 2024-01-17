package dev.velvet.util.game

import net.minecraft.client.Minecraft

fun Minecraft.isInGame(): Boolean {
    return this.thePlayer != null && this.theWorld != null
}
