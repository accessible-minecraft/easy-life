package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;

public class SubmitButton extends WButton{
    private String translationText;
    private WTextField textField;

    public SubmitButton(String translationText,WTextField textField){
        super(new LiteralText(translationText));
        this.translationText = translationText;
        this.textField = textField;
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        this.setLabel(new LiteralText(textField.getText()));
    }
}
