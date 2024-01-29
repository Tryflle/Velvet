package dev.velvet.module.impl.misc

import dev.velvet.module.api.Category
import dev.velvet.module.api.EnumSetting
import dev.velvet.module.api.Module
import dev.velvet.util.game.PlayerUtils
import net.minecraft.network.play.server.S02PacketChat
import net.weavemc.loader.api.event.PacketEvent
import net.weavemc.loader.api.event.SubscribeEvent

private var type: EnumSetting = EnumSetting("Type", "Type", emptyArray(), "FORTNITE", listOf("FORTNITE", "UWU", "FURRY", "NORMAL", "MRRESETTI"))

class Killsults : Module("Killsults", "Killsults", Category.MISC, 0, arrayOf(type)) {

    private val fortniteInsults = arrayOf("You're such a bot kid", "BROOO UR LITERALLY DOGWATER", "BRO IM CRANKIN 90S ON YOU", "bro ur such a sweat!!", "you're a scrub kid", "i have 90000 more vbucks than you")
    private val uwuInsults = arrayOf("uwu nyaaa~", "owooo nya~~", "woopsies didnt mean to kill you owo", "owo", "meow", "uwu", "you just died owo", "you just got wrekt uwu")
    private val furryInsults = arrayOf("come to furcon with me PLEASE", "me and the furs going to go enable killsults", "meow", "woof", "im a fuwwy uwu", "haiii", "OwO", "UwU")
    private val normalInsults = arrayOf("u suck at this game", "get better", "get wrekt loser", "is this your first time playing or something?", "I think a literal monkey could play better than you")
    private val ressetiInsults = arrayOf("TAKE A SHOWER NOW", "CLEAN YOUR HAIR NOW", "YOU ARE SO PERSISTENT AT THIS, STOP AT ONCE", "Just a fair warning, please stop doing that.")

    private fun getInsult(): String {
        if (type.value == "FORTNITE") return fortniteInsults.random()
        if (type.value == "UWU") return uwuInsults.random()
        if (type.value == "FURRY") return furryInsults.random()
        if (type.value == "MRRESETTI") return ressetiInsults.random()
        return if (type.value == "NORMAL") normalInsults.random()
        else ""
    }
    @SubscribeEvent
    fun onPacket(e: PacketEvent) {
        if (PlayerUtils.isInGame() && e.packet is S02PacketChat) {
            val message = (e.packet as S02PacketChat).chatComponent.unformattedText
            if (message.contains("killed by ${mc.thePlayer.name}") || message.contains("shot by ${mc.thePlayer.name}") || message.contains("slain by ${mc.thePlayer.name}")) {
                mc.thePlayer.sendChatMessage(getInsult())
            }
        }
    }
}