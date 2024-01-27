package dev.velvet.command.api

open class Command(
    val name: String,
    val description: String,
    val aliases: Array<String>
) {

    open fun execute(args: Array<String>) {}
}