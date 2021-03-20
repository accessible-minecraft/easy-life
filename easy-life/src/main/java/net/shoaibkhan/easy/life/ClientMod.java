package net.shoaibkhan.easy.life;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.gui.ConfigGui;
import net.shoaibkhan.easy.life.gui.ConfigScreen;

@Environment(EnvType.CLIENT)
public class ClientMod {
    private MinecraftClient client;
    public static float tickCount = 0f;
    private boolean coordFlag = false;
    public static boolean kbFlag = false;
    public static boolean flag = true;
    private CustomWait obj[] = {new CustomWait(),new CustomWait(),new CustomWait()};
    public static int healthWarningFlag = 0, foodWarningFlag = 0, airWarningFlag = 0;
    public static int healthWarningAfterFlag = 0, foodWarningAfterFlag = 0, airWarningAfterFlag = 0;
    public static String[] colorNames = {"black","white","red","blue","purple","green","grey","lightgrey","yellow","orange","brown","pink"};
    public static String[] soundNames = {"anvil land"};
    GameOptions gameOptions;

    public ClientMod(KeyBinding kb, KeyBinding coord, KeyBinding CONFIG_KEY) {
        client = MinecraftClient.getInstance();
        

        HudRenderCallback.EVENT.register((__, ___) -> {
            if(client.player== null) return;

            while(CONFIG_KEY.wasPressed()){
                client.openScreen(new ConfigScreen(new ConfigGui(client.player,client)));
                return;
            }
            
            if (tickCount > 0f) {
                if (this.kbPressed() && ELConfig.get(ELConfig.Health_n_Hunger_Key)) {
                    tickCount -= client.getTickDelta();
                }
            }

            if (coordFlag && (ELConfig.get(ELConfig.Player_Coordinates_Key)
                    || ELConfig.get(ELConfig.Player_Direction_Key))) {
                showCoord();
            }

            while (coord.wasPressed()
                    && (ELConfig.get(ELConfig.Player_Coordinates_Key)
                            || ELConfig.get(ELConfig.Player_Direction_Key))) {
                coordFlag = !coordFlag;
            }

            while (kb.wasPressed() && ELConfig.get(ELConfig.Health_n_Hunger_Key)) {
                if (this.kbPressed()) {
                    if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                        double health = client.player.getHealth();
                        double hunger = client.player.getHungerManager().getFoodLevel();
                        client.player.sendMessage(new LiteralText("health is "+((double) Math.round((health / 2) * 10) / 10)+" Hunger is "+((double) Math.round((hunger / 2) * 10) / 10)),true);
                    }
                    tickCount = 120f;
                }
            }

            if (!client.isPaused() && client.world.isClient) {
                final PlayerEntity player = client.player;
                final InGameHud inGameHud = client.inGameHud;
                final MatrixStack matrixStack = new MatrixStack();
                final TextRenderer textRenderer = client.textRenderer;

                if (player != null && (ELConfig.get(ELConfig.Health_Bar_Key)
                        || ELConfig.get(ELConfig.Player_Warning_Key)) ) {

                    
                    final int height = client.getWindow().getScaledHeight();
                    final int width = client.getWindow().getScaledWidth();
                    final int reqHeight = Integer.parseInt(ELConfig.getString(ELConfig.getPwPositionY()));
                    final int reqWidth = Integer.parseInt(ELConfig.getString(ELConfig.getPwPositionX()));
                    final double health = player.getHealth();
                    final double food = player.getHungerManager().getFoodLevel();
                    final double air = player.getAir();

                    if (ELConfig.get(ELConfig.Health_Bar_Key)) {
                        matrixStack.push();
                        matrixStack.scale(1, 1, inGameHud.getZOffset());

                        DrawableHelper.fill(matrixStack, 0, 0, width, Integer.parseInt(ELConfig.getString(ELConfig.getHbWidth())), getColor(health));
                        matrixStack.pop();

                    }

                    if (ELConfig.get(ELConfig.Player_Warning_Key)) {
                        healthWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth,
                                health);

                        foodWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth,
                                health, food);

                        airWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth, air);

                    }

                }
            }

        });
    }

    private boolean kbPressed() {
        final PlayerEntity player = client.player;
        final InGameHud inGameHud = client.inGameHud;
        final TextRenderer textRenderer = client.textRenderer;
        final MatrixStack matrixStack = new MatrixStack();
        if (player == null)
            return false;
        double health = player.getHealth();
        double hunger = player.getHungerManager().getFoodLevel();
        int height = client.getWindow().getScaledHeight();
        int width = client.getWindow().getScaledWidth();
        int reqHeight = Integer.parseInt(ELConfig.getString(ELConfig.getHnhPositionY()));
        int reqWidth = Integer.parseInt(ELConfig.getString(ELConfig.getHnhPositionX()));
        matrixStack.push();
        matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getHnhScale())), Integer.parseInt(ELConfig.getString(ELConfig.getHnhScale())), inGameHud.getZOffset());

        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer,
                new LiteralText("" + (double) Math.round((health / 2) * 10) / 10
                        + "X Health    " + (double) Math.round((hunger / 2) * 10) / 10 + "X Food"),
                (int) (width * reqWidth/100), (int) (height * reqHeight/100), colors(ELConfig.getString(ELConfig.getHnhColor())));
        matrixStack.pop();

        return true;
    }

    private int getColor(double health) {
        if (health <= 20.0 && health > 12.0) {
            return colors("green");
        } else if (health <= 12.0 && health > 6.0) {
            return colors("brown");
        } else if (health <= 6.0) {
            return colors("red");
        }
        return 0;

    }

    private void showCoord() {
        final PlayerEntity player = client.player;
        final InGameHud inGameHud = client.inGameHud;
        final TextRenderer textRenderer = client.textRenderer;
        final MatrixStack matrixStack = new MatrixStack();

        RenderSystem.enableBlend();

        

        if (player == null)
            return;

        if(ELConfig.get(ELConfig.Player_Coordinates_Key)){
            Vec3d pos = player.getPos();
            String posX = ((double)pos.x)+"";
            String posY = ((double)pos.y)+"";
            String posZ = ((double)pos.z)+"";
            posX = posX.substring(0, posX.indexOf("."));
            posY = posY.substring(0, posY.indexOf("."));
            posZ = posZ.substring(0, posZ.indexOf("."));
            String posString= "Position: "+posX+" | "+posY+" | "+posZ;
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX())), Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY())), (posString.length()*5)-2, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()))+14, colors(ELConfig.getString(ELConfig.getPcBgColor())));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, posString, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX()))+3, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()))+3, colors(ELConfig.getString(ELConfig.getPcColor())));
            matrixStack.pop();

        }

        if(ELConfig.get(ELConfig.Player_Direction_Key)){
            String dirString="Direction: " + player.getHorizontalFacing().asString()+" ("+getDirection(player.getHorizontalFacing().asString())+")";
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX())), Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY())), (dirString.length()*5), Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()))+14, colors(ELConfig.getString(ELConfig.getPdBgColor())));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, dirString, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX()))+3, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()))+3, colors(ELConfig.getString(ELConfig.getPdColor())));
            matrixStack.pop();
        }
        

    }

    private String getDirection(String dir){
        dir = dir.toLowerCase().trim();
        switch (dir) {
            case "north":
                return "-Z";
            case "south":
                return "+Z";
            case "west":
                return "-X";
            case "east":
                return "+X";
            default:
                return "";
        }
    }

    public static int colors(String c){
        c = c.trim().toLowerCase();
        switch (c) {
            case "red":
                return 0xffdb0000;
            case "grey":
                return 0xff808080;
            case "purple":
                return 0xff800080;
            case "white":
                return 0xfff0f0f0;
            case "black":
                return 0xff0f0f0f;
            case "pink":
                return 0xffff0f87;
            case "blue":
                return 0xff1f1fff;
            case "green":
                return 0xff00bd00;
            case "yellow":
                return 0xffffff3d;
            case "orange":
                return 0xffe09200;
            case "brown":
                return 0xff610000;
            case "lightgrey":
                return 0xffececec;
            default:
                return 0xffff0000;
        }
    }

    private void healthWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health){
        double firstTH;
        double secondTH;
        if( Double.parseDouble(ELConfig.getString(ELConfig.getPwHtFTh())) > Double.parseDouble(ELConfig.getString(ELConfig.getPwHtSTh())) ){
            firstTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtFTh())) * 2;
            secondTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtSTh())) * 2;
        } else {
            firstTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtSTh())) * 2;
            secondTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtFTh())) * 2; 
        }
        if( health>=firstTH && health>=secondTH && healthWarningFlag>0 && obj[0].isAlive() && healthWarningAfterFlag<=0 ){
            obj[0].stopThread();
            healthWarningFlag = 0;
            obj[0] = new CustomWait();
            obj[0].setWait(10000, 4,this.client);
            obj[0].startThread();
            obj[0].start();
        }
        if (health < firstTH && health > secondTH && healthWarningFlag <= 0 && healthWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            
            if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                player.sendMessage(new LiteralText("Health Low"), true);
            } 
            if(ELConfig.get(ELConfig.getPwSoundStatus())){
                player.playSound(getSoundEvent("anvil_land"),SoundCategory.PLAYERS,(float)1,(float) 1);
            }
                    
            matrixStack.pop();
            if(obj[0].isAlive()) obj[0].stopThread();
            obj[0] = new CustomWait();
            obj[0].setWait(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000, 1,this.client);
            obj[0].startThread();
            obj[0].start();
        }

        if (health < secondTH && health > 0 && healthWarningFlag<=0 && healthWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
            if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                player.sendMessage(new LiteralText("Health Low"), true);
            } 
            if(ELConfig.get(ELConfig.getPwSoundStatus())){
                player.playSound(getSoundEvent("anvil_land"),SoundCategory.PLAYERS,(float)1,(float) 1);
            }
            if(obj[0].isAlive()) obj[0].stopThread();
            obj[0] = new CustomWait();
            obj[0].setWait(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000, 1,this.client);
            obj[0].startThread();
            obj[0].start();
            
        }

        if (healthWarningFlag >= ((Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1000) && healthWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
        }


    }

    private void foodWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health,double food){
        double foodTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwFtth())) * 2;
        double firstTH;
        if(food>=foodTH && foodWarningFlag>0 && obj[1].isAlive() && foodWarningAfterFlag<=0){
            obj[1].stopThread();
            foodWarningFlag = 0;
            obj[1] = new CustomWait();
            obj[1].setWait(10000, 5,this.client);
            obj[1].startThread();
            obj[1].start();
        }
        if( Double.parseDouble(ELConfig.getString(ELConfig.getPwHtFTh())) > Double.parseDouble(ELConfig.getString(ELConfig.getPwHtSTh())) ){
            firstTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtFTh())) * 2;
        } else {
            firstTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwHtSTh())) * 2; 
        }
        if (food < foodTH && food > 0 && health >=firstTH && foodWarningFlag <=0 && foodWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
            if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                player.sendMessage(new LiteralText("Food Low"), true);
            } 
            if(ELConfig.get(ELConfig.getPwSoundStatus())) {
                player.playSound(getSoundEvent("anvil_land"),SoundCategory.PLAYERS,(float)1,(float) 1);
            }
            if(obj[1].isAlive()) obj[1].stopThread();
            obj[1] = new CustomWait();
            obj[1].setWait(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000, 2,this.client);
            obj[1].startThread();
            obj[1].start();
        }

        if (foodWarningFlag >= ((Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1000) && healthWarningFlag<=0 && foodWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
        }


    }

    private void airWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double air){
        double airTH = Double.parseDouble(ELConfig.getString(ELConfig.getPwAtth())) * 30;

        if(air>=airTH && airWarningFlag>0 && obj[2].isAlive() && airWarningAfterFlag<=0){
            obj[2].stopThread();
            airWarningFlag = 0;
            obj[2] = new CustomWait();
            obj[2].setWait(10000, 6,this.client);
            obj[2].startThread();
            obj[2].start();
        }

        if (air < airTH && air > 0 && foodWarningFlag<(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1500 && healthWarningFlag<(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1500 && airWarningFlag <=0 && airWarningAfterFlag<=0 ) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
            if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                player.sendMessage(new LiteralText("Air Low"), true);
            } 
            if(ELConfig.get(ELConfig.getPwSoundStatus())) {
                player.playSound(getSoundEvent("anvil_land"),SoundCategory.PLAYERS,(float)1,(float) 1);
            }
            
            if(obj[2].isAlive()) obj[2].stopThread();
            obj[2] = new CustomWait();
            obj[2].setWait(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000, 3,this.client);
            obj[2].startThread();
            obj[2].start();
        }

        if (airWarningFlag >= ((Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1000) && foodWarningFlag<(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1500 && healthWarningFlag<(Integer.parseInt(ELConfig.getString(ELConfig.getPwTimeout()))*1000)-1500 && airWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())),Integer.parseInt(ELConfig.getString(ELConfig.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor())));
            matrixStack.pop();
        }
        

    }

    private SoundEvent getSoundEvent(String val){
        val = val.trim().toLowerCase();
        switch (val) {
            case "anvil_land":
                return SoundEvents.BLOCK_ANVIL_LAND;
            default:
                return SoundEvents.BLOCK_ANVIL_LAND;
        }
    }

}