package net.morimori0317.gamemenumodoption.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.fml.ModList;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.IPauseScreen;
import net.morimori0317.gamemenumodoption.integration.MineTogetherIntegration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    public abstract void renderDirtBackground(int p_96627_);

    @Shadow
    @Final
    public List<Widget> renderables;

    @Inject(method = "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V", at = @At("TAIL"), cancellable = true)
    private void renderBackground(PoseStack p_96559_, int vOffset, CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;
        Screen screen = (Screen) (Object) this;
        if (screen instanceof ModListScreen && ClientConfig.RenderModListBackground.get()) {
            ci.cancel();
            this.renderDirtBackground(vOffset);
        }
    }

    @Inject(method = "init(Lnet/minecraft/client/Minecraft;II)V", at = @At("TAIL"))
    private void init(Minecraft p_96607_, int p_96608_, int p_96609_, CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;
        if (!(((Object) this) instanceof PauseScreen pauseScreen)) return;
        if (!((IPauseScreen) pauseScreen).isShowPauseMenu()) return;

        boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");
        boolean intgrationFlg = MineTogetherIntegration.isFixButtons();

        if (!gmrmflag || intgrationFlg) {
            if (ClientConfig.SlideUpGameMenuButtons.get()) {
                for (Widget widget : renderables) {
                    if (widget instanceof AbstractWidget abstractWidget)
                        abstractWidget.y -= 16;
                }
            }
        }
    }
}