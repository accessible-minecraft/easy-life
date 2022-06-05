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
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.config.Config;
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

        ConfigButton playerWarnings = new ConfigButton("playerWarnings", Config.getPlayerWarningKey());
        root.add(playerWarnings,1, 3, 11 ,1);

        ConfigButton hotbarItemNarrator = new ConfigButton("hotbarItem", Config.getHelditemnarratorkey());
        root.add(hotbarItemNarrator, 13, 3, 11, 1);

        ConfigButton biomeIndicator = new ConfigButton("biomeIndicator", Config.getBiome_Indicator_Key());
        root.add(biomeIndicator, 1, 5, 11, 1);

        ConfigButton cardinalToDegrees = new ConfigButton("cardinalToDegrees", Config.getCardinal_to_Degrees_Key());
        root.add(cardinalToDegrees, 13,5, 11, 1);

        ConfigButton replaceYtoZ = new ConfigButton("replaceYtoZ", Config.getReplace_y_to_z_key());
        root.add(replaceYtoZ, 1, 7, 11, 1);

        WButton playerWarningsSettings = new WButton(new TranslatableText("gui.easylife.playerWarningsSettings"));
        playerWarningsSettings.setOnClick(this::pwClick);
        root.add(playerWarningsSettings, 13, 7, 11, 1);

        ConfigButton playerCoordinates = new ConfigButton("playerCoordinatesOverlay", Config.getPlayerCoordinatesKey());
        root.add(playerCoordinates, 1, 9, 11,1);

        ConfigButton playerDirection = new ConfigButton("playerDirectionOverlay", Config.getPlayerDirectionKey());
        root.add(playerDirection, 13, 9, 11 ,1);

        WButton playerCoordinatesOverlaySettings = new WButton(new TranslatableText("gui.easylife.playerCoordinatesOverlaySettings"));
        playerCoordinatesOverlaySettings.setOnClick(this::pcClick);
        root.add(playerCoordinatesOverlaySettings, 1, 11, 11, 1);

        WButton playerDirectionOverlaySettings = new WButton(new TranslatableText("gui.easylife.playerDirectionOverlaySettings"));
        playerDirectionOverlaySettings.setOnClick(this::pdClick);
        root.add(playerDirectionOverlaySettings, 13, 11, 11, 1);

        WButton doneButton = new WButton(new TranslatableText("gui.easylife.done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 7, 13, 7, 1);

        WLabel labelForPadding = new WLabel(LiteralText.EMPTY, Colors.colors("red",100));
        root.add(labelForPadding, 0, 14, 25, 1);

        WLabel label = new WLabel(new TranslatableText("gui.easylife"), Colors.colors("red",100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 1, 25, 1);

        root.validate(this);
}

    private void onDoneClick() {
        this.player.closeScreen();
    }

    private void pwClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PWConfigGui(client.player,client),"playerWarnings" );
        this.client.setScreen(screen);
    }

    private void pcClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PCConfigGui(client.player,client), "playerCoordinates");
        this.client.setScreen(screen);
    }

    private void pdClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new PDConfigGui(client.player,client), "playerDirections");
        this.client.setScreen(screen);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey",50)));
    }

}