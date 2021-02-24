package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;


public class THDoubleSubmitButton  extends WButton{
    private String translationKey,jsonkeya,jsonkeyb;
    private WTextField a,b;

    public THDoubleSubmitButton(String translationKey,WTextField a,WTextField b,String jsonkeya,String jsonkeyb){
        super(new LiteralText(translationKey));
        this.translationKey = translationKey;
        this.a = a;
        this.b = b;
        this.jsonkeya = jsonkeya;
        this.jsonkeyb = jsonkeyb;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if( ELConfig.setDouble(jsonkeya, a.getText()) && ELConfig.setDouble(jsonkeyb, b.getText()) ) {
            a.setText("");
            b.setText("");
            a.setSuggestion(ELConfig.getString(jsonkeya));
            b.setSuggestion(ELConfig.getString(jsonkeyb));
        }
    }
}
