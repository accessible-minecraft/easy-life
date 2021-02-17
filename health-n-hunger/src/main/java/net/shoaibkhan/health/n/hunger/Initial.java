package net.shoaibkhan.health.n.hunger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;

import org.json.simple.parser.JSONParser;
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
    private String health_n_hunger_status;

    @Override
    public void onInitialize() {
        // On initializing minecraft
        System.out.println("Mod is initializing!!");

        kb = KeyBindingHelper
                .registerKeyBinding(new KeyBinding("Check", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "Easy Life"));

        // FileReader reader = new FileReader("/resources/assets/config.json")
        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/config.txt")))) {

            health_n_hunger_status = in.readLine();
            if(health_n_hunger_status.indexOf("health_n_hunger.status")>=0){
                health_n_hunger_status = health_n_hunger_status.substring(health_n_hunger_status.indexOf("="));
                health_n_hunger_status.trim();
                System.out.println(health_n_hunger_status);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("reacheade\n\tsdfhxvdgsz\n\treacheade\n\tsdfhxvdgsz\n\treacheade\n\tsdfhxvdgsz\n\t");

        clientMod = new ClientMod(kb);

    }
    
}
