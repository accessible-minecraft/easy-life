package net.shoaibkhan.easy.life;

import net.minecraft.client.MinecraftClient;
import net.shoaibkhan.easy.life.config.ELConfig;

public class CustomWait extends Thread {
    private int timeOut, val;
    private MinecraftClient client;
    private boolean running=false;

    protected void setWait(int timeOut, int val, MinecraftClient client) {
        this.timeOut = timeOut;
        this.val = val;
        this.client = client;
    }

    public void run() {
        if (val == 1) {
            ClientMod.healthWarningFlag = timeOut;
            while (ClientMod.healthWarningFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.healthWarningFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.healthWarningFlag--;
            }

        } else if (val == 2) {
            ClientMod.foodWarningFlag = timeOut;
            while (ClientMod.foodWarningFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.foodWarningFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.foodWarningFlag--;
            }

        } else if (val == 3) {
            ClientMod.airWarningFlag = timeOut;
            while (ClientMod.airWarningFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.airWarningFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.airWarningFlag--;
            }

        } else if (val == 4) {
            ClientMod.healthWarningAfterFlag = timeOut;
            while (ClientMod.healthWarningAfterFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.healthWarningAfterFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.healthWarningAfterFlag--;
            }

        } else if (val == 5) {
            ClientMod.foodWarningAfterFlag = timeOut;
            while (ClientMod.foodWarningAfterFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.foodWarningAfterFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.foodWarningAfterFlag--;
            }

        } else if (val == 6) {
            ClientMod.airWarningAfterFlag = timeOut;
            while (ClientMod.airWarningAfterFlag > 0 && running ) {
                try {
                    if(client.player==null){
                        ClientMod.airWarningAfterFlag = 0;
                        return;
                    }
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.airWarningAfterFlag--;
            }

        }
    }

    public void stopThread() {
        running = false;
        interrupt();
    }

    public void startThread(){
        running = true;
    }
}
