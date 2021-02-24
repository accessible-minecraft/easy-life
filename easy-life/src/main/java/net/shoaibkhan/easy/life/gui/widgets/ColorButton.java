package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.ClientMod;

public class ColorButton extends WButton {
    private String translationKey,jsonKey;
    private int index;
    public ColorButton(String translationKey, int index, String jsonKey){
        super(new LiteralText(translationKey +" : "+ ClientMod.colorNames[index]));
        this.translationKey = translationKey;
        this.index = index;
        this.jsonKey = jsonKey;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if((this.index+1)==ClientMod.colorNames.length) index = 0;
        else this.index+=1;
        
        this.setLabel(new LiteralText(translationKey +" : "+ ClientMod.colorNames[index]));
    }
}
