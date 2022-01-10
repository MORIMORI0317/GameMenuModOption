package net.morimori.gamemenumodoption.mixin;

import net.creeperhost.minetogether.module.connect.ConnectModule;
import net.minecraft.client.gui.widget.button.Button;
import net.morimori.gamemenumodoption.compat.MineTogetherCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ConnectModule.class)
public class ConnectModuleMixin {
    @ModifyVariable(method = "onScreenOpen", ordinal = 0, at = @At("STORE"), remap = false)
    private static Button onScreenOpen(Button guiButton) {
        if (MineTogetherCompat.isCompat())
            guiButton.y -= 16;
        return guiButton;
    }
}
