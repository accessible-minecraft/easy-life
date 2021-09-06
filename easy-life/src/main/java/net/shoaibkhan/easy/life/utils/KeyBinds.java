package net.shoaibkhan.easy.life.utils;

import net.minecraft.client.option.KeyBinding;

public enum KeyBinds {
    CONFIG_MENU_KEY(null),
    HEALTH_N_HUNGER_KEY(null),
    PLAYER_COORDINATES_AND_DIRECTION_OVERLAY_KEY(null),
    POSITION_NARRATOR_KEY(null),
    DIRECTION_NARRATOR_KEY(null),
    F4_MENU_KEY(null);

    private KeyBinding keyBind;


    KeyBinds(KeyBinding keyBind) {this.keyBind = keyBind;}

    public KeyBinding getKeyBind(){return this.keyBind;}

    public void setKeyBind(KeyBinding newKeyBind){this.keyBind = newKeyBind;}
}
