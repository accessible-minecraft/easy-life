package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;

public class DoubleSubmitButton extends WButton{
    private String translationKey,jsonkeyx,jsonkeyy;
    private WTextField posx,posy;

    public DoubleSubmitButton(String translationKey,WTextField posx,WTextField posy,String jsonkeyx,String jsonkeyy){
        super(new LiteralText(translationKey));
        this.translationKey = translationKey;
        this.posx = posx;
        this.posy = posy;
        this.jsonkeyx = jsonkeyx;
        this.jsonkeyy = jsonkeyy;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(posx.getText().equals(""))posx.setText(ELConfig.getString(jsonkeyx));
        if(posy.getText().equals(""))posy.setText(ELConfig.getString(jsonkeyy));
        if( ELConfig.setInt(jsonkeyx, posx.getText()) && ELConfig.setInt(jsonkeyy, posy.getText()) ) {
            posx.setText("");
            posy.setText("");
            posx.setSuggestion(ELConfig.getString(jsonkeyx));
            posy.setSuggestion(ELConfig.getString(jsonkeyy));
        }
    }
}
