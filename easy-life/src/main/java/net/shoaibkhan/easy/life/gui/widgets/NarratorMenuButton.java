package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.CustomWait;
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;

public class NarratorMenuButton extends WButton {
    private String label;
    private CustomWait wait = new CustomWait();
    public NarratorMenuButton(LiteralText literalText) {
        super(literalText);
        label = literalText.asString();
    }
    
    
    @Override
    public void onMouseMove(int x, int y) {
        super.onMouseMove(x, y);
        if( !NarratorMenuGui.focused.get(label) || !(NarratorMenuGui.curFocused.equalsIgnoreCase(label) && NarratorMenuGui.timeout>0)){
            
            NarratorManager.INSTANCE.clear();
            NarratorManager.INSTANCE.narrate(""+label+" button");
            NarratorMenuGui.focused.forEach((k,v)->{
                NarratorMenuGui.focused.replace(k, false||true, false);
            });
            NarratorMenuGui.focused.replace(label, false, true);
            NarratorMenuGui.curFocused = label;
            if(wait.isAlive()) wait.stopThread();
            wait = new CustomWait();
            wait.setWait(5000, 7, NarratorMenuGui.client);
            wait.start();
            wait.startThread();
        }
    }
    
}
