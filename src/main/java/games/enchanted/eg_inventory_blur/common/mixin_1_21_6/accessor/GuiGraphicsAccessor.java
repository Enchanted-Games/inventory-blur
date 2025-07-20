//? if minecraft: >= 1.21.6 {
package games.enchanted.eg_inventory_blur.common.mixin_1_21_6.accessor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
    @Accessor("guiRenderState")
    GuiRenderState eg_inventory_blur$getGuiRenderState();
}
//?}