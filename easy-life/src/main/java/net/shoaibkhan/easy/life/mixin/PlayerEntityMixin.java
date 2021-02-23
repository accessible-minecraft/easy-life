package net.shoaibkhan.easy.life.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.Initial;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    KeyBinding kb = Initial.kb;
    @Inject(at = @At("HEAD"),method = "tick")
    private void tickUpdate(CallbackInfo info){
        if (ClientMod.kbFlag) {
            // player.sendMessage(new LiteralText("string"), false);
            ClientMod.kbFlag = false;
        }
    }
}
