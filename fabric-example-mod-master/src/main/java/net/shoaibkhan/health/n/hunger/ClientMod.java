package net.shoaibkhan.health.n.hunger;


import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class ClientMod implements ClientModInitializer {
    public static KeyBinding kb;
    private static float tickCount = 0f;
    @Override
    public void onInitializeClient() {
        kb = KeyBindingHelper
                .registerKeyBinding(new KeyBinding("Check", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Health n Hunger"));
        
        // ClientTickEvents.END_CLIENT_TICK.register(client -> {
        //     while (kb.wasPressed()) {
        //         // client.player.sendChatMessage("st" + client.player.getHealth());
        //         // TextRenderer textRenderer = client.getInstance().textRenderer;

        //     }
        // });

        HudRenderCallback.EVENT.register(new HudRenderCallback() {

            @Override
            public void onHudRender(MatrixStack matrixStack, float tickDelta) {
                // TODO Auto-generated method stub
                if(tickCount>0f){
                    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                    double health = MinecraftClient.getInstance().player.getHealth();
                    double hunger = MinecraftClient.getInstance().player.getHungerManager().getFoodLevel();
                    int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
                    int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
                    
                    matrixStack.push();
                    matrixStack.scale(2, 2, 1);
                    
                    DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText(""+health/2+"X Health    "+hunger/2+"X Food Level"),(int)(width*0.1) , (int)(height*0.35), 0xff0000);
                    matrixStack.pop();
                    tickCount--;
                }
                while(kb.wasPressed()){
                    // MinecraftClient.getInstance().player.sendSystemMessage(new LiteralText("string"), Util.NIL_UUID);
                    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                    double health = MinecraftClient.getInstance().player.getHealth();
                    double hunger = MinecraftClient.getInstance().player.getHungerManager().getFoodLevel();
                    int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
                    int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
                    matrixStack.push();
                    matrixStack.scale(2, 2, 1);
                    
                    DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText(""+health/2+"X Health    "+hunger/2+"X Food Level"),(int)(width*0.1) , (int)(height*0.35), 0xff0000);
                    matrixStack.pop();
                    tickCount = 60f;
                }
            }
            
        });

    }

}