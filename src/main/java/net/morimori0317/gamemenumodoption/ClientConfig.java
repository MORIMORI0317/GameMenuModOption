package net.morimori0317.gamemenumodoption;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean> ShowNotificationModUpdate;
    public static ForgeConfigSpec.ConfigValue<Boolean> RenderModListBackground;
    public static ForgeConfigSpec.ConfigValue<Boolean> SlideUpGameMenuButtons;
    public static ForgeConfigSpec.ConfigValue<Boolean> MineTogetherCompatibility;

    public static void init() {
        var config = buildConfig(new ForgeConfigSpec.Builder()).build();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config);
    }

    private static ForgeConfigSpec.Builder buildConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        ShowNotificationModUpdate = builder.define("Show notification mod update", true);
        RenderModListBackground = builder.define("Render mod list background", true);
        SlideUpGameMenuButtons = builder.define("Slide up game menu buttons", true);
        MineTogetherCompatibility = builder.define("MineTogether Compatibility", true);
        builder.pop();
        return builder;
    }
}
