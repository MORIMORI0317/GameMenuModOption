package net.morimori0317.gmmo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

@Mod(GameMenuModOption.MODID)
public class GameMenuModOption {
    public static final String MODID = "gamemenumodoption";

    public GameMenuModOption() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        ClientConfig.init();
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, isServer) -> true));
    }

    private void doClientStuff(final FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.register(ScreenHandler.class);
    }
}
