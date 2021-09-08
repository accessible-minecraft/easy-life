package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.Config;

public class ScaleButton extends WButton {
    private String translationKey;
    private String jsonKey;
    private int value;

    public ScaleButton(String translationKey,String jsonKey){
        super(new LiteralText(translationKey + " : " + Config.getString(jsonKey)));
        this.translationKey = translationKey;
        this.jsonKey = jsonKey;
        this.value = Integer.parseInt(Config.getString(jsonKey));
    }

    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            if(this.value+1>5) this.value = 1;
//            else this.value += 1;
//            Config.setInt(jsonKey, ""+value);
//            this.setLabel(new LiteralText(this.translationKey + " : " + this.value));
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            if(this.value+1>5) this.value = 1;
            else this.value += 1;
            Config.setInt(jsonKey, ""+value);
            this.setLabel(new LiteralText(this.translationKey + " : " + this.value));
        }
        return InputResult.PROCESSED;
    }
}
