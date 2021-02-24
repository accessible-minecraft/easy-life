package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;

public class DoubleSubmitButton extends WButton{
    private String translationKey;
    private WTextField posx,posy;

    public DoubleSubmitButton(String translationKey,WTextField posx,WTextField posy){
        super(new LiteralText(translationKey));
        this.translationKey = translationKey;
        this.posx = posx;
        this.posy = posy;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        this.setLabel(new LiteralText(this.posx.getText()+","+this.posy.getText()));
    }
}
