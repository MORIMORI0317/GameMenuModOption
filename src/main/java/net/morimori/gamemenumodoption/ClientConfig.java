package net.morimori.gamemenumodoption;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfig {
    public static ForgeConfigSpec.ConfigValue<Boolean> ShowNotificationModUpdate;
    public static ForgeConfigSpec.ConfigValue<Boolean> RenderModListBackground;

    public static void init() {
        Pair<ConfigLoder, ForgeConfigSpec> client_config = new ForgeConfigSpec.Builder().configure(ConfigLoder::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, client_config.getRight());
    }

    public static class ConfigLoder {
        public ConfigLoder(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            ShowNotificationModUpdate = builder.define("Show Notification Mod Update", true);
            RenderModListBackground = builder.define("Render Mod List Background", true);
            builder.pop();
        }
    }
}
