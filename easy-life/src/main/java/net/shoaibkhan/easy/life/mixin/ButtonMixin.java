package net.shoaibkhan.easy.life.mixin;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.Initial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WButton.class)
public abstract class ButtonMixin{

    @Shadow(remap = false)
    private Text label;

    @Inject(at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILSOFT,method = "paint",remap = false)
    private void buttonHovered(MatrixStack matrices, int x, int y, int mouseX, int mouseY, CallbackInfo info, boolean hovered,int state,float px,float buttonLeft,float buttonTop,int halfWidth,float buttonWidth,float buttonHeight,float buttonEndLeft){
        if(state == 2) Initial.narrateLabel(label.getString(),x, y);

    }
}