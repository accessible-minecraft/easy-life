package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.resource.language.I18n;
import net.shoaibkhan.easy.life.Initial;

public class ConfigScreen extends CottonClientScreen {
    public ConfigScreen(GuiDescription description, String title) {
        super(description);
        Initial.narrate(I18n.translate("gui.easylife." + title));
    }
}
