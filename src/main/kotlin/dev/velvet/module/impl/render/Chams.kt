package dev.velvet.module.impl.render

import dev.velvet.module.api.Category
import dev.velvet.module.api.Module
import net.minecraft.entity.player.EntityPlayer
import net.weavemc.loader.api.event.RenderLivingEvent
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.opengl.GL11.*

class Chams() : Module("Chams", "Render entities above blocks", Category.RENDER, 0, emptyArray()) {

    @SubscribeEvent
    fun onPreLivingRender(e: RenderLivingEvent.Pre) {
        if (e.entity is EntityPlayer) {
            glEnable(GL_POLYGON_OFFSET_FILL)
            glPolygonOffset(1.0F, -1100000.0F)
        }
    }

    @SubscribeEvent
    fun onPostLivingRender(e: RenderLivingEvent.Post) {
        if (e.entity is EntityPlayer) {
            glDisable(GL_POLYGON_OFFSET_FILL);
            glPolygonOffset(1.0F, 1100000.0F)
        }
    }
}