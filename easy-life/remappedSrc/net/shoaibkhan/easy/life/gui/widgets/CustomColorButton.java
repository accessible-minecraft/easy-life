package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;

import net.shoaibkhan.easy.life.config.ELConfig;

public class CustomColorButton extends WButton {
    private String translateKey;
    private String jsonKey;
    private ColorButton cButton;
    private WTextField tField;
    private SubmitColorButton sButton;

    public CustomColorButton(String translationKey, String jsonKey, ColorButton cButton, WTextField tField, SubmitColorButton sButton) {
        super(I18n.translate(translationKey + (ELConfig.get(jsonKey) ? " : on" : " : off")));
        this.translateKey = translationKey;
        this.jsonKey = jsonKey;
        this.cButton = cButton;
        this.tField = tField;
        this.sButton = sButton;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            boolean enabled = ELConfig.toggle(this.jsonKey);
            TranslatableText newButtonText = I18n.translate(this.translateKey + (enabled ? " : on" : " : off"));
            this.setLabel(newButtonText);
            if (enabled) {
                cButton.setEnabled(false);
                tField.setEditable(true);
                sButton.setEnabled(true);
            } else {
                cButton.setEnabled(true);
                tField.setEditable(false);
                sButton.setEnabled(false);
            }
        }
    }
}