package net.morimori0317.gamemenumodoption;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

/**
 * A button that deceives the message because other mods may rely on the message to find the mod button.
 */
public class ModMenuButton extends Button {
    private static final Component MOD_OPTION = Component.translatable("menu.modoption");

    public ModMenuButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, Button.DEFAULT_NARRATION);
    }

    @Override
    protected void renderScrollingString(GuiGraphics guiGraphics, Font font, int i, int j) {
        if (GameMenuModOption.getConfig().overwriteModButtonText()) {
            int k = this.getX() + i;
            int l = this.getX() + this.getWidth() - i;
            renderScrollingString(guiGraphics, font, MOD_OPTION, k, this.getY(), l, this.getY() + this.getHeight(), j);
        } else {
            super.renderScrollingString(guiGraphics, font, i, j);
        }
    }

    @Override
    protected @NotNull MutableComponent createNarrationMessage() {
        if (GameMenuModOption.getConfig().overwriteModButtonText()) {
            return wrapDefaultNarrationMessage(MOD_OPTION);
        } else {
            return super.createNarrationMessage();
        }
    }
}
