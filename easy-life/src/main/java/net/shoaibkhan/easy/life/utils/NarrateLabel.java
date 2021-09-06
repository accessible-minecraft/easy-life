package net.shoaibkhan.easy.life.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.NarratorManager;
import net.shoaibkhan.easy.life.CustomWait;

public class NarrateLabel {
    private static CustomWait wait;
    private static int prevX = -9999,prevY = -9999;
    public static int waitFlag = 0;
    public static String usingTab = "",usingMouse = "";

    public NarrateLabel(){
        wait = new CustomWait();
    }

    /**
     * A method/function which narrates the provided label of a libGui WButton.
     *
     * @param label : The string to be narrated.
     * @param x : The x position of the button.
     * @param y : The y position of the button.
     */
    public static void narrateLabel(String label,int x, int y,String using){
        MinecraftClient instance = MinecraftClient.getInstance();
        if (instance.player == null) return;
        if (waitFlag>0 && x==prevX && y==prevY && (NarrateLabel.usingMouse.contains(using)||NarrateLabel.usingTab.contains(using)) ) return;
        prevX = x;
        prevY = y;
        NarratorManager.INSTANCE.clear();
        NarratorManager.INSTANCE.narrate(label);
        if(wait.isAlive()) wait.stopThread();
        wait = new CustomWait();
        wait.setTabWait(5000, 1, instance, using);
        wait.start();
        wait.startThread();
    }
}
