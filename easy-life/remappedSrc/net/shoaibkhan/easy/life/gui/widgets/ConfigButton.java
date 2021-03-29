package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.config.ELConfig;

public class ConfigButton extends WButton {
    private String translateKey;
    private String jsonKey;

    public ConfigButton(String translationKey, String jsonKey) {
        super(new TranslatableText(translationKey + (ELConfig.get(jsonKey) ? " : on" : " : off")));
        this.translateKey = translationKey;
        this.jsonKey = jsonKey;

    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){    
            boolean enabled = ELConfig.toggle(this.jsonKey);
            TranslatableText newButtonText = new TranslatableText(this.translateKey + (enabled ? " : on" : " : off"));
            this.setLabel(newButtonText);
        }
    }
}