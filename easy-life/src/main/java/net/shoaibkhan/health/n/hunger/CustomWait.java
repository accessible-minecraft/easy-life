package net.shoaibkhan.health.n.hunger;

public class CustomWait extends Thread {
    private int timeOut, val;
    // protected int healthWarningFlag = 0,foodWarningFlag = 0;

    protected void setWait(int timeOut, int val) {
        this.timeOut = timeOut;
        this.val = val;
    }

    public void run() {
        if (val == 1) {
            ClientMod.healthWarningFlag = timeOut;
            while (ClientMod.healthWarningFlag > 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.healthWarningFlag--;
            }

        } else if (val == 2) {
            ClientMod.foodWarningFlag = timeOut;
            while (ClientMod.foodWarningFlag > 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.foodWarningFlag--;
            }

        } else if (val == 3) {
            ClientMod.airWarningFlag = timeOut;
            while (ClientMod.airWarningFlag > 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientMod.airWarningFlag--;
            }

        }
    }
}
