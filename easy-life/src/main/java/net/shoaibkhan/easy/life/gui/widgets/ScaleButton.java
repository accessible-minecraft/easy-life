package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.LiteralText;

public class ScaleButton extends WButton {
    private String translationKey;
    private int value;

    public ScaleButton(String translationKey,int value){
        super(new LiteralText(translationKey + " : " + value));
        this.translationKey = translationKey;
        this.value = value;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.value+1>5) this.value = 1;
        else this.value += 1;
        this.setLabel(new LiteralText(this.translationKey + " : " + this.value));
    }
}
