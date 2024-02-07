package net.morimori0317.gamemenumodoption.explatform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.morimori0317.gamemenumodoption.ClientConfig;
import net.morimori0317.gamemenumodoption.WrappedTitleScreenModUpdateIndicator;

public class GMMOExpectPlatform {
    @ExpectPlatform
    public static Button createModButton(Screen screen, int width) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isModListScreen(Screen screen) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WrappedTitleScreenModUpdateIndicator createTitleScreenModUpdateIndicator(Button modButton) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ClientConfig getConfig() {
        throw new AssertionError();
    }
}
