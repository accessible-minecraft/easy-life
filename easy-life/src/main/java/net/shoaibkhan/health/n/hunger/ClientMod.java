package net.shoaibkhan.health.n.hunger;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ClientMod {
    private MinecraftClient client;
    private float tickCount = 0f;
    private boolean coordFlag = false;
    private Config config;
    public static boolean flag = true;
    private CustomWait obj = new CustomWait();
    public static int healthWarningFlag = 0, foodWarningFlag = 0, airWarningFlag = 0;

    public ClientMod(KeyBinding kb, KeyBinding coord) {
        client = MinecraftClient.getInstance();
        config = Initial.config;

        HudRenderCallback.EVENT.register((__, ___) -> {

            if (tickCount > 0f) {
                if (this.kbPressed() && (config.getHealth_n_hunger_status()).equals("on")) {
                    tickCount -= client.getTickDelta();
                }
            }

            if (coordFlag && ((config.getPlayer_coordination_status().trim().toLowerCase()).equalsIgnoreCase("on")
                    || (config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on"))) {
                showCoord();
            }

            while (coord.wasPressed()
                    && ((config.getPlayer_coordination_status().trim().toLowerCase()).equalsIgnoreCase("on")
                            || (config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on"))) {
                coordFlag = !coordFlag;
            }

            while (kb.wasPressed() && (config.getHealth_n_hunger_status()).equals("on")) {
                if (this.kbPressed()) {
                    tickCount = 120f;
                }
            }

            if (!client.isPaused() && client.world.isClient) {
                final PlayerEntity player = client.player;
                final InGameHud inGameHud = client.inGameHud;
                final MatrixStack matrixStack = new MatrixStack();
                final TextRenderer textRenderer = client.textRenderer;

                if (player != null && ((config.getHealth_bar_status()).equalsIgnoreCase("on")
                        || (config.getPlayer_direction_status().equalsIgnoreCase("on")))) {

                    final int height = client.getWindow().getScaledHeight();
                    final int width = client.getWindow().getScaledWidth();
                    final int reqHeight = config.getPlayer_warning_positiony();
                    final int reqWidth = config.getPlayer_warning_positionx();
                    final double health = player.getHealth();
                    final double food = player.getHungerManager().getFoodLevel();
                    final double air = player.getAir();

                    if ((config.getHealth_bar_status()).equalsIgnoreCase("on")) {
                        matrixStack.push();
                        matrixStack.scale(1, 1, inGameHud.getZOffset());

                        DrawableHelper.fill(matrixStack, 0, 0, width, 4, getColor(health));
                        matrixStack.pop();

                    }

                    if ((config.getPlayer_warning_status().equalsIgnoreCase("on"))) {
                        healthWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth,
                                health);

                        foodWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth,
                                health, food);

                        airWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth,
                                health, food, air);

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
        int reqHeight = config.getHealth_n_hunger_positiony();
        int reqWidth = config.getHealth_n_hunger_positionx();
        matrixStack.push();
        matrixStack.scale(2, 2, inGameHud.getZOffset());

        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer,
                new LiteralText("" + (double) Math.round((health / 2) * 10) / 10
                        + "X Health    " + (double) Math.round((hunger / 2) * 10) / 10 + "X Food"),
                (int) (width * reqWidth/100), (int) (height * reqHeight/100), colors("red"));
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

        if((config.getPlayer_coordination_status().trim().toLowerCase()).equalsIgnoreCase("on")){
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
            DrawableHelper.fill(matrixStack, config.getPlayer_coordination_positionx(), config.getPlayer_coordination_positiony(), (posString.length()*5)-2, config.getPlayer_coordination_positiony()+14, colors("black"));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, posString, config.getPlayer_coordination_positionx()+3, config.getPlayer_coordination_positiony()+3, colors("white"));
            matrixStack.pop();

        }

        if((config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on")){
            String dirString="Direction: " + player.getHorizontalFacing().asString()+" ("+getDirection(player.getHorizontalFacing().asString())+")";
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, config.getPlayer_direction_positionx(), config.getPlayer_direction_positiony(), (dirString.length()*5), config.getPlayer_direction_positiony()+14, colors("black"));
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, dirString, config.getPlayer_direction_positionx()+3, config.getPlayer_direction_positiony()+3, colors("white"));
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

    private int colors(String c){
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
            default:
                return 0xffff0000;
        }
    }

    private void healthWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health){
        if (health < 10.0 && health > 6.0 && healthWarningFlag<=0) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 1);
            obj.start();
        }

        if (health < 6.0 && health > 0 && healthWarningFlag<=0) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            player.playSound(SoundEvents.BLOCK_ANVIL_LAND,SoundCategory.PLAYERS,(float)1,(float) 1);
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 1);
            obj.start();
        }

        if (healthWarningFlag >= ((config.getPlayer_warning_timeout()*1000)-1000) ){
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
        }


    }

    private void foodWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health,double food){
        if (food < 10.0 && food > 6.0 && health > 10 && foodWarningFlag <=0 ) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 2);
            obj.start();
        }

        if (food < 6.0 && food > 0 && health > 10 && foodWarningFlag <=0 ) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            player.playSound(SoundEvents.BLOCK_ANVIL_LAND,SoundCategory.PLAYERS,(float)1,(float) 1);
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 2);
            obj.start();
        }

        if (foodWarningFlag >= ((config.getPlayer_warning_timeout()*1000)-1000) && health > 10 ){
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
        }


    }

    private void airWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health,double food,double air){
        if (air < 150.0 && air > 90 && food>10 && health > 10 && airWarningFlag <=0 ) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 3);
            obj.start();
        }

        if (air < 90 && air > 0 && food>10 && health > 10 && airWarningFlag <=0 ) {
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
            player.playSound(SoundEvents.BLOCK_ANVIL_LAND,SoundCategory.PLAYERS,(float)1,(float) 1);
            obj = new CustomWait();
            obj.setWait(config.getPlayer_warning_timeout()*1000, 3);
            obj.start();
        }

        if (airWarningFlag >= ((config.getPlayer_warning_timeout()*1000)-1000) && food>10 && health > 10 ){
            matrixStack.push();
            matrixStack.scale(config.getPlayer_warning_scale(),config.getPlayer_warning_scale(), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"),
                    (int) (width * reqWidth / 100), (int) (height * reqHeight / 100), colors(config.getPlayer_warning_color()));
            matrixStack.pop();
        }
        

    }

}