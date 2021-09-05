package net.shoaibkhan.easy.life;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
//import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;



@Environment(EnvType.CLIENT)
public class Initial implements ModInitializer {
    public static ClientMod clientMod;
    public KeyBinding healthNhungerKey,coord,CONFIG_KEY,position_narrator,direction_narrator,narrator_menu;
    private static CustomWait wait;
    private static int prevX = -9999,prevY = -9999;
    public static int waitFlag = 0;
    public static String prevItemString = "null";
    public static String pickedUpItems = "|"; 
//    public static boolean usingTab = false,usingMouse = false;
    public static String usingTab = "",usingMouse = "";
	public static Map<String, Integer> counterMap;

    /**
     * Constructor which initializes the global variables/parameters. 
     */
    @Override
    public void onInitialize() {
        wait = new CustomWait();

        healthNhungerKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Health n Hunger", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Easy Life"));
        coord = KeyBindingHelper.registerKeyBinding(new KeyBinding("Co-ordinates", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "Easy Life"));
        CONFIG_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding("Configuration", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "Easy Life"));
        position_narrator = KeyBindingHelper.registerKeyBinding(new KeyBinding("Position Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Easy Life"));
        direction_narrator = KeyBindingHelper.registerKeyBinding(new KeyBinding("Direction Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "Easy Life"));
        narrator_menu = KeyBindingHelper.registerKeyBinding(new KeyBinding("Narrator Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4, "Easy Life"));

        counterMap = new HashMap<>();
        CustomWait countMapThread = new CustomWait();
        countMapThread.setWait(999, 0, MinecraftClient.getInstance());
        countMapThread.startThread();
        countMapThread.start();
        
        clientMod = new ClientMod(healthNhungerKey,coord,CONFIG_KEY,position_narrator,direction_narrator, narrator_menu);
    }
    
    /**
     * A method/function which narrates the provided label of a libGui WButton. 
     * 
     * @param label : The string to be narrated.
     * @param x : The x position of the button.
     * @param y : The y position of the button.
     */
    public static void narrateLabel(String label,int x, int y,String using){
        MinecraftClient instance = MinecraftClient.getInstance();
		if (instance.player == null) return;
        if (waitFlag>0 && x==prevX && y==prevY && (Initial.usingMouse.contains(using)||Initial.usingTab.contains(using)) ) return;
        prevX = x;
        prevY = y;
        NarratorManager.INSTANCE.clear();
        NarratorManager.INSTANCE.narrate(label);
        if(wait.isAlive()) wait.stopThread();
        wait = new CustomWait();
        wait.setTabWait(5000, 7, instance, using);
        wait.start();
        wait.startThread();
    }
    
}
