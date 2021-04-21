package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;

public class ConfigScreen extends CottonClientScreen {
    public ConfigScreen(GuiDescription description, String title, ClientPlayerEntity playerEntity) {
        super(description);
        if(ELConfig.get(ELConfig.getNarratorSupportKey())) playerEntity.sendMessage(new LiteralText(title), true);
    }
}
