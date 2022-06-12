package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;

import net.shoaibkhan.easy.life.config.ELConfig;

public class SubmitButton extends WButton {
    private String jsonKey;
    private WTextField textField;

    public SubmitButton(String translationText, WTextField textField, String jsonKey) {
        super(Text.of(translationText));
        this.textField = textField;
        this.jsonKey = jsonKey;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            String value = this.textField.getText();
            if (ELConfig.setInt(jsonKey, value)) {
                this.textField.setText("");
                this.textField.setSuggestion(value);
            }
        }
    }
}
