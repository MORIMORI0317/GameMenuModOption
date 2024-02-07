package net.morimori0317.gamemenumodoption;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.morimori0317.gamemenumodoption.explatform.GMMOExpectPlatform;

public class GMMOUtils {
    private static final String FORGE_MOD_BUTTON_TEXT = "fml.menu.mods";

    public static boolean isForgeModButton(Button button) {
        Component msg = button.getMessage();

        if (msg.getContents() instanceof TranslatableContents translatableContents) {
            return FORGE_MOD_BUTTON_TEXT.equals(translatableContents.getKey());
        }

        return false;
    }

    public static Button createModButton(Screen screen, int width) {
        return GMMOExpectPlatform.createModButton(screen, width);
    }

    public static boolean isModListScreen(Screen screen) {
        return GMMOExpectPlatform.isModListScreen(screen);
    }

    public static WrappedTitleScreenModUpdateIndicator createTitleScreenModUpdateIndicator(Button modButton) {
        return GMMOExpectPlatform.createTitleScreenModUpdateIndicator(modButton);
    }
}
