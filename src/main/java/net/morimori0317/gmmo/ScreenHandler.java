package net.morimori0317.gmmo;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.client.gui.ModListScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ScreenHandler {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post e) {
        if (!(e.getScreen() instanceof PauseScreen pauseScreen) || !((GMMOPauseScreen) pauseScreen).isShowPauseMenu())
            return;

        if (ClientConfig.SHOW_NOTIFICATION_MOD_UPDATE.get())
            e.addListener(new NotificationModUpdateListener(notificationInit(pauseScreen, ((GMMOPauseScreen) e.getScreen()).getModOptionButton())));
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post e) {
        if (!(e.getScreen() instanceof PauseScreen pauseScreen) || !((GMMOPauseScreen) pauseScreen).isShowPauseMenu())
            return;

        if (ClientConfig.SHOW_NOTIFICATION_MOD_UPDATE.get()) {
            for (GuiEventListener child : e.getScreen().children()) {
                if (child instanceof NotificationModUpdateListener notificationModUpdateListener)
                    notificationModUpdateListener.notificationModUpdateScreen.render(e.getGuiGraphics(), e.getMouseX(), e.getMouseY(), e.getPartialTick());
            }
        }
    }

    @SubscribeEvent
    public static void onScreenBackgroundRender(ScreenEvent.BackgroundRendered e) {
        if (!(e.getScreen() instanceof ModListScreen modListScreen) || !ClientConfig.RENDER_MOD_LIST_BACKGROUND.get() || Minecraft.getInstance().level == null)
            return;

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, Screen.BACKGROUND_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(0.0D, modListScreen.height, 0.0D).uv(0.0F, (float) modListScreen.height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(modListScreen.width, modListScreen.height, 0.0D).uv((float) modListScreen.width / 32.0F, (float) modListScreen.height / 32.0F).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(modListScreen.width, 0.0D, 0.0D).uv((float) modListScreen.width / 32.0F, 0).color(64, 64, 64, 255).endVertex();
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).uv(0.0F, 0).color(64, 64, 64, 255).endVertex();
        tesselator.end();
    }

    private static TitleScreenModUpdateIndicator notificationInit(PauseScreen gui, Button modButton) {
        TitleScreenModUpdateIndicator notificationModUpdateScreen = new TitleScreenModUpdateIndicator(modButton);
        notificationModUpdateScreen.resize(gui.getMinecraft(), gui.width, gui.height);
        notificationModUpdateScreen.init();
        return notificationModUpdateScreen;
    }

    private static class NotificationModUpdateListener implements GuiEventListener {
        private final TitleScreenModUpdateIndicator notificationModUpdateScreen;

        private NotificationModUpdateListener(TitleScreenModUpdateIndicator notificationModUpdateScreen) {
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
