package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.client.resource.language.I18n;

import net.minecraft.text.Text;

import net.shoaibkhan.easy.life.config.Config;

public class ConfigButton extends WButton {
    private final String translateKey;
    private final String jsonKey;

    public ConfigButton(String translationKey, String jsonKey) {
        super(getTitle(translationKey, Config.get(jsonKey)));
        this.translateKey = translationKey;
        this.jsonKey = jsonKey;
    }

//    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            boolean enabled = Config.toggle(this.jsonKey);
//            TranslatableText newButtonText = I18n.translate(this.translateKey + (enabled ? " : on" : " : off"));
//            this.setLabel(newButtonText);
//        }
//    }

    private static Text getTitle(String key, boolean enabled) {
        String s = I18n.translate("gui.easylife." + (enabled ? "on" : "off"));
        return Text.of(I18n.translate("gui.easylife." + key) + s);
    }

    // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            boolean enabled = Config.toggle(this.jsonKey);
            this.setLabel(getTitle(translateKey, enabled));
        }
        return InputResult.PROCESSED;
    }
}