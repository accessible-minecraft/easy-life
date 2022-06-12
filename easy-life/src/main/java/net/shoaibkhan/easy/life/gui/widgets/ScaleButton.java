package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;

import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;

public class ScaleButton extends WButton {
    private final String translationKey;
    private final String jsonKey;
    private int value;

    public ScaleButton(String translationKey, String jsonKey) {
        super(Text.of(translationKey + " : " + Config.getString(jsonKey)));
        this.translationKey = translationKey;
        this.jsonKey = jsonKey;
        this.value = Integer.parseInt(Config.getString(jsonKey));
    }

    //    // pre 1.17
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if (this.isEnabled()) {
//            if (this.value + 1 > 5) this.value = 1;
//            else this.value += 1;
//            Config.setInt(jsonKey, "" + value);
//            this.setLabel(Text.of(this.translationKey + " : " + this.value));
//        }
//    }

    // post 1.17
    @Override
    public io.github.cottonmc.cotton.gui.widget.data.InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if (this.value + 1 > 5) this.value = 1;
            else this.value += 1;
            Config.setInt(jsonKey, "" + value);
            this.setLabel(Text.of(this.translationKey + " : " + this.value));
        }
        return io.github.cottonmc.cotton.gui.widget.data.InputResult.PROCESSED;
    }
}
