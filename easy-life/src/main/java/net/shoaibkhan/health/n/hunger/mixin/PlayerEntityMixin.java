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

//     @Inject(at = @At("HEAD"),method="sendChatMessage")
//     private void onSendMessage(Text message, boolean actionBar, CallbackInfo info){
//         final PlayerEntity player = (PlayerEntity)(Object) this;
//         // player.sendMessage(new LiteralText(message.asString()), true);
//         System.out.println("\n\n\n\t\t"+message);
//         System.out.println("\n\n\n\t\t"+message);
//         System.out.println("\n\n\n\t\t"+message);
//         System.out.println("\n\n\n\t\t"+message);
//     }
    
// }
