package net.shoaibkhan.easy.life;

import net.minecraft.client.MinecraftClient;

public class CustomWait extends Thread {
    private int timeOut, val;
    private MinecraftClient client;
    private boolean running=false;
    private String usingString = "";

    public void setWait(int timeOut, int val, MinecraftClient client) {
        this.timeOut = timeOut;
        this.val = val;
        this.client = client;
    }

    public void setTabWait(int timeOut, int val, MinecraftClient client,String using) {
    	setWait(timeOut, val, client);
    	usingString = using;
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

        } else if (val==7) {
            Initial.waitFlag = timeOut;
            while(Initial.waitFlag > 0 && running){
                try {
                    Thread.sleep(1);
                    if(Initial.waitFlag <= 500) {
                    	if(Initial.usingMouse.contains(usingString)) Initial.usingMouse = Initial.usingMouse.replace(usingString, "");
                    	if(Initial.usingTab.contains(usingString)) Initial.usingTab= Initial.usingTab.replace(usingString, "");
                    }
                } catch (Exception e) {
                    
                }
                Initial.waitFlag--;
            }
        }
    }

    public void stopThread() {
        running = false;
        interrupt();
        if(!usingString.equals("")) {
        	if(Initial.usingMouse.contains(usingString)) Initial.usingMouse = Initial.usingMouse.replace(usingString, "");
        	if(Initial.usingTab.contains(usingString)) Initial.usingTab= Initial.usingTab.replace(usingString, "");
        }
    }

    public void startThread(){
        running = true;
    }
}
