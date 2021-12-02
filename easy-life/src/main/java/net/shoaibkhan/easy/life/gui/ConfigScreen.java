package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.Initial;

public class ConfigScreen extends CottonClientScreen {
    public ConfigScreen(GuiDescription description, String title, ClientPlayerEntity playerEntity) {
        super(description);
        Initial.narrate(title);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
