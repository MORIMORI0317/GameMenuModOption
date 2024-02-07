package net.morimori0317.gamemenumodoption.neoforge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.morimori0317.gamemenumodoption.WrappedTitleScreenModUpdateIndicator;
import net.neoforged.neoforge.client.gui.TitleScreenModUpdateIndicator;

public class WrappedTitleScreenModUpdateIndicatorNeoForgeImpl implements WrappedTitleScreenModUpdateIndicator {
    private final TitleScreenModUpdateIndicator indicator;

    public WrappedTitleScreenModUpdateIndicatorNeoForgeImpl(Button modButton) {
        this.indicator = new TitleScreenModUpdateIndicator(modButton);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        this.indicator.resize(minecraft, width, height);
    }

    @Override
    public void init() {
        this.indicator.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.indicator.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
