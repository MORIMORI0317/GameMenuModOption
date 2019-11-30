package com.morimori.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("gamemenumodoption")
public class GameMenuModOption {

	public GameMenuModOption() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onGUI(GuiScreenEvent.InitGuiEvent.Post e) {

		if (e.getGui() instanceof IngameMenuScreen) {

			e.addWidget(new Button(e.getWidgetList().get(6).x, e.getWidgetList().get(6).y + 24,
					e.getWidgetList().get(6).getWidth(), e.getWidgetList().get(6).getHeight(),
					I18n.format("menu.modoption"),
					(p_213055_1_) -> {
						Minecraft.getInstance()
								.displayGuiScreen(new net.minecraftforge.fml.client.gui.GuiModList(e.getGui()));

					}));

			e.getWidgetList().get(6).x = e.getGui().width / 2 - 102;
			e.getWidgetList().get(6).setWidth(204);

			e.getWidgetList().get(7).y += 24;

			e.getWidgetList().get(5).y += 24;

		}

	}
}
