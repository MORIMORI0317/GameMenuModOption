package net.morimori0317.gamemenumodoption.integration;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.ModList;
import net.morimori0317.gamemenumodoption.ClientConfig;

public class MineTogetherIntegration {
    public static boolean isCompat() {
        return ModList.get().isLoaded("minetogether") && ClientConfig.MineTogetherCompatibility.get();
    }

    public static boolean isFixButtons() {
        return isCompat() && Minecraft.getInstance().getSingleplayerServer() != null;
    }
}
