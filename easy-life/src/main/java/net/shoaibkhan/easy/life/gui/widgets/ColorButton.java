package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.config.ELConfig;

public class ColorButton extends WButton {
    private String translationKey,jsonKey;
    private int index;
    public ColorButton(String translationKey,String jsonKey){
        super(new LiteralText(translationKey +" : "+ ELConfig.getString(jsonKey)));
        this.translationKey = translationKey;
        this.jsonKey = jsonKey;

        for (int i = 0; i < ClientMod.colorNames.length; i++) {
            if(ClientMod.colorNames[i].equalsIgnoreCase(ELConfig.getString(jsonKey))){
                this.index = i;
                break;
            }
        }
    }

    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            if((this.index+1)==ClientMod.colorNames.length) index = 0;
//            else this.index+=1;
//            ELConfig.setString(jsonKey, ClientMod.colorNames[index]);
//            this.setLabel(new LiteralText(translationKey +" : "+ ClientMod.colorNames[index]));
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            if((this.index+1)==ClientMod.colorNames.length) index = 0;
            else this.index+=1;
            ELConfig.setString(jsonKey, ClientMod.colorNames[index]);
            this.setLabel(new LiteralText(translationKey +" : "+ ClientMod.colorNames[index]));
        }
        return InputResult.PROCESSED;
    }
}
