package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.util.NarratorManager;

import net.shoaibkhan.easy.life.CustomWait;
import net.shoaibkhan.easy.life.NarratorPlus;

public class NarratorMenuButton extends WButton {
    CustomWait wait = new CustomWait();
    private boolean mouseOver = false;
    private String label;

    public NarratorMenuButton(LiteralText literalText) {
        super(literalText);
        label = literalText.asString();
    }


    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
        if (!mouseOver) {
            NarratorManager.INSTANCE.clear();
            NarratorManager.INSTANCE.narrate("" + label);
            mouseOver = true;
        }
    }
}
