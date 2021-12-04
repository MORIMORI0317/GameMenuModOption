package net.morimori0317.gamemenumodoption;

import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.gui.ModListScreen;

import java.util.function.Function;

public class GameMenuModOptionAPI {
    private static Function<Screen, Screen> OPEN_MOD_OPTIONS = ModListScreen::new;

    public static void setOpenModOptions(Function<Screen, Screen> modOptionsFactory) {
        OPEN_MOD_OPTIONS = modOptionsFactory;
    }

    public static Screen getOpenModOptions(Screen screen) {
        if (OPEN_MOD_OPTIONS == null)
            return null;
        return OPEN_MOD_OPTIONS.apply(screen);
    }
}