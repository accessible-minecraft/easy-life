package net.shoaibkhan.easy.life.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.shoaibkhan.easy.life.config.ELConfig;

public class PlayerPosition {
    private final PlayerEntity player;

    public PlayerPosition(MinecraftClient client){
        this.player = client.player;
    }

    public double getX(){
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosX = pos.x +"";
        tempPosX = tempPosX.substring(0, tempPosX.indexOf(".")+2);

        return Double.parseDouble(tempPosX);
    }

    public double getY(){
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosY;
        if(ELConfig.get(ELConfig.getReplace_y_to_z_key())) tempPosY = pos.z +"";
        else tempPosY = pos.y +"";
        tempPosY = tempPosY.substring(0, tempPosY.indexOf(".")+2);

        return Double.parseDouble(tempPosY);
    }

    public double getZ(){
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosZ;
        if(ELConfig.get(ELConfig.getReplace_y_to_z_key())) tempPosZ = pos.y +"";
        else tempPosZ = pos.z +"";
        tempPosZ = tempPosZ.substring(0, tempPosZ.indexOf(".")+2);

        return Double.parseDouble(tempPosZ);
    }

    public String getNarratableXPos(){
        String posX = "" + getX();
        if(posX.contains("-")) posX = posX.replace("-", "negative");
        return ""+posX+"x";
    }

    public String getNarratableYPos(){
        String posY= "" + getY();
        if(posY.contains("-")) posY = posY.replace("-", "negative");
        return ""+posY+"y";
    }

    public String getNarratableZPos(){
        String posZ= "" + getZ();
        if(posZ.contains("-")) posZ = posZ.replace("-", "negative");
        return ""+posZ+"z";
    }
}
