package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.shoaibkhan.easy.life.config.SerializableConfig;

public class PWConfigGui extends LightweightGuiDescription {
    private SerializableConfig tempConfig;
    private ClientPlayerEntity player;
    private MinecraftClient client;
    
    public PWConfigGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 240);
    }
    
}
