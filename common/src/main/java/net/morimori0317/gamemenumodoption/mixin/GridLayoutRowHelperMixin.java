package net.morimori0317.gamemenumodoption.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.morimori0317.gamemenumodoption.GMMOUtils;
import net.morimori0317.gamemenumodoption.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(GridLayout.RowHelper.class)
public class GridLayoutRowHelperMixin {
    @Inject(method = "addChild(Lnet/minecraft/client/gui/layouts/LayoutElement;I)Lnet/minecraft/client/gui/layouts/LayoutElement;", at = @At("HEAD"), cancellable = true)
    private <T extends LayoutElement> void addChildInject(T element, int p_265491_, CallbackInfoReturnable<T> cir) {
        if (ScreenHandler.FORGE_MOD_BUTTON_ADDED.get() && element instanceof Button button && GMMOUtils.isForgeModButton(button)) {
            cir.setReturnValue(element);
        }
    }
}