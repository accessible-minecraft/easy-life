package net.shoaibkhan.easy.life;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config {

    private String health_n_hunger_status="on", health_n_hunger_color="red";
    private int health_n_hunger_scale=2,health_n_hunger_positionx=10,health_n_hunger_positiony=30;

    private String health_bar_status="off";
    private int health_bar_width=3;

    private String player_coordination_status="on",player_coordination_color="white",player_coordination_backgroundcolor="black";
    private int player_coordination_positionx=0,player_coordination_positiony=40;

    private String player_direction_status="on",player_direction_color="white",player_direction_backgroundcolor="black";
    private int player_direction_positionx=0,player_direction_positiony=57;

    private String player_warning_status="off",player_warning_color="yellow";
    private int player_warning_positionx=20,player_warning_positiony=30,player_warning_timeout=300,player_warning_scale=2;
    
    Config(){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/assets/config.txt")))) {

            String line;
            while((line=in.readLine())!=null){
                line = line.toLowerCase();
                line.replace(" ", "");
                if(!line.contains("//")){
                    System.out.println(line);
                    if(line.contains("health-n-hunger")){
                        line = line.replace("health-n-hunger-", "");
                        if(line.contains("status"))
                            health_n_hunger_status = line.replace("status=", "");
                        else if(line.contains("color"))
                                health_n_hunger_color = line.replace("color=", "");
                            else if(line.contains("scale"))
                                    health_n_hunger_scale = Integer.parseInt(line.replace("scale=", ""));
                                else if(line.contains("positionx"))
                                        health_n_hunger_positionx = Integer.parseInt(line.replace("positionx=", ""));
                                    else if(line.contains("positiony"))
                                            health_n_hunger_positiony = Integer.parseInt(line.replace("positiony=", ""));
    
                    } else if(line.contains("health-bar")){
                        line = line.replace("health-bar-", "");
                        if(line.contains("status"))
                            health_bar_status = line.replace("status=", "");
                        else if(line.contains("width"))
                                health_bar_width = Integer.parseInt(line.replace("width=", ""));
                    } else if(line.contains("player-coordination")){
                        line = line.replace("player-coordination-", "");
                        if(line.contains("status"))
                            player_coordination_status = line.replace("status=", "");
                        else if(line.contains("color"))
                                player_coordination_color = line.replace("color=", "");
                            else if(line.contains("background"))
                                    player_coordination_backgroundcolor = line.replace("background=", "");
                                else if(line.contains("positionx"))
                                        player_coordination_positionx = Integer.parseInt(line.replace("positionx=", ""));
                                    else if(line.contains("positiony"))
                                            player_coordination_positiony = Integer.parseInt(line.replace("positiony=", ""));
                    } else if(line.contains("player-direction")){
                        line = line.replace("player-direction-", "");
                        if(line.contains("status"))
                            player_direction_status = line.replace("status=", "");
                        else if(line.contains("color"))
                                player_direction_color = line.replace("color=", "");
                            else if(line.contains("background"))
                                    player_direction_backgroundcolor = line.replace("background=", "");
                                else if(line.contains("positionx"))
                                        player_direction_positionx = Integer.parseInt(line.replace("positionx=", ""));
                                    else if(line.contains("positiony"))
                                            player_direction_positiony = Integer.parseInt(line.replace("positiony=", ""));
                    } else if(line.contains("player-warnings")){
                        line = line.replace("player-warnings-", "");
                        if(line.contains("status"))
                            player_warning_status = line.replace("status=", "");
                        else if(line.contains("color"))
                                player_warning_color = line.replace("color=", "");
                            else if(line.contains("positionx"))
                                        player_warning_positionx = Integer.parseInt(line.replace("positionx=", ""));
                                    else if(line.contains("positiony"))
                                            player_warning_positiony = Integer.parseInt(line.replace("positiony=", ""));
                                        else if(line.contains("timeout"))
                                                player_warning_timeout = Integer.parseInt(line.replace("timeout=", ""));
                                            else if(line.contains("scale"))
                                                    player_warning_scale = Integer.parseInt(line.replace("scale=", ""));
                    }
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

    public String getPlayer_warning_status() {
        return player_warning_status;
    }

    public String getPlayer_warning_color() {
        return player_warning_color;
    }

    public int getPlayer_warning_positionx() {
        return player_warning_positionx;
    }

    public int getPlayer_warning_positiony() {
        return player_warning_positiony;
    }

    public int getPlayer_warning_timeout() {
        return player_warning_timeout;
    }

    public int getPlayer_warning_scale() {
        return player_warning_scale;
    }

}
