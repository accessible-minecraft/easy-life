package net.shoaibkhan.health.n.hunger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

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

            if(client.world.isClient){
                final PlayerEntity player = client.player;
                final InGameHud inGameHud = client.inGameHud;
                final MatrixStack matrixStack = new MatrixStack();
                if(player != null){
                    double health = client.player.getHealth();
                    int width = client.getWindow().getScaledWidth();
                    matrixStack.push();
                    matrixStack.scale(1, 1, inGameHud.getZOffset());
                    
                    DrawableHelper.fill(matrixStack, 0, 0, width, 4, getColor(health));
                    matrixStack.pop();
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
        matrixStack.scale(2, 2, inGameHud.getZOffset());
        
        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText(""+(double)Math.round((health/2)*10)/10+"X Health    "+(double)Math.round((hunger/2)*10)/10+"X Food Level"),(int)(width*0.1) , (int)(height*0.35), 0xff0000);
        matrixStack.pop();
        return true;
    }

    private int getColor(double health) {
        if (health <= 20.0 && health > 12.0) {
            return 0xff008000;
        } else if (health <= 12.0 && health > 8.0) {
            return 0xffff0000;
        } else if (health <= 8.0) {
            return 0xff800000;
        }
        return 0;
        
    }

}