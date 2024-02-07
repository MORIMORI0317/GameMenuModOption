package net.morimori0317.gamemenumodoption;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;

import java.util.function.Consumer;

public class ScreenHandler {
    public static final ThreadLocal<Boolean> FORGE_MOD_BUTTON_ADDED = ThreadLocal.withInitial(() -> false);

    public static void onScreenInit(Screen screen, Consumer<GuiEventListener> guiEventListenerAppender) {
        if (!(screen instanceof PauseScreen pauseScreen) || !((GMMOPauseScreen) pauseScreen).isShowPauseMenu()) {
            return;
        }

        if (GameMenuModOption.getConfig().showNotificationModUpdate()) {
            guiEventListenerAppender.accept(new NotificationModUpdateListener(notificationInit(pauseScreen, ((GMMOPauseScreen) screen).getModOptionButton())));
        }
    }

    public static void onScreenRender(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (!(screen instanceof PauseScreen pauseScreen) || !((GMMOPauseScreen) pauseScreen).isShowPauseMenu()) {
            return;
        }

        if (GameMenuModOption.getConfig().showNotificationModUpdate()) {
            for (GuiEventListener child : screen.children()) {
                if (child instanceof NotificationModUpdateListener notificationModUpdateListener) {
                    notificationModUpdateListener.notificationModUpdateScreen.render(guiGraphics, mouseX, mouseY, partialTick);
                }
            }
        }
    }

    public static void onScreenBackgroundRender(Screen screen) {
        if (!GMMOUtils.isModListScreen(screen) || !GameMenuModOption.getConfig().renderModListBackground() || Minecraft.getInstance().level == null) {
            return;
        }

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, Screen.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, screen.height, 0.0D).uv(0.0F, (float) screen.height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(screen.width, screen.height, 0.0D).uv((float) screen.width / 32.0F, (float) screen.height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(screen.width, 0.0D, 0.0D).uv((float) screen.width / 32.0F, 0).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, 0).color(64, 64, 64, 255).endVertex();
        tesselator.end();
    }

    private static WrappedTitleScreenModUpdateIndicator notificationInit(PauseScreen gui, Button modButton) {
        WrappedTitleScreenModUpdateIndicator indicator = GMMOUtils.createTitleScreenModUpdateIndicator(modButton);
        indicator.resize(Minecraft.getInstance(), gui.width, gui.height);
        indicator.init();
        return indicator;
    }

    private static class NotificationModUpdateListener implements GuiEventListener {
        private final WrappedTitleScreenModUpdateIndicator notificationModUpdateScreen;

        private NotificationModUpdateListener(WrappedTitleScreenModUpdateIndicator notificationModUpdateScreen) {
            this.notificationModUpdateScreen = notificationModUpdateScreen;
        }

        @Override
        public void setFocused(boolean focused) {
        }

        @Override
        public boolean isFocused() {
            return false;
        }
    }

}
