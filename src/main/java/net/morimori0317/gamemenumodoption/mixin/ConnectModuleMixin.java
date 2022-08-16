package net.morimori0317.gamemenumodoption.mixin;

import dev.architectury.hooks.client.screen.ScreenAccess;
import net.creeperhost.minetogether.module.connect.ConnectModule;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.integration.MineTogetherIntegration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ConnectModule.class)
public class ConnectModuleMixin {
    @Inject(method = "onScreenOpen", at = @At(value = "INVOKE", target = "Ldev/architectury/hooks/client/screen/ScreenHooks;addRenderableWidget(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/client/gui/components/AbstractWidget;)Lnet/minecraft/client/gui/components/AbstractWidget;", ordinal = 0), remap = false, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onScreenOpen(Screen screen, ScreenAccess screenAccess, CallbackInfo ci, IntegratedServer integratedServer, AbstractWidget feedBack, AbstractWidget bugs, AbstractWidget openToLan, AbstractWidget options, Button guiButton) {
        if (MineTogetherIntegration.isCompat() && ClientConfig.SlideUpGameMenuButtons.get())
            guiButton.y -= 16;
    }
}