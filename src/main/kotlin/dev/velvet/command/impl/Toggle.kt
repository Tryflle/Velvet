package dev.velvet.command.impl

import dev.velvet.command.api.Command
import dev.velvet.module.api.ModuleManager
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

class Toggle : Command("toggle", "Toggles a module", arrayOf("t")) {

    override fun execute(args: Array<String>) {
        ModuleManager.getModules().values.forEach { module ->
            if (module.name.lowercase() == args[1].lowercase()) {
                module.toggle()
                Minecraft.getMinecraft().thePlayer.addChatMessage(ChatComponentText("Toggled ${module.name}"))
                return
            }
            else return
        }
    }
}