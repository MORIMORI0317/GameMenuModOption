package net.morimori0317.gmmo;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean> SHOW_NOTIFICATION_MOD_UPDATE;
    public static ForgeConfigSpec.ConfigValue<Boolean> RENDER_MOD_LIST_BACKGROUND;
    public static ForgeConfigSpec.ConfigValue<Boolean> MOD_MENU_STYLE;

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config(new ForgeConfigSpec.Builder()).build());
    }

    private static ForgeConfigSpec.Builder config(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        SHOW_NOTIFICATION_MOD_UPDATE = builder.define("Show notification mod update", true);
        RENDER_MOD_LIST_BACKGROUND = builder.define("Render mod list background", true);
        MOD_MENU_STYLE = builder.define("ModMenu style", false);
        builder.pop();
        return builder;
    }
}
