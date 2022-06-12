package net.shoaibkhan.easy.life.config;

import java.io.File;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner; // Import the Scanner class to read text files

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Config {
    private static final String CONFIG_PATH = Paths.get("config", "easylife", "config.json").toString();
    private static final String Player_Coordinates_Key = "player_coordinates", PC_Color_Opacity = "pc_color_opacity", PC_Color = "pc_color", PC_Color_Custom = "pc_color_custom", PC_Color_Custom_val = "pc_color_custom_val", PC_Bg_Color_Opacity = "pc_bg_color_opacity", PC_Bg_Color = "pc_bg_color", PC_Position_X = "pc_position_x", PC_Position_Y = "pc_position_y";
    private static final String Player_Direction_Key = "player_direction", PD_Color_Opacity = "pd_color_opacity", PD_Color = "pd_color", PD_Color_Custom = "pd_color_custom", PD_Color_Custom_val = "pd_color_custom_val", PD_Bg_Color_Opacity = "pd_bg_color_opacity", PD_Bg_Color = "pd_bg_color", PD_Position_X = "pd_position_x", PD_Position_Y = "pd_position_y";
    private static final String Player_Warning_Key = "player_warning", PW_Color = "pw_color", PW_Scale = "pw_scale", PW_Position_X = "pw_position_x", PW_Position_Y = "pw_position_y", PW_timeout = "player_warnings_timeout", PW_HT_F_TH = "pw_ht_f_th", PW_HT_S_TH = "pw_ht_s_th", PW_FTTH = "PW_FTTH", PW_ATTH = "pw_atth", PW_Sound_Status = "pw_sound_status";
    private static final String HeldItemNarratorKey = "held_item_narrator_key";
    private static final String Cardinal_to_Degrees_Key = "cardinal_to_degrees_key";
    private static final String Replace_y_to_z_key = "replace_y_to_z_key";
    private static final String Biome_Indicator_Key = "biome_indicator_key";
    private static JsonObject data;

    public Config() {
    }

    public static boolean get(String key) {
        if (data == null) {
            loadConfig();
        }
        boolean val;
        try {
            val = data.get(key).getAsBoolean();
        } catch (Exception e) {
            resetData();
            val = data.get(key).getAsBoolean();
        }
        return val;
    }

    public static String getString(String key) {
        if (data == null) {
            loadConfig();
        }
        String val;
        try {
            val = data.get(key).getAsString();
        } catch (Exception e) {
            resetData();
            val = data.get(key).getAsString();
        }
        return val;
    }

    public static int getOpacity(String key) {
        if (data == null) {
            loadConfig();
        }
        String v;
        int val;
        try {
            v = data.get(key).getAsString();
        } catch (Exception e) {
            resetData();
            v = data.get(key).getAsString();
        }
        v = v.toLowerCase().trim();
        try {
            val = Integer.parseInt(v);
        } catch (Exception e) {
            val = 100;
        }
        return val;
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

    public static void setString(String key, String value) {
        data.addProperty(key, value);
        saveConfig(data);
    }

    public static boolean setInt(String key, String value) {
        try {
            Integer.parseInt(value);
            data.addProperty(key, value);
            saveConfig(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean setDouble(String key, String value) {
        try {
            Double.parseDouble(value);
            data.addProperty(key, value);
            saveConfig(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void loadConfig() {
        File configFile = new File(CONFIG_PATH);
        if (configFile.exists()) {
            StringBuilder jsonString = new StringBuilder();
            try {
                Scanner configReader = new Scanner(configFile);
                while (configReader.hasNextLine()) {
                    jsonString.append(configReader.nextLine());
                }
                configReader.close();
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                resetData();
                return;
            }
            data = new Gson().fromJson(jsonString.toString(), JsonObject.class);
        } else {
            resetData();
        }
    }

    private static void resetData() {
        data = new JsonObject();

        data.add(Player_Coordinates_Key, new JsonPrimitive(true));
        data.add(PC_Color, new JsonPrimitive("white"));
        data.add(PC_Color_Opacity, new JsonPrimitive("100"));
        data.add(PC_Color_Custom, new JsonPrimitive(false));
        data.add(PC_Color_Custom_val, new JsonPrimitive("2c2c2c"));
        data.add(PC_Bg_Color, new JsonPrimitive("black"));
        data.add(PC_Bg_Color_Opacity, new JsonPrimitive("100"));
        data.add(PC_Position_X, new JsonPrimitive("0"));
        data.add(PC_Position_Y, new JsonPrimitive("40"));

        data.add(Player_Direction_Key, new JsonPrimitive(true));
        data.add(PD_Color, new JsonPrimitive("white"));
        data.add(PD_Color_Opacity, new JsonPrimitive("100"));
        data.add(PD_Color_Custom, new JsonPrimitive(false));
        data.add(PD_Color_Custom_val, new JsonPrimitive("2c2c2c"));
        data.add(PD_Bg_Color, new JsonPrimitive("black"));
        data.add(PD_Bg_Color_Opacity, new JsonPrimitive("100"));
        data.add(PD_Position_X, new JsonPrimitive("0"));
        data.add(PD_Position_Y, new JsonPrimitive("57"));

        data.add(Player_Warning_Key, new JsonPrimitive(true));
        data.add(PW_Color, new JsonPrimitive("yellow"));
        data.add(PW_Scale, new JsonPrimitive("2"));
        data.add(PW_Position_X, new JsonPrimitive("20"));
        data.add(PW_Position_Y, new JsonPrimitive("30"));
        data.add(PW_timeout, new JsonPrimitive("20"));
        data.add(PW_HT_F_TH, new JsonPrimitive("3.0"));
        data.add(PW_HT_S_TH, new JsonPrimitive("0"));
        data.add(PW_FTTH, new JsonPrimitive("3.0"));
        data.add(PW_ATTH, new JsonPrimitive("3.0"));
        data.add(PW_Sound_Status, new JsonPrimitive(true));

        data.add(HeldItemNarratorKey, new JsonPrimitive(true));
        data.add(getCardinal_to_Degrees_Key(), new JsonPrimitive(false));
        data.add(getReplace_y_to_z_key(), new JsonPrimitive(false));
        data.add(getBiome_Indicator_Key(), new JsonPrimitive(true));

        saveConfig(data);
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

    public static String getBiome_Indicator_Key() {
        return Biome_Indicator_Key;
    }

    public static String getReplace_y_to_z_key() {
        return Replace_y_to_z_key;
    }

    public static String getCardinal_to_Degrees_Key() {
        return Cardinal_to_Degrees_Key;
    }

    public static String getHelditemnarratorkey() {
        return HeldItemNarratorKey;
    }

    public static String getPlayerCoordinatesKey() {
        return Player_Coordinates_Key;
    }

    public static String getPlayerWarningKey() {
        return Player_Warning_Key;
    }

    public static String getPlayerDirectionKey() {
        return Player_Direction_Key;
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
        return PW_timeout;
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

    public static String getPcColorCustom() {
        return PC_Color_Custom;
    }

    public static String getPcColorCustomVal() {
        return PC_Color_Custom_val;
    }

    public static String getPdColorCustom() {
        return PD_Color_Custom;
    }

    public static String getPdColorCustomVal() {
        return PD_Color_Custom_val;
    }

    public static String getPcColorOpacity() {
        return PC_Color_Opacity;
    }

    public static String getPcBgColorOpacity() {
        return PC_Bg_Color_Opacity;
    }

    public static String getPdColorOpacity() {
        return PD_Color_Opacity;
    }

    public static String getPdBgColorOpacity() {
        return PD_Bg_Color_Opacity;
    }
}