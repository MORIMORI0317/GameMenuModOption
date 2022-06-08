package net.morimori0317.gmmo.Integration;

import net.minecraftforge.fml.ModList;

public class BetterGameMenuIntegration {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("bettergamemenu");
    }
}
