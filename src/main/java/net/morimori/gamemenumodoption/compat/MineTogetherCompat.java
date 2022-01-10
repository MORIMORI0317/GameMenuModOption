package net.morimori.gamemenumodoption.compat;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.morimori.gamemenumodoption.ClientConfig;

public class MineTogetherCompat {
    public static boolean isCompat() {
        return ModList.get().isLoaded("minetogether") && ClientConfig.MineTogetherCompatibility.get();
    }

    public static boolean isFixButtons() {
        return isCompat() && Minecraft.getInstance().getIntegratedServer() != null;
    }
}
