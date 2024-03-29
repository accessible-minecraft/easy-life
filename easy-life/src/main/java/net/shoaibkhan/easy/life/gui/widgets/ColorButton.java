package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;

import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.utils.Colors;

public class ColorButton extends WButton {
    private final String translationKey, jsonKey;
    private int index;

    public ColorButton(String translationKey, String jsonKey) {
        super(Text.of(translationKey + " : " + Config.getString(jsonKey)));
        this.translationKey = translationKey;
        this.jsonKey = jsonKey;

        for (int i = 0; i < Colors.colorNames.length; i++) {
            if (Colors.colorNames[i].equalsIgnoreCase(Config.getString(jsonKey))) {
                this.index = i;
                break;
            }
        }
    }

    //    // pre 1.17
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if (this.isEnabled()) {
//            if ((this.index + 1) == Colors.colorNames.length) index = 0;
//            else this.index += 1;
//            Config.setString(jsonKey, Colors.colorNames[index]);
//            this.setLabel(Text.of(translationKey + " : " + Colors.colorNames[index]));
//        }
//    }

    // post 1.17
    @Override
    public io.github.cottonmc.cotton.gui.widget.data.InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if (this.isEnabled()) {
            if ((this.index + 1) == Colors.colorNames.length) index = 0;
            else this.index += 1;
            Config.setString(jsonKey, Colors.colorNames[index]);
            this.setLabel(Text.of(translationKey + " : " + Colors.colorNames[index]));
        }
        return io.github.cottonmc.cotton.gui.widget.data.InputResult.PROCESSED;
    }
}
