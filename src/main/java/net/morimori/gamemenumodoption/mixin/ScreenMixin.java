package net.morimori.gamemenumodoption.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.morimori.gamemenumodoption.ClientConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin extends FocusableGui {
    @Shadow
    public abstract void renderDirtBackground(int vOffset);

    @Inject(method = "renderBackground(Lcom/mojang/blaze3d/matrix/MatrixStack;I)V", at = @At("TAIL"), cancellable = true)
    private void renderBackground(MatrixStack matrixStack, int vOffset, CallbackInfo ci) {
        Screen screen = (Screen) (Object) this;
        if (screen instanceof ModListScreen && ClientConfig.RenderModListBackground.get()) {
            ci.cancel();
            this.renderDirtBackground(vOffset);
        }
    }
}
