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
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ClientMod {
    private MinecraftClient client;
    private float tickCount = 0f;
    private boolean coordFlag = false;
    private Config config;

    public ClientMod(KeyBinding kb, KeyBinding coord) {
        client = MinecraftClient.getInstance();
        config = Initial.config;

        HudRenderCallback.EVENT.register((__, ___) -> {

            if (tickCount > 0f) {
                if (this.kbPressed() && (config.getHealth_n_hunger_status()).equals("on")) {
                    tickCount -= client.getTickDelta();
                }
            }

            if (coordFlag && ((config.getPlayer_coordination_status().trim().toLowerCase()).equalsIgnoreCase("on")||(config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on"))) {
                showCoord();
            }

            while (coord.wasPressed() && ((config.getPlayer_coordination_status().trim().toLowerCase()).equalsIgnoreCase("on")||(config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on"))) {
                coordFlag = !coordFlag;
            }

            while (kb.wasPressed() && (config.getHealth_n_hunger_status()).equals("on")) {
                if (this.kbPressed()) {
                    tickCount = 120f;
                }
            }

            if (client.world.isClient && (config.getHealth_bar_status()).equalsIgnoreCase("on")) {
                final PlayerEntity player = client.player;
                final InGameHud inGameHud = client.inGameHud;
                final MatrixStack matrixStack = new MatrixStack();
                if (player != null) {
                    double health = client.player.getHealth();
                    int width = client.getWindow().getScaledWidth();
                    matrixStack.push();
                    matrixStack.scale(1, 1, inGameHud.getZOffset());

                    DrawableHelper.fill(matrixStack, 0, 0, width, 4, getColor(health));
                    matrixStack.pop();
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

        double health = client.player.getHealth();
        double hunger = client.player.getHungerManager().getFoodLevel();
        int height = client.getWindow().getScaledHeight();
        int width = client.getWindow().getScaledWidth();
        int reqHeight = config.getHealth_n_hunger_positiony();
        int reqWidth = config.getHealth_n_hunger_positionx();
        matrixStack.push();
        matrixStack.scale(2, 2, inGameHud.getZOffset());

        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer,
                new LiteralText( "" + (double) Math.round((health / 2) * 10) / 10
                        + "X Health    " + (double) Math.round((hunger / 2) * 10) / 10 + "X Food"),
                (int) (width * reqWidth/100), (int) (height * reqHeight/100), 0xff0000);
        matrixStack.pop();
        return true;
    }

    private int getColor(double health) {
        if (health <= 20.0 && health > 12.0) {
            return 0xff008000;
        } else if (health <= 12.0 && health > 8.0) {
            return 0xffff0000;
        } else if (health <= 8.0) {
            return 0xff800000;
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
            DrawableHelper.fill(matrixStack, config.getPlayer_coordination_positionx(), config.getPlayer_coordination_positiony(), (posString.length()*5)-2, config.getPlayer_coordination_positiony()+14, 0xff000000);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, posString, config.getPlayer_coordination_positionx()+3, config.getPlayer_coordination_positiony()+3, 0xffffffff);
            matrixStack.pop();

        }

        if((config.getPlayer_direction_status().trim().toLowerCase()).equalsIgnoreCase("on")){
            String dirString="Direction: " + player.getHorizontalFacing().asString()+" ("+getDirection(player.getHorizontalFacing().asString())+")";
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, config.getPlayer_direction_positionx(), config.getPlayer_direction_positiony(), (dirString.length()*5), config.getPlayer_direction_positiony()+14, 0xff000000);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, dirString, config.getPlayer_direction_positionx()+3, config.getPlayer_direction_positiony()+3, 0xffffffff);
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



}