package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.config.Config;
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

            if(Config.get(Config.getCardinal_to_Degrees_Key())){
                text += angle;
            } else {
              String string = new PlayerPosition(client).getHorizontalFacingDirectionInCardinal();
              text = I18n.translate("narrate.easylife.direction", string);
            }
        }

        Initial.narrate(text);
    }
}
