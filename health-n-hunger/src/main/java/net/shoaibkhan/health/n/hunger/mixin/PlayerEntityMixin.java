// package net.shoaibkhan.health.n.hunger.mixin;

// import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Inject;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// import net.minecraft.client.network.ClientPlayerEntity;
// import net.minecraft.entity.player.PlayerEntity;
// import net.minecraft.text.LiteralText;
// import net.minecraft.text.Text;


// @Mixin(ClientPlayerEntity.class)
// public class PlayerEntityMixin {

//     @Inject(at = @At("HEAD"),method = "tick")
//     private void tickUpdate(CallbackInfo info){

//     }

//     @Inject(at = @At("HEAD"),method="sendChatMessage")
//     private void onSendMessage(String message, CallbackInfo info){
//         final PlayerEntity player = (PlayerEntity)(Object) this;
//         sendMessage(new LiteralText(message), true);
//     }
    
// }
