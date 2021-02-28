package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;

public class THSubmitButton extends WButton{
    private String translationText,jsonKey;
    private WTextField textField;

    public THSubmitButton(String translationText,WTextField textField,String jsonKey){
        super(new LiteralText(translationText));
        this.translationText = translationText;
        this.textField = textField;
        this.jsonKey = jsonKey;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        String value = this.textField.getText();
        if(ELConfig.setDouble(jsonKey, value)) this.textField.setSuggestion(value);
    }
}
