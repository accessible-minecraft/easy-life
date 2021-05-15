package net.shoaibkhan.easy.life;

import java.util.Map;

import net.minecraft.client.MinecraftClient;

public class CustomWait extends Thread {
    private int timeOut, val;
    @SuppressWarnings("unused")
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
        if (val==7) {
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
        } else if(val==0) {
        	while(running) {
        		try {
					if(!Initial.counterMap.isEmpty()) {
						for (Map.Entry<String, Integer> entry : Initial.counterMap.entrySet()) {
							entry.setValue(entry.getValue()-1);
							if(entry.getValue()<=10) {
								System.out.println("removed "+entry.getKey());
								Initial.counterMap.remove(entry.getKey());
							}
						}
					}
					Thread.sleep(1);
				} catch (Exception e) {
				}
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
