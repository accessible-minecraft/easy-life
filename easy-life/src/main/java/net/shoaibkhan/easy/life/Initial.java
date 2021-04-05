package net.shoaibkhan.easy.life;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Initial implements ModInitializer {
    public static ClientMod clientMod;
    public static KeyBinding kb,coord,CONFIG_KEY,position_narrator,direction_narrator,narrator_menu;
    private static String prevLabel = "";
    private static CustomWait wait;
    private static int prevX = -9999,prevY = -9999;
    public static int waitFlag = 0;

    @Override
    public void onInitialize() {
        System.out.println("Mod is initializing!!");
        wait = new CustomWait();

        kb = KeyBindingHelper.registerKeyBinding(new KeyBinding("Health n Hunger", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Easy Life"));
        coord = KeyBindingHelper.registerKeyBinding(new KeyBinding("Co-ordinates", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "Easy Life"));
        CONFIG_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("Configuration", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_J, "Easy Life"));
        position_narrator = KeyBindingHelper.registerKeyBinding(new KeyBinding("Position Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Easy Life"));
        direction_narrator = KeyBindingHelper.registerKeyBinding(new KeyBinding("Direction Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "Easy Life"));
        narrator_menu = KeyBindingHelper.registerKeyBinding(new KeyBinding("Narrator Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4, "Easy Life"));

        clientMod = new ClientMod(kb,coord,CONFIG_KEY,position_narrator,direction_narrator, narrator_menu);
    }

    public static void narrateLabel(String label,int x, int y){
        if (label.equalsIgnoreCase(prevLabel) && waitFlag>0 && x==prevX && y==prevY) return;
        prevLabel = label;
        prevX = x;
        prevY = y;
        NarratorManager.INSTANCE.clear();
        NarratorManager.INSTANCE.narrate(label);
        if(wait.isAlive()) wait.stopThread();
        wait = new CustomWait();
        wait.setWait(10000, 7, MinecraftClient.getInstance());
        wait.start();
        wait.startThread();
    }
    
}
