package net.shoaibkhan.health.n.hunger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config {

    private String health_n_hunger_status, health_n_hunger_color;
    private int health_n_hunger_scale,health_n_hunger_positionx,health_n_hunger_positiony;

    private String health_bar_status;
    private int health_bar_width;

    private String player_coordination_status,player_coordination_color,player_coordination_backgroundcolor;
    private int player_coordination_positionx,player_coordination_positiony;

    private String player_direction_status,player_direction_color,player_direction_backgroundcolor;
    private int player_direction_positionx,player_direction_positiony;
    
    Config(){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/config.txt")))) {

            String line;
            while((line=in.readLine())!=null){
                line = line.toLowerCase();
                line.replace(" ", "");
                if(line.contains("health-n-hunger")){
                    line = line.replace("health-n-hunger-", "");
                    if(line.contains("status"))
                        health_n_hunger_status = line.replace("status=", "");
                    else if(line.contains("color"))
                            health_n_hunger_color = line.replace("color=", "");

                } else if(line.toLowerCase().contains("health-bar")){
                    line = line.replace("health-bar-", "");
                    if(line.contains("status"))
                        health_bar_status = line.replace("status=", "");
                }
            }
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHealth_n_hunger_status() {
        return health_n_hunger_status;
    }

    public String getHealth_n_hunger_color() {
        return health_n_hunger_color;
    }

    public String getHealth_bar_status() {
        return health_bar_status;
    }

    public int getHealth_n_hunger_scale() {
        return health_n_hunger_scale;
    }

    public int getHealth_n_hunger_positionx() {
        return health_n_hunger_positionx;
    }

    public int getHealth_n_hunger_positiony() {
        return health_n_hunger_positiony;
    }

    public int getHealth_bar_width() {
        return health_bar_width;
    }

    public String getPlayer_coordination_status() {
        return player_coordination_status;
    }

    public String getPlayer_coordination_color() {
        return player_coordination_color;
    }

    public String getPlayer_coordination_backgroundcolor() {
        return player_coordination_backgroundcolor;
    }

    public int getPlayer_coordination_positionx() {
        return player_coordination_positionx;
    }

    public int getPlayer_coordination_positiony() {
        return player_coordination_positiony;
    }

    public String getPlayer_direction_status() {
        return player_direction_status;
    }

    public String getPlayer_direction_color() {
        return player_direction_color;
    }

    public String getPlayer_direction_backgroundcolor() {
        return player_direction_backgroundcolor;
    }

    public int getPlayer_direction_positionx() {
        return player_direction_positionx;
    }

    public int getPlayer_direction_positiony() {
        return player_direction_positiony;
    }

}
