package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.utils.KeyBinds;
import net.shoaibkhan.easy.life.utils.PlayerPosition;

public class PositionNarrator {
    private final MinecraftClient client;
    private final ClientPlayerEntity player;

    public PositionNarrator(MinecraftClient client){
        this.client = client;
        this.player = client.player;

        main();
    }

    private void main() {
        if (ClientMod.isAltPressed) {
            assert player != null;
            if (ClientMod.isXPressed) {
                String text = new PlayerPosition(client).getNarratableXPos();
                Initial.narrate(text);
            }

            if (ClientMod.isCPressed) {
                String text = new PlayerPosition(client).getNarratableYPos();
                Initial.narrate(text);
            }

            if (ClientMod.isZPressed) {
                String text = new PlayerPosition(client).getNarratableZPos();
                Initial.narrate(text);
            }
        }

        if (KeyBinds.POSITION_NARRATOR_KEY.getKeyBind().wasPressed() && client.currentScreen == null) {
            String posX = new PlayerPosition(client).getNarratableXPos();
            String posY = new PlayerPosition(client).getNarratableYPos();
            String posZ = new PlayerPosition(client).getNarratableZPos();
            String text = String.format("%s, %s, %s", posX, posY, posZ);

            assert client.player != null;
            Initial.narrate(text);
        }
    }
}
