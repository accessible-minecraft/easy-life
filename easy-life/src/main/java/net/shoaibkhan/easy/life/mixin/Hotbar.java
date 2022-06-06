package net.shoaibkhan.easy.life.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.config.Config;

@Mixin(InGameHud.class)
public class Hotbar {
	
	@Shadow
	private int heldItemTooltipFade;
	
	@Shadow
	private ItemStack currentStack;
	
	@Inject(at=@At("TAIL") ,method="renderHeldItemTooltip")
	public void renderHeldItemTooltipMixin(MatrixStack matrixStack,CallbackInfo callbackInfo) {
		if (this.heldItemTooltipFade == 38 && !this.currentStack.isEmpty() && Config.get(Config.getHelditemnarratorkey())) {
			MutableText mutableText = (new LiteralText("")).append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
			Initial.narrate(mutableText.getString());
		}
	}
}
