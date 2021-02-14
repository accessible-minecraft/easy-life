package net.shoaibkhan.health.n.hunger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

@Mixin(PlayerEntity.class)
public class Health {
    PlayerEntity player = (PlayerEntity)(Object)this;

    @Inject(at = @At("RETURN"), method = "damage")
    private void onDamage(DamageSource source, float amount,CallbackInfoReturnable info){
        double health = player.getHealth();
        if(health <= 12.0 & health > 6.0){
            player.sendSystemMessage(new LiteralText("Health is less than 60 percent"), Util.NIL_UUID);
        } else if (health <= 6.0 & health > 0){
            player.sendSystemMessage(new LiteralText("Health is less than 30 percent"), Util.NIL_UUID);
        }
    }

    
}
