package net.shoaibkhan.health.n.hunger;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class Initial implements ModInitializer {
    public static ClientMod clientMod;
    public static Config config;
    public static KeyBinding kb,coord;

    @Override
    public void onInitialize() {
        // On initializing minecraft
        System.out.println("Mod is initializing!!");
        
        config = new Config();

        kb = KeyBindingHelper.registerKeyBinding(new KeyBinding("Health n Hunger", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Easy Life"));
        coord = KeyBindingHelper.registerKeyBinding(new KeyBinding("Co-ordination", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F9, "Easy Life"));

        clientMod = new ClientMod(kb,coord);

    }
    
}
