package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.gui.widgets.ScaleButton;

public class HBConfigGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;

    public HBConfigGui(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(200, 80);

        WButton doneButton = new WButton(Text.of("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 3, 7, 1);

        WLabel label = new WLabel(Text.of("Health Bar Configuration"), ClientMod.colors("red", 100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        WButton nsbutton = new WButton(Text.of("Back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 3, 7, 1);

        ScaleButton hbscale = new ScaleButton("Width", ELConfig.getHbWidth());
        root.add(hbscale, 0, 1, 6, 1);

        root.validate(this);
    }

    private void onBackClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new ConfigGui(this.player, this.client)));
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey", 50)));
    }

}
