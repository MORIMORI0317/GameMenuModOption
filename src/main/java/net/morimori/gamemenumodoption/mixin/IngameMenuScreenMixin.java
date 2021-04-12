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
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.morimori.gamemenumodoption.ClientConfig;
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

    private boolean showUpdate = ClientConfig.ShowNotificationModUpdate.get();

    private NotificationModUpdateScreen modUpdateNotification;

    protected IngameMenuScreenMixin(ITextComponent titleIn) {
        super(titleIn);
    }

    @Inject(method = "init", at = @At("TAIL"), cancellable = true)
    private void init(CallbackInfo ci) {
        if (this.isFullMenu) {
            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");
            Button options = (Button) buttons.get(gmrmflag ? 3 : 5);
            Button returnToMenu = (Button) buttons.get(gmrmflag ? 5 : 7);
            Button shareToLan = (Button) buttons.get(6);
            if (shareToLan != null) {
                shareToLan.x = width / 2 - 102;
                shareToLan.setWidth(204);
                if (gmrmflag)
                    shareToLan.y -= 24;
            }

            Button button = new Button(this.width / 2 + 4, this.height / 4 + (gmrmflag ? 96 : 120) - 16, 98, 20, new TranslationTextComponent("menu.modoption"), (n) -> Minecraft.getInstance().displayGuiScreen(new ModListScreen(this)));
            addButton(button);
            if (showUpdate)
                modUpdateNotification = init((IngameMenuScreen) (Object) this, button);

            if (!gmrmflag) {
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

    @Inject(method = "render", at = @At("TAIL"), cancellable = true)
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
}
