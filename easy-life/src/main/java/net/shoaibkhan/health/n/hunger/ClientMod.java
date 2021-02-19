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

            if (coordFlag) {
                showCoord();
            }

            while (coord.wasPressed()) {
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
        matrixStack.push();
        matrixStack.scale(2, 2, inGameHud.getZOffset());

        DrawableHelper.drawTextWithShadow(matrixStack, textRenderer,
                new LiteralText(config.getHealth_n_hunger_status() + "" + (double) Math.round((health / 2) * 10) / 10
                        + "X Health    " + (double) Math.round((hunger / 2) * 10) / 10 + "X Food Level"),
                (int) (width * 0.1), (int) (height * 0.35), 0xff0000);
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
        Vec3d pos = player.getPos();
        String posString= "Position: "+(double) Math.round(pos.x*100)/100+" | "+(double) Math.round(pos.y*100)/100+" | "+(double) Math.round(pos.z*100)/100;
        
        String dirString="Direction: " + player.getHorizontalFacing().asString()+" ("+getDirection(player.getHorizontalFacing().asString())+")";
        
        
        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        DrawableHelper.fill(matrixStack, 0, 40, (posString.length()*5)-5, 54, 0xff000000);
        matrixStack.pop();
        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        textRenderer.draw(matrixStack, posString, 3, 43, 0xffffffff);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        DrawableHelper.fill(matrixStack, 0, 57, (dirString.length()*5), 71, 0xff000000);
        matrixStack.pop();
        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        textRenderer.draw(matrixStack, dirString, 3, 60, 0xffffffff);
        matrixStack.pop();

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