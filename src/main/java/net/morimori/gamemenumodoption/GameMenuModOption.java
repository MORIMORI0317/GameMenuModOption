package net.morimori.gamemenumodoption;

import net.minecraftforge.fml.common.Mod;

@Mod(GameMenuModOption.MODID)
public class GameMenuModOption {
    public static final String MODID = "gamemenumodoption";

    public GameMenuModOption() {
        ClientConfig.init();
    }
}
