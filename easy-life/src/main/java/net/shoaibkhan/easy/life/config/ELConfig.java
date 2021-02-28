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

    public static final String Health_n_Hunger_Key = "health_n_hunger", HNH_Color = "hnh_color" , HNH_Scale = "hnh_scale", HNH_Position_X = "hnh_position_x", HNH_Position_Y = "hnh_position_y";
    public static final String Player_Coordinates_Key = "player_coordinates",PC_Color = "pc_color", PC_Bg_Color = "pc_bg_color",PC_Position_X = "pc_position_x",PC_Position_Y = "pc_position_y";
    public static final String Player_Direction_Key = "player_direction",PD_Color = "pd_color", PD_Bg_Color = "pd_bg_color",PD_Position_X = "pd_position_x",PD_Position_Y = "pd_position_y";
    public static final String Player_Warning_Key = "player_warning",PW_Color = "pw_color", PW_Scale = "pw_scale", PW_Position_X = "pw_position_x", PW_Position_Y = "pw_position_y",PW_Timeout = "pw_timeout",PW_HT_F_TH = "pw_ht_f_th",PW_HT_S_TH="pw_ht_s_th",PW_FTTH="PW_FTTH",PW_ATTH="pw_atth",PW_Sound_Status = "pw_sound_status";
    public static final String Health_Bar_Key = "health_bar",HB_Width = "hb_width";
    public static final String Narrator_Support_Key = "narrator_support";

    public ELConfig() {
    }


    public static boolean get(String key) {
        if (data == null) {
            loadConfig();
        }
        return data.get(key).getAsBoolean();
    }

    public static String getString(String key) {
        if (data == null) {
            loadConfig();
        }
        return data.get(key).getAsString();
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

    public static void setString(String key, String value){
        data.addProperty(key, value);
        saveConfig(data);
    }

    public static boolean setInt(String key, String value){
        try{
            Integer.parseInt(value);
            data.addProperty(key, value);
            saveConfig(data);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public static boolean setDouble(String key, String value){
        try{
            Double.parseDouble(value);
            data.addProperty(key, value);
            saveConfig(data);
            return true;
        }catch (Exception e) {
            return false;
        }
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
            data.add(HNH_Color, new JsonPrimitive("red"));
            data.add(HNH_Scale, new JsonPrimitive("2"));
            data.add(HNH_Position_X, new JsonPrimitive("10"));
            data.add(HNH_Position_Y, new JsonPrimitive("30"));

            data.add(Player_Coordinates_Key, new JsonPrimitive(true));
            data.add(PC_Color, new JsonPrimitive("white"));
            data.add(PC_Bg_Color, new JsonPrimitive("black"));
            data.add(PC_Position_X, new JsonPrimitive("0"));
            data.add(PC_Position_Y, new JsonPrimitive("40"));
            
            data.add(Player_Direction_Key, new JsonPrimitive(true));
            data.add(PD_Color, new JsonPrimitive("white"));
            data.add(PD_Bg_Color, new JsonPrimitive("black"));
            data.add(PD_Position_X, new JsonPrimitive("0"));
            data.add(PD_Position_Y, new JsonPrimitive("57"));

            data.add(Player_Warning_Key, new JsonPrimitive(true));
            data.add(PW_Color, new JsonPrimitive("yellow"));
            data.add(PW_Scale, new JsonPrimitive("2"));
            data.add(PW_Position_X, new JsonPrimitive("20"));
            data.add(PW_Position_Y, new JsonPrimitive("30"));
            data.add(PW_Timeout, new JsonPrimitive("60"));
            data.add(PW_HT_F_TH, new JsonPrimitive("3.0"));
            data.add(PW_HT_S_TH, new JsonPrimitive("0"));
            data.add(PW_FTTH, new JsonPrimitive("3.0"));
            data.add(PW_ATTH, new JsonPrimitive("3.0"));
            data.add(PW_Sound_Status, new JsonPrimitive("on"));
            
            data.add(Health_Bar_Key, new JsonPrimitive(false));
            data.add(HB_Width, new JsonPrimitive("3"));
            
            data.add(Narrator_Support_Key, new JsonPrimitive(true));

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

    public static String getNarratorSupportKey() {
        return Narrator_Support_Key;
    }

    public static String getHnhColor() {
        return HNH_Color;
    }

    public static String getHnhScale() {
        return HNH_Scale;
    }

    public static String getHnhPositionX() {
        return HNH_Position_X;
    }

    public static String getHnhPositionY() {
        return HNH_Position_Y;
    }

    public static String getPcColor() {
        return PC_Color;
    }

    public static String getPcBgColor() {
        return PC_Bg_Color;
    }

    public static String getPcPositionX() {
        return PC_Position_X;
    }

    public static String getPcPositionY() {
        return PC_Position_Y;
    }

    public static String getPdColor() {
        return PD_Color;
    }

    public static String getPdBgColor() {
        return PD_Bg_Color;
    }

    public static String getPdPositionX() {
        return PD_Position_X;
    }

    public static String getPdPositionY() {
        return PD_Position_Y;
    }

    public static String getPwColor() {
        return PW_Color;
    }

    public static String getPwScale() {
        return PW_Scale;
    }

    public static String getPwPositionX() {
        return PW_Position_X;
    }

    public static String getPwPositionY() {
        return PW_Position_Y;
    }

    public static String getPwTimeout() {
        return PW_Timeout;
    }

    public static String getHbWidth() {
        return HB_Width;
    }

    public static String getPwHtFTh() {
        return PW_HT_F_TH;
    }

    public static String getPwHtSTh() {
        return PW_HT_S_TH;
    }

    public static String getPwFtth() {
        return PW_FTTH;
    }

    public static String getPwAtth() {
        return PW_ATTH;
    }

    public static String getPwSoundStatus() {
        return PW_Sound_Status;
    }
}