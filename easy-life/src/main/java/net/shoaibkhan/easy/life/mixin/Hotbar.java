package net.shoaibkhan.easy.life.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.shoaibkhan.easy.life.config.ELConfig;

@Mixin(InGameHud.class)
public class Hotbar {
	
	@Shadow(remap = false)
	private int heldItemTooltipFade;
	
	@Shadow(remap = false)
	private ItemStack currentStack;
	
	@Inject(at=@At("TAIL") ,method="renderHeldItemTooltip",remap = false)
	public void renderHeldItemTooltipMixin(MatrixStack matrixStack,CallbackInfo callbackInfo) {
		if (this.heldItemTooltipFade == 35 && !this.currentStack.isEmpty() && ELConfig.get(ELConfig.getHelditemnarratorkey())) {
			MutableText mutableText = (new LiteralText("")).append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
			MinecraftClient.getInstance().player.sendMessage(new LiteralText(mutableText.getString()), true);
		}
	}
}
