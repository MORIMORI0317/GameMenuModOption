package net.morimori0317.gmmo;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

public class GMMOUtils {
    private static final DefaultArtifactVersion EXIST_MOD_BUTTON_FORGE_VERSION = new DefaultArtifactVersion("47.1.25");
    private static final Supplier<DefaultArtifactVersion> CURRENT_FORGE_VERSION = Suppliers.memoize(() -> {
        IModFileInfo forgeMod = ModList.get().getModFileById("forge");

        if (forgeMod == null) {
            return null;
        }

        return new DefaultArtifactVersion(forgeMod.versionString());
    });

    private static final String FORGE_MOD_BUTTON_TEXT = "fml.menu.mods";

    public static boolean existModButton() {
        DefaultArtifactVersion currentForgeVersion = CURRENT_FORGE_VERSION.get();

        if (currentForgeVersion == null) {
            return false;
        }

        return currentForgeVersion.compareTo(EXIST_MOD_BUTTON_FORGE_VERSION) >= 0;
    }

    public static boolean isForgeModButton(Button button) {
        Component msg = button.getMessage();

        if (msg.getContents() instanceof TranslatableContents translatableContents) {
            return FORGE_MOD_BUTTON_TEXT.equals(translatableContents.getKey());
        }

        return false;
    }

    public static Button createForgeModButton(Screen screen, int width) {
        return Button.builder(Component.translatable("fml.menu.mods"), button -> Minecraft.getInstance().setScreen(new net.minecraftforge.client.gui.ModListScreen(screen))).width(width)
                .build();
    }
}