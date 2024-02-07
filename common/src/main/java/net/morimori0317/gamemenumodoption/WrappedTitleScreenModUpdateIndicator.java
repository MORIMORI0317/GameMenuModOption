package net.morimori0317.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public interface WrappedTitleScreenModUpdateIndicator {
    void resize(Minecraft minecraft, int width, int height);

    void init();

    void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}
