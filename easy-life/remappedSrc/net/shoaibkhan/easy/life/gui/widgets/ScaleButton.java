package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;

import net.shoaibkhan.easy.life.config.ELConfig;

public class ScaleButton extends WButton {
    private String translationKey;
    private String jsonKey;
    private int value;

    public ScaleButton(String translationKey, String jsonKey) {
        super(Text.of(translationKey + " : " + ELConfig.getString(jsonKey)));
        this.translationKey = translationKey;
        this.jsonKey = jsonKey;
        this.value = Integer.parseInt(ELConfig.getString(jsonKey));
    }

    @Override
    public void onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if (this.value + 1 > 5) this.value = 1;
            else this.value += 1;
            ELConfig.setInt(jsonKey, "" + value);
            this.setLabel(Text.of(this.translationKey + " : " + this.value));
        }
    }
}
