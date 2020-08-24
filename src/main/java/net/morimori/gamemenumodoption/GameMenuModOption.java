package net.morimori.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.gui.screen.ModListScreen;
import net.minecraftforge.fml.common.Mod;

@Mod("gamemenumodoption")
public class GameMenuModOption {

    public GameMenuModOption() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGUI(GuiScreenEvent.InitGuiEvent.Post e) {

        if (e.getGui() instanceof IngameMenuScreen) {

            e.addWidget(new Button(e.getWidgetList().get(6).field_230690_l_, e.getWidgetList().get(6).field_230691_m_ + 24,
                    e.getWidgetList().get(6).func_230998_h_(), e.getWidgetList().get(6).func_238483_d_(),
                    new StringTextComponent(I18n.format("menu.modoption")),
                    (n) -> {
                        Minecraft.getInstance().displayGuiScreen(new ModListScreen(e.getGui()));
                    }));

            e.getWidgetList().get(6).field_230690_l_ = e.getGui().field_230708_k_ / 2 - 102;
            e.getWidgetList().get(6).func_230991_b_(204);

            e.getWidgetList().get(7).field_230691_m_ += 24;

            e.getWidgetList().get(5).field_230691_m_ += 24;

            for (Widget in : e.getWidgetList()) {
                in.field_230691_m_ -= 16;
            }
        }

    }
}
