package games.enchanted.eg_inventory_blur.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class, priority = 200)
public abstract class ScreenMixin {
    @Unique
    private static final ResourceLocation INWORLD_INVENTORY_BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath("eg-inventory-blur","textures/gui/inworld_inventory_background.png");

    @Shadow public int width;
    @Shadow public int height;
    //? if minecraft: >= 1.21.6 {
    @Shadow protected abstract void renderBlurredBackground(GuiGraphics par1);
    //?} else {
    /*@Shadow protected abstract void renderBlurredBackground();
    *///?}

    @WrapOperation(
        method = "renderTransparentBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphics;fillGradient(IIIIII)V"
        )
    )
    public void eg_inventory_blur$ignoreGradientCall(GuiGraphics instance, int x1, int y1, int x2, int y2, int colorFrom, int colorTo, Operation<Void> original) {
    }

    @Inject(
        at = @At("HEAD"),
        method = "renderTransparentBackground"
    )
    public void eg_inventory_blur$applyBlurAndDrawBG(GuiGraphics guiGraphics, CallbackInfo ci) {
        this.renderBlurredBackground();
        Screen.renderMenuBackgroundTexture(guiGraphics, INWORLD_INVENTORY_BACKGROUND_TEXTURE, 0, 0, 0.0f, 0.0f, width, height);
    }
}
