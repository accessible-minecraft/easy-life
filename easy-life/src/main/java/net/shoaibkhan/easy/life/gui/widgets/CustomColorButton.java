package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.config.Config;

public class CustomColorButton extends WButton {
    private String translateKey;
    private String jsonKey;
    private ColorButton cButton;
    private WTextField tField;
    private SubmitColorButton sButton;

    public CustomColorButton(String translationKey, String jsonKey, ColorButton cButton, WTextField tField, SubmitColorButton sButton) {
        super(new TranslatableText(translationKey + (Config.get(jsonKey) ? " : on" : " : off")));
        this.translateKey = translationKey;
        this.jsonKey = jsonKey;
        this.cButton = cButton;
        this.tField = tField;
        this.sButton = sButton;
    }

    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            boolean enabled = Config.toggle(this.jsonKey);
//            TranslatableText newButtonText = new TranslatableText(this.translateKey + (enabled ? " : on" : " : off"));
//            this.setLabel(newButtonText);
//            if(enabled){
//                cButton.setEnabled(false);
//                tField.setEditable(true);
//                sButton.setEnabled(true);
//            } else {
//                cButton.setEnabled(true);
//                tField.setEditable(false);
//                sButton.setEnabled(false);
//            }
//        }
//    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            boolean enabled = Config.toggle(this.jsonKey);
            TranslatableText newButtonText = new TranslatableText(this.translateKey + (enabled ? " : on" : " : off"));
            this.setLabel(newButtonText);
            if(enabled){
                cButton.setEnabled(false);
                tField.setEditable(true);
                sButton.setEnabled(true);
            } else {
                cButton.setEnabled(true);
                tField.setEditable(false);
                sButton.setEnabled(false);
            }
        }
        return InputResult.PROCESSED;
    }
}