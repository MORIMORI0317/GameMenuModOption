package net.morimori0317.gamemenumodoption;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(GameMenuModOption.MODID)
public class GameMenuModOption {
    public static final String MODID = "gamemenumodoption";

    public GameMenuModOption() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, isServer) -> true));
        ClientConfig.init();
    }
}