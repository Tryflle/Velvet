package dev.velvet.command.impl

import dev.velvet.command.api.Command
import dev.velvet.command.api.CommandManager
import net.minecraft.client.Minecraft
import net.minecraft.util.ChatComponentText

class Help : Command("help", "Shows all commands", emptyArray()) {

    override fun execute(args: Array<String>) {
        CommandManager.getCommands().values.forEach { command ->
            Minecraft.getMinecraft()?.thePlayer?.addChatMessage(ChatComponentText ("${command.name} - ${command.description}"))
        }
    }
}