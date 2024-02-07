package net.morimori0317.gamemenumodoption.neoforge;

import net.morimori0317.gamemenumodoption.ScreenHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class ScreenHandlerNeoForge {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post e) {
        ScreenHandler.onScreenInit(e.getScreen(), e::addListener);
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post e) {
        ScreenHandler.onScreenRender(e.getScreen(), e.getGuiGraphics(), e.getMouseX(), e.getMouseY(), e.getPartialTick());
    }

    @SubscribeEvent
    public static void onScreenBackgroundRender(ScreenEvent.BackgroundRendered e) {
        ScreenHandler.onScreenBackgroundRender(e.getScreen());
    }
}
