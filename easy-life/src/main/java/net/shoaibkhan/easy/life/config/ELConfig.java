package net.shoaibkhan.easy.life.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.FileWriter; // Import the FileWriter class

public class ELConfig {
    private static SerializableConfig config;
    private static JsonObject data;
    private static String CONFIG_PATH = Paths.get("config", "easylife", "config.json").toString();

    public static final String Health_n_Hunger_Key = "health_n_hunger";
    public static final String Player_Coordinates_Key = "player_coordinates";
    public static final String Player_Direction_Key = "player_direction";
    public static final String Player_Warning_Key = "player_warning";
    public static final String Health_Bar_Key = "health_bar";

    public ELConfig() {
    }


    public static boolean get(String key) {
        if (data == null) {
            loadConfig();
        }
        return data.get(key).getAsBoolean();
    }

    public static boolean toggle(String key) {
        boolean newValue = !get(key);
        set(key, newValue);
        return newValue;
    }

    public static void set(String key, boolean value) {
        data.addProperty(key, value);
        saveConfig(data);
    }

    private static JsonObject loadConfig() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            String jsonString = "";
            try {
                Scanner configReader = new Scanner(configFile);
                while (configReader.hasNextLine()) {
                    jsonString += configReader.nextLine();
                }
                configReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();

            }
            data = new Gson().fromJson(jsonString, JsonObject.class);
            return data;
        } else {
            data = new JsonObject();
            data.add(Health_n_Hunger_Key, new JsonPrimitive(true));
            data.add(Player_Coordinates_Key, new JsonPrimitive(true));
            data.add(Player_Direction_Key, new JsonPrimitive(true));
            data.add(Player_Warning_Key, new JsonPrimitive(true));
            data.add(Health_Bar_Key, new JsonPrimitive(false));

            saveConfig(data);
            return data;
        }
    }

    public static void saveConfig(JsonObject newConfig) {
        // Save config to file

        String jsonString = new Gson().toJson(data);
        try {

            File configFile = new File(CONFIG_PATH);
            configFile.getParentFile().mkdirs();
            FileWriter configWriter = new FileWriter(configFile);
            configWriter.write(jsonString);
            configWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        data = newConfig;
    }

    public static String getHealthNHungerKey() {
        return Health_n_Hunger_Key;
    }

    public static String getPlayerCoordinatesKey() {
        return Player_Coordinates_Key;
    }

    public static String getPlayerWarningKey() {
        return Player_Warning_Key;
    }

    public static String getHealthBarKey() {
        return Health_Bar_Key;
    }

    public static String getPlayerDirectionKey() {
        return Player_Direction_Key;
    }
}