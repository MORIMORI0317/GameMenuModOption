package net.morimori.gamemenumodoption.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.gui.NotificationModUpdateScreen;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.morimori.gamemenumodoption.ClientConfig;
import net.morimori.gamemenumodoption.compat.GameMenuRemoveGFARBCompat;
import net.morimori.gamemenumodoption.compat.MineTogetherCompat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IngameMenuScreen.class)
public class IngameMenuScreenMixin extends Screen {
    @Shadow
    @Final
    private boolean isFullMenu;

    private final boolean showUpdate = ClientConfig.ShowNotificationModUpdate.get();

    private NotificationModUpdateScreen modUpdateNotification;

    protected IngameMenuScreenMixin(ITextComponent titleIn) {
        super(titleIn);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        if (this.isFullMenu) {
            boolean gmrmFlag = GameMenuRemoveGFARBCompat.isCompat();
            boolean mineTFlg = MineTogetherCompat.isFixButtons();
            Button options = findButton("menu.options", gmrmFlag ? 3 : 5);
            Button returnToMenu = findButton("menu.returnToMenu", gmrmFlag ? 5 : 7);
            Button shareToLan = findButton("menu.shareToLan", 6);

            if (shareToLan != null) {
                if (!mineTFlg) {
                    shareToLan.x = width / 2 - 102;
                    shareToLan.setWidth(204);
                }
                if (gmrmFlag)
                    shareToLan.y -= 24;
            }

            Button button = new Button(mineTFlg ? width / 2 - 102 : this.width / 2 + 4, this.height / 4 + ((gmrmFlag || mineTFlg) ? 96 : 120) - 16, mineTFlg ? 204 : 98, 20, new TranslationTextComponent("menu.modoption"), (n) -> Minecraft.getInstance().displayGuiScreen(new ModListScreen(this)));
            addButton(button);
            if (showUpdate)
                modUpdateNotification = init((IngameMenuScreen) (Object) this, button);

            if (!gmrmFlag || mineTFlg) {
                if (options != null)
                    options.y += 24;
                if (returnToMenu != null)
                    returnToMenu.y += 24;
                for (Widget widget : buttons) {
                    widget.y -= 16;
                }
            }
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (this.isFullMenu) {
            if (showUpdate)
                modUpdateNotification.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }


    private static NotificationModUpdateScreen init(IngameMenuScreen gui, Button modButton) {
        NotificationModUpdateScreen notificationModUpdateScreen = new NotificationModUpdateScreen(modButton);
        notificationModUpdateScreen.resize(gui.getMinecraft(), gui.width, gui.height);
        notificationModUpdateScreen.init();
        return notificationModUpdateScreen;
    }

    private Button findButton(String name, int num) {
        for (Widget button : buttons) {
            if (button instanceof Button && button.getMessage() instanceof TranslationTextComponent) {
                TranslationTextComponent msg = (TranslationTextComponent) button.getMessage();
                if (name.equals(msg.getKey()))
                    return (Button) button;
            }
        }
        return (Button) buttons.get(num);
    }

}
