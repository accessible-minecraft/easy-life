package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.utils.PlayerPosition;

public class PositionNarrator {
    private final MinecraftClient client;
    private final ClientPlayerEntity player;
    private final KeyBinding position_narrator;

    public PositionNarrator(MinecraftClient client, KeyBinding position_narrator){
        this.client = client;
        this.player = client.player;
        this.position_narrator = position_narrator;

        main();
    }

    private void main() {
        if (ClientMod.isAltPressed) {
            assert player != null;
            if (ClientMod.isXPressed) {
                String text = new PlayerPosition(client).getNarratableXPos();
                player.sendMessage(new LiteralText(text), true);
            }

            if (ClientMod.isCPressed) {
                String text = new PlayerPosition(client).getNarratableYPos();
                player.sendMessage(new LiteralText(text), true);
            }

            if (ClientMod.isZPressed) {
                String text = new PlayerPosition(client).getNarratableZPos();
                player.sendMessage(new LiteralText(text), true);
            }
        }

        if (position_narrator.wasPressed() && client.currentScreen == null) {
            String posX = new PlayerPosition(client).getNarratableXPos();
            String posY = new PlayerPosition(client).getNarratableYPos();
            String posZ = new PlayerPosition(client).getNarratableZPos();
            String text = String.format("%s, %s, %s", posX, posY, posZ);

            assert client.player != null;
            client.player.sendMessage(new LiteralText(text), true);
        }
    }
}
