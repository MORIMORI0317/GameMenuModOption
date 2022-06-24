package net.morimori0317.gmmo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.morimori0317.gmmo.Integration.BetterGameMenuIntegration;

import java.util.List;

public class ScreenHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.InitScreenEvent.Post e) {
        if (!(e.getScreen() instanceof PauseScreen pauseScreen) || !pauseScreen.showPauseMenu || BetterGameMenuIntegration.isModLoaded())
            return;
        var options = findButton(e.getListenersList(), "menu.options");
        var returnToMenu = findButton(e.getListenersList(), mc.isLocalServer() ? "menu.returnToMenu" : "menu.disconnect");
        var shareToLan = findButton(e.getListenersList(), "menu.shareToLan");

        boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");

        if (shareToLan != null) {
            shareToLan.x = e.getScreen().width / 2 - 102;
            shareToLan.setWidth(204);
            if (gmrmflag)
                shareToLan.y -= 24;
        }

        Button button = new Button(e.getScreen().width / 2 + 4, e.getScreen().height / 4 + (gmrmflag ? 96 : 120) - 16, 98, 20, Component.translatable("menu.modoption"), (n) -> {
            var openGui = GameMenuModOptionAPI.getOpenModOptions(e.getScreen());
            if (openGui != null)
                Minecraft.getInstance().setScreen(openGui);
        });
        e.addListener(button);

        if (!gmrmflag) {
            if (options != null)
                options.y += 24;
            if (returnToMenu != null)
                returnToMenu.y += 24;

            if (ClientConfig.SlideUpGameMenuButtons.get()) {
                for (Widget widget : e.getScreen().renderables) {
                    if (widget instanceof AbstractWidget abstractWidget)
                        abstractWidget.y -= 16;
                }
            }
        }

        if (ClientConfig.ShowNotificationModUpdate.get())
            e.addListener(new NotificationModUpdateListener(notificationInit(pauseScreen, button)));
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.DrawScreenEvent.Post e) {
        if (!(e.getScreen() instanceof PauseScreen pauseScreen) || !pauseScreen.showPauseMenu || BetterGameMenuIntegration.isModLoaded())
            return;

        if (ClientConfig.ShowNotificationModUpdate.get()) {
            for (GuiEventListener child : e.getScreen().children()) {
                if (child instanceof NotificationModUpdateListener notificationModUpdateListener)
                    notificationModUpdateListener.notificationModUpdateScreen.render(e.getPoseStack(), e.getMouseX(), e.getMouseY(), e.getPartialTick());
            }
        }
    }

    @SubscribeEvent
    public static void onScreenBackgroundRender(ScreenEvent.BackgroundDrawnEvent e) {
        if (!(e.getScreen() instanceof ModListScreen modListScreen) || !ClientConfig.RenderModListBackground.get() || BetterGameMenuIntegration.isModLoaded() || Minecraft.getInstance().level == null)
            return;

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, GuiComponent.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, modListScreen.height, 0.0D).uv(0.0F, (float) modListScreen.height / 32.0F + (float) 0).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(modListScreen.width, modListScreen.height, 0.0D).uv((float) modListScreen.width / 32.0F, (float) modListScreen.height / 32.0F + (float) 0).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(modListScreen.width, 0.0D, 0.0D).uv((float) modListScreen.width / 32.0F, (float) 0).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, (float) 0).color(64, 64, 64, 255).endVertex();
        tesselator.end();
    }

    private static Button findButton(List<GuiEventListener> listeners, String name) {
        for (GuiEventListener listener : listeners) {
            if (listener instanceof Button button && button.getMessage() instanceof MutableComponent mutableComponent && mutableComponent.getContents() instanceof TranslatableContents translatableContents) {
                if (translatableContents.getKey().equals(name))
                    return button;
            }
        }
        return null;
    }

    private static NotificationModUpdateScreen notificationInit(PauseScreen gui, Button modButton) {
        NotificationModUpdateScreen notificationModUpdateScreen = new NotificationModUpdateScreen(modButton);
        notificationModUpdateScreen.resize(gui.getMinecraft(), gui.width, gui.height);
        notificationModUpdateScreen.init();
        return notificationModUpdateScreen;
    }

    private static class NotificationModUpdateListener implements GuiEventListener {
        private final NotificationModUpdateScreen notificationModUpdateScreen;

        private NotificationModUpdateListener(NotificationModUpdateScreen notificationModUpdateScreen) {
            this.notificationModUpdateScreen = notificationModUpdateScreen;
        }
    }
}
