package games.enchanted.inventoryblur.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Unique
	private static final Identifier INWORLD_INVENTORY_BACKGROUND_TEXTURE = Identifier.of("eg-inventory-blur","textures/gui/inworld_inventory_background.png");

	@Shadow
    public int width;
	@Shadow
    public int height;
	@Shadow
    protected abstract void applyBlur(float delta);

	// redirect call to render gradient and do nothing
	@Redirect(
		method = "renderInGameBackground(Lnet/minecraft/client/gui/DrawContext;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/DrawContext;fillGradient(IIIIII)V"
		)
	)
	public void ignoreGradientCall(DrawContext context, int startX, int startY, int endX, int endY, int colorStart, int colorEnd) {
	}

	// inject into renderInGameBackground to render background texture and apply blur
	@Inject(
		at = @At("HEAD"),
		method = "renderInGameBackground(Lnet/minecraft/client/gui/DrawContext;)V"
	)
	public void renderInGameBackground(DrawContext context, CallbackInfo ci) {
		this.applyBlur(1.0f);
		Screen.renderBackgroundTexture(context, INWORLD_INVENTORY_BACKGROUND_TEXTURE, 0, 0, 0.0f, 0.0f, width, height);
	}
}