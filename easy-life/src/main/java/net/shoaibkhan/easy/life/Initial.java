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
import net.shoaibkhan.easy.life.utils.KeyBinds;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class Initial implements ModInitializer {
    private static NarratorManager narratorManager;
    public static ClientMod clientMod;
    public static CustomWait wait;
	public static Map<String, Integer> counterMap;
    public static String biomeIndicatorString = "";

    @Override
    public void onInitialize() {

        narratorManager = NarratorManager.INSTANCE;

        initializeKeyBinds();

        counterMap = new HashMap<>();
        CustomWait countMapThread = new CustomWait();
        countMapThread.setWait(999, 0, MinecraftClient.getInstance());
        countMapThread.startThread();
        countMapThread.start();
        
        clientMod = new ClientMod();
        wait = new CustomWait();
    }

    private void initializeKeyBinds(){
        KeyBinds.HEALTH_N_HUNGER_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Health n Hunger", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Easy Life")));
        KeyBinds.PLAYER_COORDINATES_AND_DIRECTION_OVERLAY_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Co-ordinates", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "Easy Life")));
        KeyBinds.CONFIG_MENU_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Configuration", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "Easy Life")));
        KeyBinds.POSITION_NARRATOR_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Position Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "Easy Life")));
        KeyBinds.DIRECTION_NARRATOR_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Direction Narrator", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "Easy Life")));
        KeyBinds.F4_MENU_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("Narrator Menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4, "Easy Life")));
    }

    public static void narrate(String string){
        NarratorManager.INSTANCE.clear();
        narratorManager.narrate(string);
    }
}
