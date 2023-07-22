package net.morimori0317.gmmo.mixin;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.network.chat.Component;
import net.morimori0317.gmmo.*;
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
    private boolean showPauseMenu;
    @Shadow
    @Final
    private static int BUTTON_WIDTH_FULL;
    @Shadow
    @Final
    private static int BUTTON_WIDTH_HALF;
    @Unique
    private static final Component MOD_OPTION = Component.translatable("menu.modoption");
    @Unique
    private Button modOptionButton;

    @Inject(method = "createPauseMenu", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/layouts/GridLayout$RowHelper;addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;)Lnet/minecraft/client/gui/layouts/LayoutElement;", ordinal = 4), locals = LocalCapture.CAPTURE_FAILHARD)
    private void createPauseMenuInject(CallbackInfo ci, GridLayout gridlayout, GridLayout.RowHelper rowHelper) {
        var mc = Minecraft.getInstance();

        if (!ClientConfig.MOD_MENU_STYLE.get()) {
            if (mc.hasSingleplayerServer() && !mc.getSingleplayerServer().isPublished()) {
                rowHelper.addChild(openScreenLongButton(SHARE_TO_LAN, () -> new ShareToLanScreen((PauseScreen) (Object) this)), 2);
            } else {
                rowHelper.addChild(openScreenLongButton(PLAYER_REPORTING, SocialInteractionsScreen::new), 2);
            }
        } else {
            if (GMMOUtils.existModButton()) {
                this.modOptionButton = rowHelper.addChild(GMMOUtils.createForgeModButton((PauseScreen) (Object) this, BUTTON_WIDTH_FULL), 2);
            } else {
                this.modOptionButton = rowHelper.addChild(openScreenLongButton(MOD_OPTION, () -> GameMenuModOptionAPI.getOpenModOptions((PauseScreen) (Object) this)), 2);
            }
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
        if (ClientConfig.MOD_MENU_STYLE.get())
            return;

        if (name == SHARE_TO_LAN || name == PLAYER_REPORTING) {
            Button modButton;

            if (GMMOUtils.existModButton()) {
                modButton = GMMOUtils.createForgeModButton((PauseScreen) (Object) this, BUTTON_WIDTH_HALF);
            } else {
                modButton = Button.builder(MOD_OPTION, (button) -> Minecraft.getInstance().setScreen(GameMenuModOptionAPI.getOpenModOptions((PauseScreen) (Object) this))).width(BUTTON_WIDTH_HALF).build();
            }


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
