package net.morimori0317.gamemenumodoption.neoforge;

import net.morimori0317.gamemenumodoption.GameMenuModOption;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;

@Mod(GameMenuModOption.MOD_ID)
public class GameMenuModOptionNeoForge {
    public static final ClientConfigNeoForge CONFIG = new ClientConfigNeoForge();

    public GameMenuModOptionNeoForge() {
        CONFIG.init();

        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (remote, isServer) -> true));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(ScreenHandlerNeoForge.class);
    }
}
