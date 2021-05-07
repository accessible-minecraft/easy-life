package net.shoaibkhan.easy.life.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


@Mixin(ItemEntity.class)
public abstract class ItemPickedUpClass {

	@Shadow(remap = false)
    private int pickupDelay;
	
	@Shadow(remap = false)
	private UUID owner;
	
	@Shadow(remap = false)
	public abstract ItemStack getStack();
	
	@Inject(at = @At("TAIL"),method = "onPlayerCollision",remap = false)
	public void onPlayerCollisionMixin(PlayerEntity player, CallbackInfo callbackInfo) {
		if(MinecraftClient.getInstance().isRunning()) {
	        try { 
			ItemStack itemStack = this.getStack();
	         Item item = itemStack.getItem();
	         if (this.pickupDelay == 0 && (this.owner == null || this.owner.equals(player.getUuid())) && player.inventory.insertStack(itemStack)) {
	        	 System.out.println("picked up"+item.getTranslationKey());
	        	 System.out.println("picked up"+item.getName());
	         }
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
	}
}
