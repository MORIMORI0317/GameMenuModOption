package net.morimori.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;

public class GuiHandler {
    @SubscribeEvent
    public static void onGUI(GuiScreenEvent.InitGuiEvent.Post e) {
        if (e.getGui() instanceof IngameMenuScreen) {

            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");

            Button options = (Button) e.getWidgetList().stream().filter(n -> {
                if (n instanceof Button) {
                    if (n.getMessage() instanceof TranslationTextComponent) {
                        return ((TranslationTextComponent) n.getMessage()).getKey().equals("menu.options");
                    }
                }
                return false;
            }).findAny().orElseGet(null);

            Button returnToMenu = (Button) e.getWidgetList().stream().filter(n -> {
                if (n instanceof Button) {
                    if (n.getMessage() instanceof TranslationTextComponent) {
                        return ((TranslationTextComponent) n.getMessage()).getKey().equals("menu.returnToMenu") || ((TranslationTextComponent) n.getMessage()).getKey().equals("menu.disconnect");
                    }
                }
                return false;
            }).findAny().orElseGet(null);

            Button shareToLan = (Button) e.getWidgetList().stream().filter(n -> {
                if (n instanceof Button) {
                    if (n.getMessage() instanceof TranslationTextComponent) {
                        return ((TranslationTextComponent) n.getMessage()).getKey().equals("menu.shareToLan");
                    }
                }
                return false;
            }).findAny().orElseGet(null);


            if (shareToLan != null) {
                e.addWidget(new Button(shareToLan.x, shareToLan.y + (gmrmflag ? 0 : 24), shareToLan.getWidth(), shareToLan.getHeightRealms(), new TranslationTextComponent("menu.modoption"), (n) -> Minecraft.getInstance().displayGuiScreen(new ModListScreen(e.getGui()))));
                shareToLan.x = e.getGui().width / 2 - 102;
                shareToLan.setWidth(204);
                if (gmrmflag)
                    shareToLan.y -= 24;
            }

            if (!gmrmflag) {
                if (options != null)
                    options.y += 24;
                if (returnToMenu != null)
                    returnToMenu.y += 24;
                for (Widget widget : e.getWidgetList()) {
                    widget.y -= 16;
                }
            }
        }
    }
}
