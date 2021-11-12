package net.morimori0317.gamemenumodoption.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmlclient.gui.screen.ModListScreen;
import net.morimori0317.gamemenumodoption.ClientConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    public abstract void renderDirtBackground(int p_96627_);

    @Inject(method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", at = @At("TAIL"), cancellable = true)
    private void renderBackground(PoseStack p_96559_, int vOffset, CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;
        Screen screen = (Screen) (Object) this;
        if (screen instanceof ModListScreen && ClientConfig.RenderModListBackground.get()) {
            ci.cancel();
            this.renderDirtBackground(vOffset);
        }
    }
}
