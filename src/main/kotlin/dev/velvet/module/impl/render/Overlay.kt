package dev.velvet.module.impl.render

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.ModuleManager
import dev.velvet.util.game.PlayerUtils
import net.weavemc.loader.api.event.RenderGameOverlayEvent
import net.weavemc.loader.api.event.SubscribeEvent

class Overlay : Module("Overlay", "Renders the overlay", Category.RENDER, 0, emptyArray()) {

    private var offset: Float = 0F
    private var enabledMods: ArrayList<String> = ArrayList()
    //Todo: Fix this. It doesn't render enabled modules, only "velvet dev".

    @SubscribeEvent
    fun onRender(e: RenderGameOverlayEvent.Post) {
        if (!PlayerUtils.isInGame() || mc.currentScreen != null) return
        mc.fontRendererObj.drawStringWithShadow("velvet dev", 2f, 2f, 0xa30a91)
        ModuleManager.getModules().values.forEach() { module ->
            if (module is ClickGUI || module is Overlay) return
            if (module.enabled) enabledMods.add(module.name)
            if (!module.enabled) enabledMods.remove(module.name)
        }
        val sortedEnabledMods: List<String> = enabledMods.sortedBy { it.length }.reversed()
        sortedEnabledMods.forEach() { string ->
            mc.fontRendererObj.drawStringWithShadow(string, 2f, 12f + offset, 0x22FFFFFF)
            offset += 10
        }
    }

    override fun onDisable() {
        offset = 0F
        enabledMods.clear()
    }
}