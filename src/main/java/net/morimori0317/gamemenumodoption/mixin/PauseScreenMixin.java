package net.morimori0317.gamemenumodoption.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.ModList;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.GameMenuModOptionAPI;
import net.morimori0317.gamemenumodoption.IPauseScreen;
import net.morimori0317.gamemenumodoption.integration.MineTogetherIntegration;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen implements IPauseScreen {
    @Shadow
    @Final
    private boolean showPauseMenu;

    @Unique
    private boolean showUpdate = ClientConfig.ShowNotificationModUpdate.get();
    @Unique
    private NotificationModUpdateScreen modUpdateNotification;

    protected PauseScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (ModList.get().isLoaded("bettergamemenu")) return;

        if (this.showPauseMenu) {

            boolean intgrationFlg = MineTogetherIntegration.isFixButtons();

            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");
            Button options = (Button) this.renderables.get(gmrmflag ? 3 : 5);
            Button returnToMenu = (Button) this.renderables.get(gmrmflag ? 5 : 7);
            Button shareToLan = (Button) this.renderables.get(6);
            if (shareToLan != null) {
                if (!intgrationFlg) {
                    shareToLan.x = width / 2 - 102;
                    shareToLan.setWidth(204);
                }
                if (gmrmflag)
                    shareToLan.y -= 24;
            }

            Button button = new Button(intgrationFlg ? width / 2 - 102 : this.width / 2 + 4, this.height / 4 + ((gmrmflag || intgrationFlg) ? 96 : 120) - 16, intgrationFlg ? 204 : 98, 20, new TranslatableComponent("menu.modoption"), (n) -> {
                var openGui = GameMenuModOptionAPI.getOpenModOptions(this);
                if (openGui != null)
                    Minecraft.getInstance().setScreen(openGui);
            });
            addRenderableWidget(button);
            if (showUpdate)
                modUpdateNotification = init((PauseScreen) (Object) this, button);

            if (!gmrmflag || intgrationFlg) {
                if (options != null)
                    options.y += 24;
                if (returnToMenu != null)
                    returnToMenu.y += 24;

                /*if (ClientConfig.SlideUpGameMenuButtons.get()) {
                    for (Widget widget : renderables) {
                        if (widget instanceof AbstractWidget abstractWidget)
                            abstractWidget.y -= 16;
                    }
                }*/
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

    @Override
    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }
}
