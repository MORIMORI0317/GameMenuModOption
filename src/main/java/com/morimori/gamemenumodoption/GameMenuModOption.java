package com.morimori.gamemenumodoption;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
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

		if (e.getGui() instanceof GuiIngameMenu) {

			e.addButton(new GuiButton(7, e.getGui().width / 2 + 2, e.getGui().height / 4 + 96 + -16, 98, 20,
					I18n.format("menu.modoption")) {

				public void onClick(double mouseX, double mouseY) {
					Minecraft.getInstance()
							.displayGuiScreen(new net.minecraftforge.fml.client.gui.GuiModList(e.getGui()));
				}
			});

			e.getButtonList().get(3).x = e.getGui().width / 2 - 100;
			e.getButtonList().get(3).setWidth(200);
			e.getButtonList().get(3).y -= 24;

		}

	}
}
