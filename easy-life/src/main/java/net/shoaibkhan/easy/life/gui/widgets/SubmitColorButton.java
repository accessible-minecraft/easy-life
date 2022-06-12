package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;

public class SubmitColorButton extends WButton {
    private final String jsonKey;
    private final WTextField textField;

    public SubmitColorButton(String translationText, WTextField textField, String jsonKey) {
        super(Text.of(I18n.translate(translationText)));
        this.textField = textField;
        this.jsonKey = jsonKey;
    }

//    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            String value = this.textField.getText();
//            value = value.trim().toLowerCase();
//            if(!value.isEmpty())
//            {
//                Config.setString(jsonKey, value);
//                this.textField.setText("");
//                this.textField.setSuggestion(value);
//            }
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            String value = this.textField.getText();
            value = value.trim().toLowerCase();
            if (!value.isEmpty()) {
                Config.setString(jsonKey, value);
                this.textField.setText("");
                this.textField.setSuggestion(Text.of(value));
            }
        }
        return InputResult.PROCESSED;
    }
}
