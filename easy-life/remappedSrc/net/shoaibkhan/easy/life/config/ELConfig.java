package net.shoaibkhan.easy.life.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Gson;

import java.util.Scanner; // Import the Scanner class to read text files
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.FileWriter; // Import the FileWriter class

public class ELConfig {
    private static JsonObject data;
    private static String CONFIG_PATH = Paths.get("config", "easylife", "config.json").toString();

    public static final String Health_n_Hunger_Key = "health_n_hunger",HNH_Color_Opacity = "hnh_color_opacity", HNH_Color = "hnh_color" ,HNH_Color_Custom = "hnh_color_custom",HNH_Color_Custom_val = "hnh_color_custom_val", HNH_Scale = "hnh_scale", HNH_Position_X = "hnh_position_x", HNH_Position_Y = "hnh_position_y";
    public static final String Player_Coordinates_Key = "player_coordinates",PC_Color_Opacity = "pc_color_opacity",PC_Color = "pc_color",PC_Color_Custom = "pc_color_custom",PC_Color_Custom_val = "pc_color_custom_val",PC_Bg_Color_Opacity = "pc_bg_color_opacity", PC_Bg_Color = "pc_bg_color",PC_Position_X = "pc_position_x",PC_Position_Y = "pc_position_y";
    public static final String Player_Direction_Key = "player_direction",PD_Color_Opacity = "pd_color_opacity",PD_Color = "pd_color",PD_Color_Custom = "pd_color_custom",PD_Color_Custom_val = "pd_color_custom_val",PD_Bg_Color_Opacity = "pd_bg_color_opacity", PD_Bg_Color = "pd_bg_color",PD_Position_X = "pd_position_x",PD_Position_Y = "pd_position_y";
    public static final String Player_Warning_Key = "player_warning",PW_Color = "pw_color", PW_Scale = "pw_scale", PW_Position_X = "pw_position_x", PW_Position_Y = "pw_position_y",PW_Timeout = "pw_timeout",PW_HT_F_TH = "pw_ht_f_th",PW_HT_S_TH="pw_ht_s_th",PW_FTTH="PW_FTTH",PW_ATTH="pw_atth",PW_Sound_Status = "pw_sound_status";
    public static final String Health_Bar_Key = "health_bar",HB_Width = "hb_width";
    public static final String Narrator_Support_Key = "narrator_support";

    public ELConfig() {
    }


    public static boolean get(String key) {
        if (data == null) {
            loadConfig();
        }
        boolean val;
        try {
            val = data.get(key).getAsBoolean();
        } catch(Exception e) {
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
        int val=100;
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
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                return resetData();
            }
            data = new Gson().fromJson(jsonString, JsonObject.class);
            return data;
        } else {
            return resetData();
        }
    }

    private static JsonObject resetData(){
        data = new JsonObject();
        data.add(Health_n_Hunger_Key, new JsonPrimitive(true));
        data.add(HNH_Color, new JsonPrimitive("red"));
        data.add(HNH_Color_Opacity, new JsonPrimitive("100"));
        data.add(HNH_Color_Custom,new JsonPrimitive(false));
        data.add(HNH_Color_Custom_val,new JsonPrimitive("2c2c2c"));
        data.add(HNH_Scale, new JsonPrimitive("2"));
        data.add(HNH_Position_X, new JsonPrimitive("10"));
        data.add(HNH_Position_Y, new JsonPrimitive("30"));

        data.add(Player_Coordinates_Key, new JsonPrimitive(true));
        data.add(PC_Color, new JsonPrimitive("white"));
        data.add(PC_Color_Opacity, new JsonPrimitive("100"));
        data.add(PC_Color_Custom,new JsonPrimitive(false));
        data.add(PC_Color_Custom_val,new JsonPrimitive("2c2c2c"));
        data.add(PC_Bg_Color, new JsonPrimitive("black"));
        data.add(PC_Bg_Color_Opacity, new JsonPrimitive("100"));
        data.add(PC_Position_X, new JsonPrimitive("0"));
        data.add(PC_Position_Y, new JsonPrimitive("40"));
        
        data.add(Player_Direction_Key, new JsonPrimitive(true));
        data.add(PD_Color, new JsonPrimitive("white"));
        data.add(PD_Color_Opacity, new JsonPrimitive("100"));
        data.add(PD_Color_Custom,new JsonPrimitive(false));
        data.add(PD_Color_Custom_val,new JsonPrimitive("2c2c2c"));
        data.add(PD_Bg_Color, new JsonPrimitive("black"));
        data.add(PD_Bg_Color_Opacity, new JsonPrimitive("100"));
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


    public static String getHnhColorCustom() {
        return HNH_Color_Custom;
    }


    public static String getHnhColorCustomVal() {
        return HNH_Color_Custom_val;
    }


    public static String getHnhColorOpacity() {
        return HNH_Color_Opacity;
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