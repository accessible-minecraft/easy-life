package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.Config;


public class THDoubleSubmitButton  extends WButton{
    private String jsonkeya,jsonkeyb;
    private WTextField a,b;

    public THDoubleSubmitButton(String translationKey,WTextField a,WTextField b,String jsonkeya,String jsonkeyb){
        super(new LiteralText(translationKey));
        this.a = a;
        this.b = b;
        this.jsonkeya = jsonkeya;
        this.jsonkeyb = jsonkeyb;
    }

    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            if(a.getText().equals(""))a.setText(Config.getString(jsonkeya));
//            if(b.getText().equals(""))b.setText(Config.getString(jsonkeyb));
//            if( Config.setDouble(jsonkeya, a.getText()) && Config.setDouble(jsonkeyb, b.getText()) ) {
//                a.setText("");
//                b.setText("");
//                a.setSuggestion(Config.getString(jsonkeya));
//                b.setSuggestion(Config.getString(jsonkeyb));
//            }
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            if(a.getText().equals(""))a.setText(Config.getString(jsonkeya));
            if(b.getText().equals(""))b.setText(Config.getString(jsonkeyb));
            if( Config.setDouble(jsonkeya, a.getText()) && Config.setDouble(jsonkeyb, b.getText()) ) {
                a.setText("");
                b.setText("");
                a.setSuggestion(Config.getString(jsonkeya));
                b.setSuggestion(Config.getString(jsonkeyb));
            }
        }
        return InputResult.PROCESSED;
    }
}
