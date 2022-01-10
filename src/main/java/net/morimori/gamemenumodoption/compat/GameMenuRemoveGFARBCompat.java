package net.morimori.gamemenumodoption.compat;

import net.minecraftforge.fml.ModList;
import net.morimori.gamemenumodoption.ClientConfig;

public class GameMenuRemoveGFARBCompat {
    public static boolean isCompat() {
        return ModList.get().isLoaded("gamemenuremovegfarb") && ClientConfig.GameMenuRemoveGFARBCompatibility.get();
    }
}
