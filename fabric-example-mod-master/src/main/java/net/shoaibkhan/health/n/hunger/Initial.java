package net.shoaibkhan.health.n.hunger;

import javax.swing.text.JTextComponent.KeyBinding;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;

public class Initial implements ClientModInitializer,ModInitializer {
    

    @Override
    public void onInitializeClient() {
        // Runs every tick
        PlayerEntity player = (PlayerEntity)(Object)this;
        player.sendSystemMessage(new LiteralText("Hunger is "), Util.NIL_UUID);
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            // while (kb.wasPressed()) {
            //     double hunger = client.player.getHungerManager().getFoodLevel();
            //     double health = client.player.getHealth();
            //     client.player.sendSystemMessage(new LiteralText("Health is " + (int) health / 2), Util.NIL_UUID);
            //     client.player.sendSystemMessage(new LiteralText("Hunger is " + (int) health / 2), Util.NIL_UUID);
            // }
            double hunger = client.player.getHungerManager().getFoodLevel(),curHunger=0;
            if(hunger<curHunger){
                if (hunger<=12.0 & hunger>6.0) {
                    client.player.sendSystemMessage(new LiteralText("Hunger is " + (int) hunger / 2), Util.NIL_UUID);
                } else if(hunger<=6.0 & hunger > 0) {
                    client.player.sendSystemMessage(new LiteralText("Hunger is " + (int) hunger / 2), Util.NIL_UUID);
                }
            }
            curHunger = hunger;
            
        });
        
    }

    @Override
    public void onInitialize() {
        // On initializing minecraft
        System.out.println("Mod is initializing!!\n\t\tMod created by:-Shoaib Khan");

    }
    
}
