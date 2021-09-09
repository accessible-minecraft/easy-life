package net.shoaibkhan.easy.life;

import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;

import io.github.cottonmc.cotton.gui.widget.data.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
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
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;

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

    public ClientMod(KeyBinding kb, KeyBinding coord, KeyBinding CONFIG_KEY,KeyBinding position_narrator,KeyBinding direction_narrator, KeyBinding narrator_menu) {
        client = MinecraftClient.getInstance();
        

        HudRenderCallback.EVENT.register((__, ___) -> {
            if(client.player== null) return;

            while(CONFIG_KEY.wasPressed()){
                client.openScreen(new ConfigScreen(new ConfigGui(client.player,client)));
                return;
            }

            while(narrator_menu.wasPressed()){
                client.openScreen(new ConfigScreen(new NarratorMenuGui(client.player,client)));
                return;
            }

            while(position_narrator.wasPressed()){
                final PlayerEntity player = client.player;
                Vec3d pos = player.getPos();
                String posX = ((double)pos.x)+"";
                String posY = ((double)pos.y)+"";
                String posZ = ((double)pos.z)+"";
                posX = posX.substring(0, posX.indexOf("."));
                posY = posY.substring(0, posY.indexOf("."));
                posZ = posZ.substring(0, posZ.indexOf("."));
                if(posX.contains("-")) posX = posX.replace("-", "negative");
                if(posY.contains("-")) posY = posY.replace("-", "negative");
                if(posZ.contains("-")) posZ = posZ.replace("-", "negative");
                String text = "Position is, "+posX+"x, "+posY+"y, "+posZ+"z";
                player.sendMessage(new LiteralText(text), true);
            }

            while(direction_narrator.wasPressed()){
                final PlayerEntity player = client.player;
                String text = "Player is facing, "+player.getHorizontalFacing().asString();
                player.sendMessage(new LiteralText(text), true);
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
        int color = colors(ELConfig.getString(ELConfig.getHnhColor()),ELConfig.getOpacity(ELConfig.getHnhColorOpacity()));
        try {
            if(ELConfig.get(ELConfig.getHnhColorCustom())) color = colors(ELConfig.getString(ELConfig.getHnhColorCustomVal()),ELConfig.getOpacity(ELConfig.getHnhColorOpacity()));
        } catch (Exception e) {
            color = colors(ELConfig.getString(ELConfig.getHnhColor()),ELConfig.getOpacity(ELConfig.getHnhColorOpacity()));
        }        
        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer,
                new LiteralText("" + (double) Math.round((health / 2) * 10) / 10
                        + "X Health    " + (double) Math.round((hunger / 2) * 10) / 10 + "X Food"),
                (int) (width * reqWidth/100), (int) (height * reqHeight/100), color);
        matrixStack.pop();

        return true;
    }

    private int getColor(double health) {
        if (health <= 20.0 && health > 12.0) {
            return colors("green",100);
        } else if (health <= 12.0 && health > 6.0) {
            return colors("brown",100);
        } else if (health <= 6.0) {
            return colors("red",100);
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

        if(ELConfig.get(ELConfig.getPlayerCoordinatesKey())){
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
            DrawableHelper.fill(matrixStack, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX())), Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY())), (posString.length()*5)-2, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()))+14, colors(ELConfig.getString(ELConfig.getPcBgColor()),ELConfig.getOpacity(ELConfig.getPcBgColorOpacity())));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            int color = colors(ELConfig.getString(ELConfig.getPcColor()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
            try {
                if(ELConfig.get(ELConfig.getPcColorCustom())){
                    color = colors(ELConfig.getString(ELConfig.getPcColorCustomVal()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
                }
            } catch (Exception e) {
                color = colors(ELConfig.getString(ELConfig.getPcColor()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
            }
            textRenderer.draw(matrixStack, posString, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX()))+3, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()))+3, color);
            matrixStack.pop();

        }

        if(ELConfig.get(ELConfig.getPlayerDirectionKey())){
            String dirString="Direction: " + player.getHorizontalFacing().asString()+" ("+getDirection(player.getHorizontalFacing().asString())+")";
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX())), Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY())), (dirString.length()*5), Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()))+14, colors(ELConfig.getString(ELConfig.getPdBgColor()),ELConfig.getOpacity(ELConfig.getPdBgColorOpacity())));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            int color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            try {
                if(ELConfig.get(ELConfig.getPdColorCustom())) color = colors(ELConfig.getString(ELConfig.getPdColorCustomVal()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            } catch (Exception e) {
                color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            }
            textRenderer.draw(matrixStack, dirString, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX()))+3, Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()))+3, color);
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

    public static int colors(String c,int o){
        c = c.trim().toLowerCase();
        switch (c) {
            case "red":
                if(o>95 && o<=100) return 0xffdb0000;
                else if(o>90 && o<=95) return 0xfadb0000;
                else if(o>85 && o<=90) return 0xe6db0000;
                else if(o>80 && o<=85) return 0xd9db0000;
                else if(o>75 && o<=80) return 0xccdb0000;
                else if(o>70 && o<=75) return 0xbfdb0000;
                else if(o>65 && o<=70) return 0xb3db0000;
                else if(o>60 && o<=65) return 0xa6db0000;
                else if(o>55 && o<=60) return 0x99db0000;
                else if(o>50 && o<=55) return 0x8cdb0000;
                else if(o>45 && o<=50) return 0x80db0000;
                else if(o>40 && o<=45) return 0x73db0000;
                else if(o>35 && o<=40) return 0x66db0000;
                else if(o>30 && o<=35) return 0x59db0000;
                else if(o>25 && o<=30) return 0x4ddb0000;
                else if(o>20 && o<=25) return 0x40db0000;
                else if(o>15 && o<=20) return 0x33db0000;
                else if(o>10 && o<=15) return 0x26db0000;
                else if(o>5 && o<=10) return 0x1adb0000;
                else if(o>0 && o<=5) return 0x0ddb0000;
                else if(o<=0) return 0x00db0000;
                return 0xffdb0000;
            case "grey":
                if(o>95 && o<=100) return 0xff808080;
                else if(o>90 && o<=95) return 0xfa808080;
                else if(o>85 && o<=90) return 0xe6808080;
                else if(o>80 && o<=85) return 0xd9808080;
                else if(o>75 && o<=80) return 0xcc808080;
                else if(o>70 && o<=75) return 0xbf808080;
                else if(o>65 && o<=70) return 0xb3808080;
                else if(o>60 && o<=65) return 0xa6808080;
                else if(o>55 && o<=60) return 0x99808080;
                else if(o>50 && o<=55) return 0x8c808080;
                else if(o>45 && o<=50) return 0x80808080;
                else if(o>40 && o<=45) return 0x73808080;
                else if(o>35 && o<=40) return 0x66808080;
                else if(o>30 && o<=35) return 0x59808080;
                else if(o>25 && o<=30) return 0x4d808080;
                else if(o>20 && o<=25) return 0x40808080;
                else if(o>15 && o<=20) return 0x33808080;
                else if(o>10 && o<=15) return 0x26808080;
                else if(o>5 && o<=10) return 0x1a808080;
                else if(o>0 && o<=5) return 0x0d808080;
                else if(o<=0) return 0x00808080;
                return 0xff808080;
            case "purple":
                if(o>95 && o<=100) return 0xff800080;
                else if(o>90 && o<=95) return 0xfa800080;
                else if(o>85 && o<=90) return 0xe6800080;
                else if(o>80 && o<=85) return 0xd9800080;
                else if(o>75 && o<=80) return 0xcc800080;
                else if(o>70 && o<=75) return 0xbf800080;
                else if(o>65 && o<=70) return 0xb3800080;
                else if(o>60 && o<=65) return 0xa6800080;
                else if(o>55 && o<=60) return 0x99800080;
                else if(o>50 && o<=55) return 0x8c800080;
                else if(o>45 && o<=50) return 0x80800080;
                else if(o>40 && o<=45) return 0x73800080;
                else if(o>35 && o<=40) return 0x66800080;
                else if(o>30 && o<=35) return 0x59800080;
                else if(o>25 && o<=30) return 0x4d800080;
                else if(o>20 && o<=25) return 0x40800080;
                else if(o>15 && o<=20) return 0x33800080;
                else if(o>10 && o<=15) return 0x26800080;
                else if(o>5 && o<=10) return 0x1a800080;
                else if(o>0 && o<=5) return 0x0d800080;
                else if(o<=0) return 0x00800080;
                return 0xff800080;
            case "white":
                if(o>95 && o<=100) return 0xfff0f0f0;
                else if(o>90 && o<=95) return 0xfaf0f0f0;
                else if(o>85 && o<=90) return 0xe6f0f0f0;
                else if(o>80 && o<=85) return 0xd9f0f0f0;
                else if(o>75 && o<=80) return 0xccf0f0f0;
                else if(o>70 && o<=75) return 0xbff0f0f0;
                else if(o>65 && o<=70) return 0xb3f0f0f0;
                else if(o>60 && o<=65) return 0xa6f0f0f0;
                else if(o>55 && o<=60) return 0x99f0f0f0;
                else if(o>50 && o<=55) return 0x8cf0f0f0;
                else if(o>45 && o<=50) return 0x80f0f0f0;
                else if(o>40 && o<=45) return 0x73f0f0f0;
                else if(o>35 && o<=40) return 0x66f0f0f0;
                else if(o>30 && o<=35) return 0x59f0f0f0;
                else if(o>25 && o<=30) return 0x4df0f0f0;
                else if(o>20 && o<=25) return 0x40f0f0f0;
                else if(o>15 && o<=20) return 0x33f0f0f0;
                else if(o>10 && o<=15) return 0x26f0f0f0;
                else if(o>5 && o<=10) return 0x1af0f0f0;
                else if(o>0 && o<=5) return 0x0df0f0f0;
                else if(o<=0) return 0x00f0f0f0;
                return 0xfff0f0f0;
            case "black":
                if(o>95 && o<=100) return 0xff0f0f0f;
                else if(o>90 && o<=95) return 0xfa0f0f0f;
                else if(o>85 && o<=90) return 0xe60f0f0f;
                else if(o>80 && o<=85) return 0xd90f0f0f;
                else if(o>75 && o<=80) return 0xcc0f0f0f;
                else if(o>70 && o<=75) return 0xbf0f0f0f;
                else if(o>65 && o<=70) return 0xb30f0f0f;
                else if(o>60 && o<=65) return 0xa60f0f0f;
                else if(o>55 && o<=60) return 0x990f0f0f;
                else if(o>50 && o<=55) return 0x8c0f0f0f;
                else if(o>45 && o<=50) return 0x800f0f0f;
                else if(o>40 && o<=45) return 0x730f0f0f;
                else if(o>35 && o<=40) return 0x660f0f0f;
                else if(o>30 && o<=35) return 0x590f0f0f;
                else if(o>25 && o<=30) return 0x4d0f0f0f;
                else if(o>20 && o<=25) return 0x400f0f0f;
                else if(o>15 && o<=20) return 0x330f0f0f;
                else if(o>10 && o<=15) return 0x260f0f0f;
                else if(o>5 && o<=10) return 0x1a0f0f0f;
                else if(o>0 && o<=5) return 0x0d0f0f0f;
                else if(o<=0) return 0x000f0f0f;
                return 0xff0f0f0f;
            case "pink":
                if(o>95 && o<=100) return 0xffff0f87;
                else if(o>90 && o<=95) return 0xfaff0f87;
                else if(o>85 && o<=90) return 0xe6ff0f87;
                else if(o>80 && o<=85) return 0xd9ff0f87;
                else if(o>75 && o<=80) return 0xccff0f87;
                else if(o>70 && o<=75) return 0xbfff0f87;
                else if(o>65 && o<=70) return 0xb3ff0f87;
                else if(o>60 && o<=65) return 0xa6ff0f87;
                else if(o>55 && o<=60) return 0x99ff0f87;
                else if(o>50 && o<=55) return 0x8cff0f87;
                else if(o>45 && o<=50) return 0x80ff0f87;
                else if(o>40 && o<=45) return 0x73ff0f87;
                else if(o>35 && o<=40) return 0x66ff0f87;
                else if(o>30 && o<=35) return 0x59ff0f87;
                else if(o>25 && o<=30) return 0x4dff0f87;
                else if(o>20 && o<=25) return 0x40ff0f87;
                else if(o>15 && o<=20) return 0x33ff0f87;
                else if(o>10 && o<=15) return 0x26ff0f87;
                else if(o>5 && o<=10) return 0x1aff0f87;
                else if(o>0 && o<=5) return 0x0dff0f87;
                else if(o<=0) return 0x00ff0f87;
                return 0xffff0f87;
            case "blue":
                if(o>95 && o<=100) return 0xff1f1fff;
                else if(o>90 && o<=95) return 0xfa1f1fff;
                else if(o>85 && o<=90) return 0xe61f1fff;
                else if(o>80 && o<=85) return 0xd91f1fff;
                else if(o>75 && o<=80) return 0xcc1f1fff;
                else if(o>70 && o<=75) return 0xbf1f1fff;
                else if(o>65 && o<=70) return 0xb31f1fff;
                else if(o>60 && o<=65) return 0xa61f1fff;
                else if(o>55 && o<=60) return 0x991f1fff;
                else if(o>50 && o<=55) return 0x8c1f1fff;
                else if(o>45 && o<=50) return 0x801f1fff;
                else if(o>40 && o<=45) return 0x731f1fff;
                else if(o>35 && o<=40) return 0x661f1fff;
                else if(o>30 && o<=35) return 0x591f1fff;
                else if(o>25 && o<=30) return 0x4d1f1fff;
                else if(o>20 && o<=25) return 0x401f1fff;
                else if(o>15 && o<=20) return 0x331f1fff;
                else if(o>10 && o<=15) return 0x261f1fff;
                else if(o>5 && o<=10) return 0x1a1f1fff;
                else if(o>0 && o<=5) return 0x0d1f1fff;
                else if(o<=0) return 0x001f1fff;
                return 0xff1f1fff;
            case "green":
                if(o>95 && o<=100) return 0xff00bd00;
                else if(o>90 && o<=95) return 0xfa00bd00;
                else if(o>85 && o<=90) return 0xe600bd00;
                else if(o>80 && o<=85) return 0xd900bd00;
                else if(o>75 && o<=80) return 0xcc00bd00;
                else if(o>70 && o<=75) return 0xbf00bd00;
                else if(o>65 && o<=70) return 0xb300bd00;
                else if(o>60 && o<=65) return 0xa600bd00;
                else if(o>55 && o<=60) return 0x9900bd00;
                else if(o>50 && o<=55) return 0x8c00bd00;
                else if(o>45 && o<=50) return 0x8000bd00;
                else if(o>40 && o<=45) return 0x7300bd00;
                else if(o>35 && o<=40) return 0x6600bd00;
                else if(o>30 && o<=35) return 0x5900bd00;
                else if(o>25 && o<=30) return 0x4d00bd00;
                else if(o>20 && o<=25) return 0x4000bd00;
                else if(o>15 && o<=20) return 0x3300bd00;
                else if(o>10 && o<=15) return 0x2600bd00;
                else if(o>5 && o<=10) return 0x1a00bd00;
                else if(o>0 && o<=5) return 0x0d00bd00;
                else if(o<=0) return 0x0000bd00;
                return 0xff00bd00;
            case "yellow":
                if(o>95 && o<=100) return 0xffffff3d;
                else if(o>90 && o<=95) return 0xfaffff3d;
                else if(o>85 && o<=90) return 0xe6ffff3d;
                else if(o>80 && o<=85) return 0xd9ffff3d;
                else if(o>75 && o<=80) return 0xccffff3d;
                else if(o>70 && o<=75) return 0xbfffff3d;
                else if(o>65 && o<=70) return 0xb3ffff3d;
                else if(o>60 && o<=65) return 0xa6ffff3d;
                else if(o>55 && o<=60) return 0x99ffff3d;
                else if(o>50 && o<=55) return 0x8cffff3d;
                else if(o>45 && o<=50) return 0x80ffff3d;
                else if(o>40 && o<=45) return 0x73ffff3d;
                else if(o>35 && o<=40) return 0x66ffff3d;
                else if(o>30 && o<=35) return 0x59ffff3d;
                else if(o>25 && o<=30) return 0x4dffff3d;
                else if(o>20 && o<=25) return 0x40ffff3d;
                else if(o>15 && o<=20) return 0x33ffff3d;
                else if(o>10 && o<=15) return 0x26ffff3d;
                else if(o>5 && o<=10) return 0x1affff3d;
                else if(o>0 && o<=5) return 0x0dffff3d;
                else if(o<=0) return 0x00ffff3d;
                return 0xffffff3d;
            case "orange":
                if(o>95 && o<=100) return 0xffe09200;
                else if(o>90 && o<=95) return 0xfae09200;
                else if(o>85 && o<=90) return 0xe6e09200;
                else if(o>80 && o<=85) return 0xd9e09200;
                else if(o>75 && o<=80) return 0xcce09200;
                else if(o>70 && o<=75) return 0xbfe09200;
                else if(o>65 && o<=70) return 0xb3e09200;
                else if(o>60 && o<=65) return 0xa6e09200;
                else if(o>55 && o<=60) return 0x99e09200;
                else if(o>50 && o<=55) return 0x8ce09200;
                else if(o>45 && o<=50) return 0x80e09200;
                else if(o>40 && o<=45) return 0x73e09200;
                else if(o>35 && o<=40) return 0x66e09200;
                else if(o>30 && o<=35) return 0x59e09200;
                else if(o>25 && o<=30) return 0x4de09200;
                else if(o>20 && o<=25) return 0x40e09200;
                else if(o>15 && o<=20) return 0x33e09200;
                else if(o>10 && o<=15) return 0x26e09200;
                else if(o>5 && o<=10) return 0x1ae09200;
                else if(o>0 && o<=5) return 0x0de09200;
                else if(o<=0) return 0x00e09200;
                return 0xffe09200;
            case "brown":
                if(o>95 && o<=100) return 0xff610000;
                else if(o>90 && o<=95) return 0xfa610000;
                else if(o>85 && o<=90) return 0xe6610000;
                else if(o>80 && o<=85) return 0xd9610000;
                else if(o>75 && o<=80) return 0xcc610000;
                else if(o>70 && o<=75) return 0xbf610000;
                else if(o>65 && o<=70) return 0xb3610000;
                else if(o>60 && o<=65) return 0xa6610000;
                else if(o>55 && o<=60) return 0x99610000;
                else if(o>50 && o<=55) return 0x8c610000;
                else if(o>45 && o<=50) return 0x80610000;
                else if(o>40 && o<=45) return 0x73610000;
                else if(o>35 && o<=40) return 0x66610000;
                else if(o>30 && o<=35) return 0x59610000;
                else if(o>25 && o<=30) return 0x4d610000;
                else if(o>20 && o<=25) return 0x40610000;
                else if(o>15 && o<=20) return 0x33610000;
                else if(o>10 && o<=15) return 0x26610000;
                else if(o>5 && o<=10) return 0x1a610000;
                else if(o>0 && o<=5) return 0x0d610000;
                else if(o<=0) return 0x00610000;
                return 0xff610000;
            case "lightgrey":
                if(o>95 && o<=100) return 0xffececec;
                else if(o>90 && o<=95) return 0xfaececec;
                else if(o>85 && o<=90) return 0xe6ececec;
                else if(o>80 && o<=85) return 0xd9ececec;
                else if(o>75 && o<=80) return 0xccececec;
                else if(o>70 && o<=75) return 0xbfececec;
                else if(o>65 && o<=70) return 0xb3ececec;
                else if(o>60 && o<=65) return 0xa6ececec;
                else if(o>55 && o<=60) return 0x99ececec;
                else if(o>50 && o<=55) return 0x8cececec;
                else if(o>45 && o<=50) return 0x80ececec;
                else if(o>40 && o<=45) return 0x73ececec;
                else if(o>35 && o<=40) return 0x66ececec;
                else if(o>30 && o<=35) return 0x59ececec;
                else if(o>25 && o<=30) return 0x4dececec;
                else if(o>20 && o<=25) return 0x40ececec;
                else if(o>15 && o<=20) return 0x33ececec;
                else if(o>10 && o<=15) return 0x26ececec;
                else if(o>5 && o<=10) return 0x1aececec;
                else if(o>0 && o<=5) return 0x0dececec;
                else if(o<=0) return 0x00ececec;
                return 0xffececec;
            default:
                if(c.contains("#")) c = c.replace("#", "");
                if(c.contains("0x")) c = c.replace("0x", "");
                int hex = Integer.parseInt(c,16);
                int r = (hex & 0xff0000) >> 16;
                int g = (hex & 0xff00) >> 8;
                int b = (hex & 0xff);
                Color color = Color.rgb((int)(o*2.55), r, g, b);
                return color.toRgb();
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
            
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(ELConfig.getString(ELConfig.getPwColor()),100));
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