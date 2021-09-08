package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.Config;

public class DoubleSubmitButton extends WButton{
    private String jsonkeyx,jsonkeyy;
    private WTextField posx,posy;

    public DoubleSubmitButton(String translationKey,WTextField posx,WTextField posy,String jsonkeyx,String jsonkeyy){
        super(new LiteralText(translationKey));
        this.posx = posx;
        this.posy = posy;
        this.jsonkeyx = jsonkeyx;
        this.jsonkeyy = jsonkeyy;
    }

    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            if(posx.getText().equals(""))posx.setText(Config.getString(jsonkeyx));
//            if(posy.getText().equals(""))posy.setText(Config.getString(jsonkeyy));
//            if( Config.setInt(jsonkeyx, posx.getText()) && Config.setInt(jsonkeyy, posy.getText()) ) {
//                posx.setText("");
//                posy.setText("");
//                posx.setSuggestion(Config.getString(jsonkeyx));
//                posy.setSuggestion(Config.getString(jsonkeyy));
//            }
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if (posx.getText().equals("")) posx.setText(Config.getString(jsonkeyx));
            if (posy.getText().equals("")) posy.setText(Config.getString(jsonkeyy));
            if (Config.setInt(jsonkeyx, posx.getText()) && Config.setInt(jsonkeyy, posy.getText())) {
                posx.setText("");
                posy.setText("");
                posx.setSuggestion(Config.getString(jsonkeyx));
                posy.setSuggestion(Config.getString(jsonkeyy));
            }
        }
        return InputResult.PROCESSED;
    }
}
