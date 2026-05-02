package games.enchanted.eg_inventory_blur.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import games.enchanted.eg_inventory_blur.common.mixin.accessor.GuiGraphicsAccessor;
import games.enchanted.eg_inventory_blur.common.mixin.accessor.GuiRenderStateAccessor;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class, priority = 200)
public abstract class ScreenMixin {
    @Unique
    private static final Identifier INWORLD_INVENTORY_BACKGROUND_TEXTURE = Identifier.fromNamespaceAndPath("eg-inventory-blur","textures/gui/inworld_inventory_background.png");

    @Shadow public int width;
    @Shadow public int height;

    @Shadow
    protected abstract void extractBlurredBackground(GuiGraphicsExtractor graphics);

    @WrapOperation(
        method = "extractTransparentBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fillGradient(IIIIII)V"
        )
    )
    public void eg_inventory_blur$ignoreGradientCall(GuiGraphicsExtractor instance, int x1, int y1, int x2, int y2, int colorFrom, int colorTo, Operation<Void> original) {
    }

    @Inject(
        at = @At("HEAD"),
        method = "extractTransparentBackground"
    )
    public void eg_inventory_blur$applyBlurAndDrawBG(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if(((GuiRenderStateAccessor) ((GuiGraphicsAccessor) guiGraphics).eg_inventory_blur$getGuiRenderState()).eg_inventory_blur$getFirstStratumAfterBlur() != Integer.MAX_VALUE) {
            // dont draw the blur if its already been drawn this frame
            return;
        }
        this.extractBlurredBackground(guiGraphics);
        Screen.extractMenuBackgroundTexture(guiGraphics, INWORLD_INVENTORY_BACKGROUND_TEXTURE, 0, 0, 0.0f, 0.0f, width, height);
    }
}
