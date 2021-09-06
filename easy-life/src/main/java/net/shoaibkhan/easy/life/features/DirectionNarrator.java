package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.utils.KeyBinds;
import net.shoaibkhan.easy.life.utils.PlayerPosition;

public class DirectionNarrator {

    private final MinecraftClient client;

    public DirectionNarrator(MinecraftClient client){
        this.client = client;
        if(KeyBinds.DIRECTION_NARRATOR_KEY.getKeyBind().wasPressed())
            main();
    }

    private void main(){
        final PlayerEntity player = client.player;
        assert player != null;
        String text = "";

        if(ClientMod.isAltPressed){
            int angle = new PlayerPosition(client).getVerticalFacingDirection();
            text = "" + angle;
        } else {
            int angle = new PlayerPosition(client).getHorizontalFacingDirectionInDegrees();

            if(ELConfig.get(ELConfig.getCardinal_to_Degrees_Key())){
                text += angle;
            } else {
                String string = new PlayerPosition(client).getHorizontalFacingDirectionInCardinal();
                text = "Facing " + string.toLowerCase();
            }
        }

        player.sendMessage(new LiteralText(text), true);
    }
}
