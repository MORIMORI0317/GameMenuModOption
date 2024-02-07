package net.morimori0317.gamemenumodoption.forge;

import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.morimori0317.gamemenumodoption.ScreenHandler;

public class ScreenHandlerForge {
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
