package net.morimori0317.gamemenumodoption.neoforge;

import net.morimori0317.gamemenumodoption.ClientConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfigNeoForge implements ClientConfig {
    private ModConfigSpec.ConfigValue<Boolean> showNotificationModUpdate;
    private ModConfigSpec.ConfigValue<Boolean> renderModListBackground;
    private ModConfigSpec.ConfigValue<Boolean> modMenuStyle;
    private ModConfigSpec.ConfigValue<Boolean> overwriteModButtonText;

    protected void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config(new ModConfigSpec.Builder()).build());
    }

    private ModConfigSpec.Builder config(ModConfigSpec.Builder builder) {
        builder.push("General");
        this.showNotificationModUpdate = builder.define("Show notification mod update", true);
        this.renderModListBackground = builder.define("Render mod list background", true);
        this.modMenuStyle = builder.define("ModMenu style", false);
        this.overwriteModButtonText = builder.define("Override mod button text", true);
        builder.pop();
        return builder;
    }

    @Override
    public boolean showNotificationModUpdate() {
        return this.showNotificationModUpdate.get();
    }

    @Override
    public boolean renderModListBackground() {
        return this.renderModListBackground.get();
    }

    @Override
    public boolean modMenuStyle() {
        return this.modMenuStyle.get();
    }

    @Override
    public boolean overwriteModButtonText() {
        return this.overwriteModButtonText.get();
    }
}
