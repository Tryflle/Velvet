package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.SliderSetting

private var range: SliderSetting = SliderSetting("Range", "Range", emptyArray(), 3.0, 6.0, 0.1, 3.2)
class Reach : Module("Reach", "Increases your reach", Category.COMBAT, 0, arrayOf(range)) {
    //TODO: make it lol, mixin/hook EntityRenderer.getMouseOver
}