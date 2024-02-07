package net.morimori0317.gamemenumodoption.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import net.morimori0317.gamemenumodoption.WrappedTitleScreenModUpdateIndicator;

public class WrappedTitleScreenModUpdateIndicatorForgeImpl implements WrappedTitleScreenModUpdateIndicator {
    private final TitleScreenModUpdateIndicator indicator;

    public WrappedTitleScreenModUpdateIndicatorForgeImpl(Button modButton) {
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
