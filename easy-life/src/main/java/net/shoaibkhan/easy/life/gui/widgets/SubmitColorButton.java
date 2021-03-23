package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;

public class SubmitColorButton extends WButton{
    private String jsonKey;
    private WTextField textField;

    public SubmitColorButton(String translationText,WTextField textField,String jsonKey){
        super(new LiteralText(translationText));
        this.textField = textField;
        this.jsonKey = jsonKey;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            String value = this.textField.getText();
            value = value.trim().toLowerCase();
            if(!value.equals(""))
            {
                ELConfig.setString(jsonKey, value);
                this.textField.setText("");
                this.textField.setSuggestion(value);
            }
        }
    }
}
