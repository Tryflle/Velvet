package dev.velvet.module.impl.render

import dev.velvet.clickgui.ClickGui
import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.util.game.PlayerUtils
import org.lwjgl.input.Keyboard

class ClickGUI : Module("ClickGUI", "Opens the ClickGUI", Category.RENDER, Keyboard.KEY_INSERT, emptyArray()) {

    private var cgui: ClickGui? = null

    override fun onEnable() {
        if (PlayerUtils.isInGame() && mc.currentScreen == null) {
            cgui = ClickGui()
        }

        enabled = false
        mc.displayGuiScreen(cgui)
    }
}
