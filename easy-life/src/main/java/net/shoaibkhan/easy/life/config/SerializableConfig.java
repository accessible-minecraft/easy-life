package net.shoaibkhan.easy.life.config;

public class SerializableConfig {
    public String Health_n_Hunger_Key = "health_n_hunger",HNH_Color_Opacity = "hnh_color_opacity", HNH_Color = "hnh_color" ,HNH_Color_Custom = "hnh_color_custom",HNH_Color_Custom_val = "hnh_color_custom_val", HNH_Scale = "hnh_scale", HNH_Position_X = "hnh_position_x", HNH_Position_Y = "hnh_position_y";
    public String Player_Coordinates_Key = "player_coordinates",PC_Color_Opacity = "pc_color_opacity",PC_Color = "pc_color",PC_Color_Custom = "pc_color_custom",PC_Color_Custom_val = "pc_color_custom_val",PC_Bg_Color_Opacity = "pc_bg_color_opacity", PC_Bg_Color = "pc_bg_color",PC_Position_X = "pc_position_x",PC_Position_Y = "pc_position_y";
    public String Player_Direction_Key = "player_direction",PD_Color_Opacity = "pd_color_opacity",PD_Color = "pd_color",PD_Color_Custom = "pd_color_custom",PD_Color_Custom_val = "pd_color_custom_val",PD_Bg_Color_Opacity = "pd_bg_color_opacity", PD_Bg_Color = "pd_bg_color",PD_Position_X = "pd_position_x",PD_Position_Y = "pd_position_y";
    public String Player_Warning_Key = "player_warning",PW_Color = "pw_color", PW_Scale = "pw_scale", PW_Position_X = "pw_position_x", PW_Position_Y = "pw_position_y",PW_Timeout = "pw_timeout",PW_HT_F_TH = "pw_ht_f_th",PW_HT_S_TH="pw_ht_s_th",PW_FTTH="PW_FTTH",PW_ATTH="pw_atth",PW_Sound_Status = "pw_sound_status";
    // public String Health_Bar_Key = "health_bar",HB_Width = "hb_width";
    public String Narrator_Support_Key = "narrator_support";
    private boolean health_n_hunger;
    private boolean player_coordinates;
    private boolean player_warning;

    public SerializableConfig() {

    }

    public SerializableConfig clone() {
        SerializableConfig cloneConfig = new SerializableConfig();
        cloneConfig.health_n_hunger = this.health_n_hunger;
        cloneConfig.player_coordinates = this.player_coordinates;
        cloneConfig.player_warning = this.player_warning;
        return cloneConfig;
    }
}