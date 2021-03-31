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
        if (e.getGui() instanceof IngameMenuScreen && e.getWidgetList().size() > 0) {
            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");

            Button options = (Button) e.getWidgetList().get(gmrmflag ? 3 : 5);
            Button returnToMenu = (Button) e.getWidgetList().get(gmrmflag ? 5 : 7);
            Button shareToLan = (Button) e.getWidgetList().get(gmrmflag ? 4 : 6);
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