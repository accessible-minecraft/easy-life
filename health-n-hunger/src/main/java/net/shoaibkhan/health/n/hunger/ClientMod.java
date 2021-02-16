package net.shoaibkhan.health.n.hunger;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

@Environment(EnvType.CLIENT)
public class ClientMod  {
    private MinecraftClient client;
    private float tickCount=0f;
    
    

    public ClientMod(KeyBinding kb){
        client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register((__, ___)->{
            if(tickCount>0f){
                if (this.kbPressed()) {
                    tickCount--;
                }
            }
            while(kb.wasPressed()){
                if (this.kbPressed()) {
                    tickCount = 60f;
                }
            }

        });
    }

    private boolean kbPressed(){
        final PlayerEntity player = client.player;
        final InGameHud inGameHud = client.inGameHud;
        final TextRenderer textRenderer = client.textRenderer;
        final MatrixStack matrixStack = new MatrixStack();
        if(player == null) return false;

        
        double health = client.player.getHealth();
        double hunger = client.player.getHungerManager().getFoodLevel();
        int height = client.getWindow().getScaledHeight();
        int width = client.getWindow().getScaledWidth();
        matrixStack.push();
        matrixStack.scale(2, 2, 1);
        
        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText(""+(double)Math.round((health/2)*10)/10+"X Health    "+(double)Math.round((hunger/2)*10)/10+"X Food Level"),(int)(width*0.1) , (int)(height*0.35), 0xff0000);
        matrixStack.pop();
        return true;
    }

}