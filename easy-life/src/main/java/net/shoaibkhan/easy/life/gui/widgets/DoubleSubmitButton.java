package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;

public class DoubleSubmitButton extends WButton {
    private final String jsonkeyx;
    private final String jsonkeyy;
    private final WTextField posx;
    private final WTextField posy;

    public DoubleSubmitButton(String translationKey, WTextField posx, WTextField posy, String jsonkeyx, String jsonkeyy) {
        super(Text.of(I18n.translate(translationKey)));
        this.posx = posx;
        this.posy = posy;
        this.jsonkeyx = jsonkeyx;
        this.jsonkeyy = jsonkeyy;
    }

    //    // pre 1.17
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if (this.isEnabled()) {
//            if (posx.getText().equals("")) posx.setText(Config.getString(jsonkeyx));
//            if (posy.getText().equals("")) posy.setText(Config.getString(jsonkeyy));
//            if (Config.setInt(jsonkeyx, posx.getText()) && Config.setInt(jsonkeyy, posy.getText())) {
//                posx.setText("");
//                posy.setText("");
//                posx.setSuggestion(Config.getString(jsonkeyx));
//                posy.setSuggestion(Config.getString(jsonkeyy));
//            }
//        }
//    }

    // post 1.17
    @Override
    public io.github.cottonmc.cotton.gui.widget.data.InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if (posx.getText().equals("")) posx.setText(Config.getString(jsonkeyx));
            if (posy.getText().equals("")) posy.setText(Config.getString(jsonkeyy));
            if (Config.setInt(jsonkeyx, posx.getText()) && Config.setInt(jsonkeyy, posy.getText())) {
                posx.setText("");
                posy.setText("");
                posx.setSuggestion(Text.of(Config.getString(jsonkeyx)));
                posy.setSuggestion(Text.of(Config.getString(jsonkeyy)));
            }
        }
        return io.github.cottonmc.cotton.gui.widget.data.InputResult.PROCESSED;
    }
}
