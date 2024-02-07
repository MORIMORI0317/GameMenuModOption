package net.morimori0317.gamemenumodoption.explatform.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.ModMenuButton;
import net.morimori0317.gamemenumodoption.WrappedTitleScreenModUpdateIndicator;
import net.morimori0317.gamemenumodoption.neoforge.GameMenuModOptionNeoForge;
import net.morimori0317.gamemenumodoption.neoforge.WrappedTitleScreenModUpdateIndicatorNeoForgeImpl;
import net.neoforged.neoforge.client.gui.ModListScreen;

public class GMMOExpectPlatformImpl {
    public static Button createModButton(Screen screen, int width) {
        return new ModMenuButton(0, 0, width, 20, Component.translatable("fml.menu.mods"), button -> Minecraft.getInstance().setScreen(new ModListScreen(screen)));
    }

    public static boolean isModListScreen(Screen screen) {
        return screen instanceof ModListScreen;
    }

    public static WrappedTitleScreenModUpdateIndicator createTitleScreenModUpdateIndicator(Button modButton) {
        return new WrappedTitleScreenModUpdateIndicatorNeoForgeImpl(modButton);
    }

    public static ClientConfig getConfig() {
        return GameMenuModOptionNeoForge.CONFIG;
    }
}
