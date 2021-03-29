package net.shoaibkhan.easy.life.config;

public class SerializableConfig {
    public boolean health_n_hunger = true;
    public boolean player_coordinates = true;
    public boolean player_warning = true;
    public boolean health_bar = true;

    public SerializableConfig() {

    }

    public SerializableConfig clone() {
        SerializableConfig cloneConfig = new SerializableConfig();
        cloneConfig.health_n_hunger = this.health_n_hunger;
        cloneConfig.player_coordinates = this.player_coordinates;
        cloneConfig.player_warning = this.player_warning;
        cloneConfig.health_bar = this.health_bar;
        return cloneConfig;
    }
}