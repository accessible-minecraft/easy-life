package net.shoaibkhan.easy.life.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessorMixin {
    @Accessor(value = "SCREEN_USAGE_TEXT")
    public static Text getSCREEN_USAGE_TEXT(){
        throw new AssertionError();
    }
}
