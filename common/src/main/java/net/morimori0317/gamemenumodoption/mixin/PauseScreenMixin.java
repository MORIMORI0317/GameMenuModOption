package net.morimori0317.gamemenumodoption.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.Component;
import net.morimori0317.gamemenumodoption.GMMOPauseScreen;
import net.morimori0317.gamemenumodoption.GMMOUtils;
import net.morimori0317.gamemenumodoption.GameMenuModOption;
import net.morimori0317.gamemenumodoption.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(PauseScreen.class)
public class PauseScreenMixin implements GMMOPauseScreen {

    @Shadow
    @Final
    private static Component SHARE_TO_LAN;

    @Shadow
    @Final
    private static Component PLAYER_REPORTING;

    @Shadow
    @Final
    private static int BUTTON_WIDTH_FULL;

    @Shadow
    @Final
    private static int BUTTON_WIDTH_HALF;

    @Shadow
    @Final
    private boolean showPauseMenu;

    @Unique
    private Button modOptionButton;

    @Inject(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 4), locals = LocalCapture.CAPTURE_FAILHARD)
    private void createPauseMenuInject1(CallbackInfo ci, GridLayout gridlayout, GridLayout.RowHelper rowHelper) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!GameMenuModOption.getConfig().modMenuStyle()) {
            if (minecraft.hasSingleplayerServer() && !minecraft.getSingleplayerServer().isPublished()) {
                rowHelper.addChild(openScreenLongButton(SHARE_TO_LAN, () -> new ShareToLanScreen((PauseScreen) (Object) this)), 2);
            } else {
                rowHelper.addChild(openScreenLongButton(PLAYER_REPORTING, SocialInteractionsScreen::new), 2);
            }
        } else {
            this.modOptionButton = rowHelper.addChild(GMMOUtils.createModButton((PauseScreen) (Object) this, BUTTON_WIDTH_FULL), 2);
        }
    }

    @Inject(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;I)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0))
    private void createPauseMenuInject2(CallbackInfo ci) {
        ScreenHandler.FORGE_MOD_BUTTON_ADDED.set(true);
    }

    @Inject(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;I)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 0, shift = At.Shift.AFTER))
    private void createPauseMenuInject3(CallbackInfo ci) {
        ScreenHandler.FORGE_MOD_BUTTON_ADDED.set(false);
    }

    @Inject(method = "openScreenButton", at = @At("HEAD"), cancellable = true)
    private void openScreenButtonInject(Component name, Supplier<Screen> screen, CallbackInfoReturnable<Button> cir) {
        if (GameMenuModOption.getConfig().modMenuStyle())
            return;

        if (name == SHARE_TO_LAN || name == PLAYER_REPORTING) {
            Button modButton;
            modButton = GMMOUtils.createModButton((PauseScreen) (Object) this, BUTTON_WIDTH_HALF);
            modOptionButton = modButton;
            cir.setReturnValue(modButton);
        }
    }

    private Button openScreenLongButton(Component name, Supplier<Screen> screen) {
        return Button.builder(name, (button) -> Minecraft.getInstance().setScreen(screen.get())).width(BUTTON_WIDTH_FULL).build();
    }

    @Override
    public Button getModOptionButton() {
        return modOptionButton;
    }

    @Override
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }
}
