package net.morimori0317.gamemenumodoption;

import net.minecraft.client.gui.components.Button;

public interface GMMOPauseScreen {
    Button getModOptionButton();

    boolean isShowPauseMenu();
}