package net.morimori0317.gamemenumodoption.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.ModList;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.GameMenuModOptionAPI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
    @Shadow
    @Final
    private boolean showPauseMenu;

    private boolean showUpdate = ClientConfig.ShowNotificationModUpdate.get();

    private NotificationModUpdateScreen modUpdateNotification;

    protected PauseScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;

        if (this.showPauseMenu) {
            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");
            Button options = (Button) this.renderables.get(gmrmflag ? 3 : 5);
            Button returnToMenu = (Button) this.renderables.get(gmrmflag ? 5 : 7);
            Button shareToLan = (Button) this.renderables.get(6);
            if (shareToLan != null) {
                shareToLan.x = width / 2 - 102;
                shareToLan.setWidth(204);
                if (gmrmflag)
                    shareToLan.y -= 24;
            }

            Button button = new Button(this.width / 2 + 4, this.height / 4 + (gmrmflag ? 96 : 120) - 16, 98, 20, new TranslatableComponent("menu.modoption"), (n) -> {
                var openGui = GameMenuModOptionAPI.getOpenModOptions(this);
                if (openGui != null)
                    Minecraft.getInstance().setScreen(openGui);
            });
            addRenderableWidget(button);
            if (showUpdate)
                modUpdateNotification = init((PauseScreen) (Object) this, button);

            if (!gmrmflag) {
                if (options != null)
                    options.y += 24;
                if (returnToMenu != null)
                    returnToMenu.y += 24;
                if (ClientConfig.SlideUpGameMenuButtons.get()) {
                    for (Widget widget : renderables) {
                        if (widget instanceof AbstractWidget abstractWidget)
                            abstractWidget.y -= 16;
                    }
                }
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;
        if (this.showPauseMenu) {
            if (showUpdate)
                modUpdateNotification.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }


    private static NotificationModUpdateScreen init(PauseScreen gui, Button modButton) {
        NotificationModUpdateScreen notificationModUpdateScreen = new NotificationModUpdateScreen(modButton);
        notificationModUpdateScreen.resize(gui.getMinecraft(), gui.width, gui.height);
        notificationModUpdateScreen.init();
        return notificationModUpdateScreen;
    }
}
