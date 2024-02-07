package net.morimori0317.gamemenumodoption.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.morimori0317.gamemenumodoption.ClientConfig;

public class ClientConfigForge implements ClientConfig {
    private ForgeConfigSpec.ConfigValue<Boolean> showNotificationModUpdate;
    private ForgeConfigSpec.ConfigValue<Boolean> renderModListBackground;
    private ForgeConfigSpec.ConfigValue<Boolean> modMenuStyle;
    private ForgeConfigSpec.ConfigValue<Boolean> overwriteModButtonText;

    protected void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, config(new ForgeConfigSpec.Builder()).build());
    }

    private ForgeConfigSpec.Builder config(ForgeConfigSpec.Builder builder) {
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
