package net.shoaibkhan.easy.life.mixin;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.utils.NarrateLabel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * 
 * @author Shoaib Khan
 *
 */
@Mixin(WButton.class)
public abstract class ButtonMixin{

    @Shadow(remap = false)
    private Text label;

    @Inject(at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT,method = "paint",remap = false)
    private void buttonHovered(MatrixStack matrices, int x, int y, int mouseX, int mouseY, CallbackInfo info, boolean hovered,int state,float px,float buttonLeft,float buttonTop,int halfWidth,float buttonWidth,float buttonHeight,float buttonEndLeft){
        // only for 1.17 and below

//        if( state == 2 && hovered == false && !NarrateLabel.usingTab.contains("tab"+label+""+x+""+y+"")) {
//        	NarrateLabel.usingTab += "tab"+label+""+x+""+y+"";
//        	if(NarrateLabel.usingMouse.contains("mouse"+label+""+x+""+y+"")) NarrateLabel.usingMouse = NarrateLabel.usingMouse.replace("mouse"+label+""+x+""+y+"","");
//        	NarrateLabel.narrateLabel(label.getString(),x, y, "tab"+label+""+x+""+y+"");
//        } else if( hovered && !NarrateLabel.usingMouse.contains("mouse"+label+""+x+""+y+"") && !NarrateLabel.usingTab.contains("tab"+label+""+x+""+y+"") && NarrateLabel.usingTab.equals("")) {
//        	NarrateLabel.usingMouse += "mouse"+label+""+x+""+y+"";
//    		NarrateLabel.narrateLabel(label.getString(),x, y, "mouse"+label+""+x+""+y+"");
//    	}
    }
}