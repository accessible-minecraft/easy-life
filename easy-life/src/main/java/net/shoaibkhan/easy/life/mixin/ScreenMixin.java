package net.shoaibkhan.easy.life.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.Narration;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.screen.narration.ScreenNarrator;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Screen.class)
public class ScreenMixin {
    private NarrationMessageBuilder newBuilder = null;
    @ModifyArg(method="addScreenNarrations", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;addElementNarrations(Lnet/minecraft/client/gui/screen/narration/NarrationMessageBuilder;)V"), index = 0)
    protected NarrationMessageBuilder addBuilder(NarrationMessageBuilder builder){
        new ScreenNarrator().buildNarrations(this::getNewNarrationMessageBuilder);
        return newBuilder;
    }

    private void getNewNarrationMessageBuilder(NarrationMessageBuilder narrationMessageBuilder) {
        newBuilder = narrationMessageBuilder;
    }
}
