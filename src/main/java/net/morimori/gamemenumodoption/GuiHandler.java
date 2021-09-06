package net.morimori.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fmlclient.gui.screen.ModListScreen;

public class GuiHandler {
    @SubscribeEvent
    public static void onGUI(GuiScreenEvent.InitGuiEvent.Post e) {
        if (e.getGui() instanceof PauseScreen && e.getWidgetList().size() > 0) {
            if (ModList.get().isLoaded("bettergamemenu")) return;
            boolean gmrmflag = ModList.get().isLoaded("gamemenuremovegfarb");

            Button options = (Button) e.getWidgetList().get(gmrmflag ? 3 : 5);
            Button returnToMenu = (Button) e.getWidgetList().get(gmrmflag ? 5 : 7);
            Button shareToLan = (Button) e.getWidgetList().get(gmrmflag ? 4 : 6);
            if (shareToLan != null) {
                e.addWidget(new Button(shareToLan.x, shareToLan.y + (gmrmflag ? 0 : 24), shareToLan.getWidth(), shareToLan.getHeight(), new TranslatableComponent("menu.modoption"), (n) -> Minecraft.getInstance().setScreen(new ModListScreen(e.getGui()))));
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
                for (GuiEventListener listener : e.getWidgetList()) {
                    if (listener instanceof AbstractWidget widget)
                        widget.y -= 16;
                }
            }
        }
    }
}