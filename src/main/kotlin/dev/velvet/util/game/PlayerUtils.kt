package dev.velvet.util.game

import net.minecraft.client.Minecraft

val Minecraft.isInGame: Boolean 
    get() {
        return this.thePlayer != null && this.theWorld != null
    }
