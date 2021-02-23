package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.config.SerializableConfig;
import net.shoaibkhan.easy.life.gui.widgets.ConfigButton;

public class ConfigGui extends LightweightGuiDescription {
    private SerializableConfig tempConfig;
    private ClientPlayerEntity player;

    public ConfigGui(ClientPlayerEntity player) {
        this.player = player;
        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        root.setSize(240, 240);

        ConfigButton Health_n_Hunger_Button = new ConfigButton("Health n Hunger",ELConfig.Health_n_Hunger_Key);
        root.add(Health_n_Hunger_Button, 0, 1, 10, 1);

        ConfigButton Player_Coordinates_Button = new ConfigButton("Player Coordinates",ELConfig.Player_Coordinates_Key);
        root.add(Player_Coordinates_Button, 11, 1, 10, 1);

        ConfigButton Player_Direction_Button = new ConfigButton("Player Direction",ELConfig.Player_Direction_Key);
        root.add(Player_Direction_Button, 11, 3, 10, 1);

        ConfigButton Player_Warning_Button = new ConfigButton("Player Warning",ELConfig.Player_Warning_Key);
        root.add(Player_Warning_Button, 0, 2, 10, 1);

        ConfigButton Health_Bar_Key_Button = new ConfigButton("Health Bar", ELConfig.Health_Bar_Key);
        root.add(Health_Bar_Key_Button, 11, 2, 10, 1);

        WButton doneButton = new WButton(new TranslatableText("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 7, 8, 7, 1);

        WLabel label = new WLabel(new TranslatableText("Easy Life"), ClientMod.colors("black"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        root.validate(this);
    }

    private void onDoneClick() {
        this.player.closeScreen();

    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("grey")));
    }

}