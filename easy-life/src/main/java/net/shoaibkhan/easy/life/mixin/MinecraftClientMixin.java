package net.shoaibkhan.easy.life.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.GameOptions;

@Mixin(MinecraftClient.class)
public interface MinecraftClientMixin {
    @Accessor("options")
    GameOptions getGameOptions();
}
