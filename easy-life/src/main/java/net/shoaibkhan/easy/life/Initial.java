package net.shoaibkhan.easy.life;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
//import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import net.shoaibkhan.easy.life.utils.KeyBinds;

public class Initial implements ClientModInitializer {
    public static ClientMod clientMod;
    public static CustomWait wait;
    public static Map<String, Integer> counterMap;
    public static String biomeIndicatorString = "";
    private static NarratorManager narratorManager;

    public static void narrate(String string) {
        NarratorManager.INSTANCE.clear();
        narratorManager.narrate(string);
    }

    @Override
    public void onInitializeClient() {

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

    private void initializeKeyBinds() {
        KeyBinds.HEALTH_N_HUNGER_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.healthHunger", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "gui.easylife")));
        KeyBinds.PLAYER_COORDINATES_AND_DIRECTION_OVERLAY_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.coordinates", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "gui.easylife")));
        KeyBinds.CONFIG_MENU_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.configuration", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, "gui.easylife")));
        KeyBinds.POSITION_NARRATOR_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.position", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "gui.easylife")));
        KeyBinds.DIRECTION_NARRATOR_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.direction", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "gui.easylife")));
        KeyBinds.F4_MENU_KEY.setKeyBind(KeyBindingHelper.registerKeyBinding(new KeyBinding("key.easylife.narratorMenu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F4, "gui.easylife")));
    }
}
