package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.gui.widgets.ConfigButton;
import net.shoaibkhan.easy.life.utils.Colors;

public class ConfigGui extends LightweightGuiDescription {
    private final ClientPlayerEntity player;
    private final MinecraftClient client;

    public ConfigGui(ClientPlayerEntity player,MinecraftClient client) {
        this.player = player;
        this.client = client;
        WGridPanel root = new WGridPanel();

        setRootPanel(root);

        ConfigButton pwstatus = new ConfigButton("Player Warnings", ELConfig.getPlayerWarningKey());
        root.add(pwstatus,1, 2, 10 ,1);

        WButton pwmore = new WButton(new LiteralText("More.."));
        pwmore.setOnClick(this::pwClick);
        root.add(pwmore, 12, 2, 5, 1);

        ConfigButton pcstatus = new ConfigButton("Player Coordinates", ELConfig.getPlayerCoordinatesKey());
        root.add(pcstatus, 1, 4, 10 ,1);

        WButton pcmore = new WButton(new LiteralText("More.."));
        pcmore.setOnClick(this::pcClick);
        root.add(pcmore, 12, 4, 5, 1);

        ConfigButton pdstatus = new ConfigButton("Player Direction", ELConfig.getPlayerDirectionKey());
        root.add(pdstatus, 1, 6, 10 ,1);

        WButton pdmore = new WButton(new LiteralText("More.."));
        pdmore.setOnClick(this::pdClick);
        root.add(pdmore, 12, 6, 5, 1);

        ConfigButton hnhstatus = new ConfigButton("Health n Hunger", ELConfig.getHealthNHungerKey());
        root.add(hnhstatus, 1, 8, 10 ,1);

        ConfigButton nsbutton = new ConfigButton("Narrator",ELConfig.getNarratorSupportKey());
        root.add(nsbutton, 3, 10, 7, 1);

        ConfigButton hinButton = new ConfigButton("Hotbar Item Narrator",ELConfig.getHelditemnarratorkey());
        root.add(hinButton, 10, 10, 10, 1);

        WButton doneButton = new WButton(new LiteralText("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 7, 12, 7, 1);

        WLabel label = new WLabel(new LiteralText("Easy Life"), Colors.colors("red",100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        root.validate(this);
}

    private void onDoneClick() {
        this.player.closeScreen();
    }

    private void pwClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PWConfigGui(client.player,client),"Player Warnings" , player);
        this.client.openScreen(screen);
    }

    private void pcClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PCConfigGui(client.player,client), "Player Coordinates", player);
        this.client.openScreen(screen);
    }

    private void pdClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PDConfigGui(client.player,client), "Player Directions", player);
        this.client.openScreen(screen);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey",50)));
    }

}