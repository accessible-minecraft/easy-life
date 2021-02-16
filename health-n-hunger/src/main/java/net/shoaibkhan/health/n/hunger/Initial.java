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
    public ClientMod clientMod;
    public static KeyBinding kb;

    @Override
    public void onInitialize() {
        // On initializing minecraft
        System.out.println("Mod is initializing!!");

        kb = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Check",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "Easy Life"
        ));
        
        clientMod = new ClientMod(kb);

    }
    
}
