package dev.velvet.event

import net.weavemc.loader.api.event.Event

class WindowResizeEvent(val width: Int, height: Int) : Event() {
}