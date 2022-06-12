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
import net.shoaibkhan.easy.life.gui.widgets.ConfigButton;

public class ConfigGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;

    public ConfigGui(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.client = client;
        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        root.setSize(300, 240);

        WButton doneButton = new WButton(Text.of("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 12, 7, 1);

        WLabel label = new WLabel(Text.of("Easy Life"), ClientMod.colors("red", 100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        ConfigButton nsbutton = new ConfigButton("Narrator", ELConfig.getNarratorSupportKey());
        root.add(nsbutton, 3, 12, 7, 1);


        WLabel pwlabel = new WLabel(Text.of("Player Warnings :-"), ClientMod.colors("black", 100));
        root.add(pwlabel, 1, 2, 7, 1);

        ConfigButton pwstatus = new ConfigButton("Status", ELConfig.getPlayerWarningKey());
        root.add(pwstatus, 9, 2, 6, 1);

        WButton pwmore = new WButton(Text.of("More.."));
        pwmore.setOnClick(this::pwClick);
        root.add(pwmore, 17, 2, 5, 1);


        WLabel pclabel = new WLabel(Text.of("Player Coordinates :-"), ClientMod.colors("black", 100));
        root.add(pclabel, 1, 4, 7, 1);

        ConfigButton pcstatus = new ConfigButton("Status", ELConfig.getPlayerCoordinatesKey());
        root.add(pcstatus, 9, 4, 6, 1);

        WButton pcmore = new WButton(Text.of("More.."));
        pcmore.setOnClick(this::pcClick);
        root.add(pcmore, 17, 4, 5, 1);


        WLabel pdlabel = new WLabel(Text.of("Player Direction :-"), ClientMod.colors("black", 100));
        root.add(pdlabel, 1, 6, 7, 1);

        ConfigButton pdstatus = new ConfigButton("Status", ELConfig.getPlayerDirectionKey());
        root.add(pdstatus, 9, 6, 6, 1);

        WButton pdmore = new WButton(Text.of("More.."));
        pdmore.setOnClick(this::pdClick);
        root.add(pdmore, 17, 6, 5, 1);


        WLabel hnhlabel = new WLabel(Text.of("Health n Hunger :-"), ClientMod.colors("black", 100));
        root.add(hnhlabel, 1, 8, 7, 1);

        ConfigButton hnhstatus = new ConfigButton("Status", ELConfig.getHealthNHungerKey());
        root.add(hnhstatus, 9, 8, 6, 1);

        WButton hnhmore = new WButton(Text.of("More.."));
        hnhmore.setOnClick(this::hnhClick);
        root.add(hnhmore, 17, 8, 5, 1);


        WLabel hblabel = new WLabel(Text.of("Health Bar :-"), ClientMod.colors("black", 100));
        root.add(hblabel, 1, 10, 7, 1);

        ConfigButton hbstatus = new ConfigButton("Status", ELConfig.getHealthBarKey());
        root.add(hbstatus, 9, 10, 6, 1);

        WButton hbmore = new WButton(Text.of("More.."));
        hbmore.setOnClick(this::hbClick);
        root.add(hbmore, 17, 10, 5, 1);

        root.validate(this);
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }

    private void pwClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new PWConfigGui(client.player, client)));
    }

    private void pcClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new PCConfigGui(client.player, client)));
    }

    private void pdClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new PDConfigGui(client.player, client)));
    }

    private void hnhClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new HNHConfigGui(client.player, client)));
    }

    private void hbClick() {
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new HBConfigGui(client.player, client)));
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey", 50)));
    }

}