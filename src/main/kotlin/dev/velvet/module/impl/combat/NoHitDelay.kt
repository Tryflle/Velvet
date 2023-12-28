package dev.velvet.module.impl.combat

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import dev.velvet.module.api.ModuleManager

class NoHitDelay: Module("NoHitDelay", "Get 1.7 hit registration", Category.COMBAT, 0, emptyArray()) {
    companion object {
        fun getEnabled(): Boolean {
            return ModuleManager.isModuleEnabled(ModuleManager.getModules().find { it.name == "NoHitDelay" }!!)
        }
    }
}