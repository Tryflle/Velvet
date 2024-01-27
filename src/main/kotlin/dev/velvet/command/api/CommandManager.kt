package dev.velvet.command.api

import dev.velvet.command.impl.Help
import dev.velvet.command.impl.Toggle
import net.weavemc.loader.api.event.ChatSentEvent
import net.weavemc.loader.api.event.SubscribeEvent

object CommandManager {
    //TODO: Fix both of my commands not working as intended.

    private val commands: HashMap<String, Command> = hashMapOf(
        Help().name to Help(),
        Toggle().name to Toggle()
    )

    fun getCommands(): HashMap<String, Command> = commands

    @SubscribeEvent
    fun onChat(e: ChatSentEvent) {
        commands.values.forEach { command ->
            if (e.message.startsWith(".${command.name}" ) || e.message.startsWith(".${command.aliases}")) {
                command.execute(e.message.split(" ").toTypedArray())
                e.cancelled = true
            }
        }
    }
}