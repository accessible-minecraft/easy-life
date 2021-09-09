package net.shoaibkhan.easy.life;

import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.shoaibkhan.easy.life.utils.NarrateLabel;

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
        if (val==1) {
            NarrateLabel.waitFlag = timeOut;
            while(NarrateLabel.waitFlag > 0 && running && client!=null){
                try {
                    Thread.sleep(1);
                    if(NarrateLabel.waitFlag <= 500) {
                    	if(NarrateLabel.usingMouse.contains(usingString)) NarrateLabel.usingMouse = NarrateLabel.usingMouse.replace(usingString, "");
                    	if(NarrateLabel.usingTab.contains(usingString)) NarrateLabel.usingTab= NarrateLabel.usingTab.replace(usingString, "");
                    }
                } catch (Exception ignored) {
                    
                }
                NarrateLabel.waitFlag--;
            }
        } else if(val==0) {
        	while(running && client!=null) {
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
				} catch (Exception ignored) {
				}
        	}
        }
    }

    public void stopThread() {
        running = false;
        interrupt();
        if(!usingString.equals("")) {
        	if(NarrateLabel.usingMouse.contains(usingString)) NarrateLabel.usingMouse = NarrateLabel.usingMouse.replace(usingString, "");
        	if(NarrateLabel.usingTab.contains(usingString)) NarrateLabel.usingTab= NarrateLabel.usingTab.replace(usingString, "");
        }
    }

    public void startThread(){
        running = true;
    }
}
