package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;


public class THDoubleSubmitButton extends WButton {
    private final String jsonkeya;
    private final String jsonkeyb;
    private final WTextField a;
    private final WTextField b;

    public THDoubleSubmitButton(String translationKey, WTextField a, WTextField b, String jsonkeya, String jsonkeyb) {
        super(Text.of(I18n.translate(translationKey)));
        this.a = a;
        this.b = b;
        this.jsonkeya = jsonkeya;
        this.jsonkeyb = jsonkeyb;
    }

    //    // pre 1.17
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if (this.isEnabled()) {
//            if (a.getText().isEmpty()) a.setText(Config.getString(jsonkeya));
//            if (b.getText().isEmpty()) b.setText(Config.getString(jsonkeyb));
//            if (Config.setDouble(jsonkeya, a.getText()) && Config.setDouble(jsonkeyb, b.getText())) {
//                a.setText("");
//                b.setText("");
//                a.setSuggestion(Config.getString(jsonkeya));
//                b.setSuggestion(Config.getString(jsonkeyb));
//            }
//        }
//    }

    // post 1.17
    @Override
    public io.github.cottonmc.cotton.gui.widget.data.InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if (a.getText().isEmpty()) a.setText(Config.getString(jsonkeya));
            if (b.getText().isEmpty()) b.setText(Config.getString(jsonkeyb));
            if (Config.setDouble(jsonkeya, a.getText()) && Config.setDouble(jsonkeyb, b.getText())) {
                a.setText("");
                b.setText("");
                a.setSuggestion(Text.of(Config.getString(jsonkeya)));
                b.setSuggestion(Text.of(Config.getString(jsonkeyb)));
            }
        }
        return io.github.cottonmc.cotton.gui.widget.data.InputResult.PROCESSED;
    }
}
