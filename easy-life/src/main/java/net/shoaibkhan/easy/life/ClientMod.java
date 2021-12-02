package net.shoaibkhan.easy.life;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.features.*;
import net.shoaibkhan.easy.life.gui.ConfigGui;
import net.shoaibkhan.easy.life.gui.ConfigScreen;
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;
import net.shoaibkhan.easy.life.utils.KeyBinds;



@Environment(EnvType.CLIENT)
public class ClientMod {
    private final MinecraftClient client;
    private BiomeIndicator biomeIndicator = new BiomeIndicator();
    private boolean coordFlag = false;
    public static boolean isAltPressed,isXPressed, isCPressed, isZPressed;

    public ClientMod() {
		this.client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register(this::hudRenderCallbackMethod);
    }

    private void hudRenderCallbackMethod(MatrixStack matrixStack, float tickDelta){

        if (client.player != null) {

            initializeData();

            keyPressHandler();

            new PlayerWarnings(client);

            new PositionNarrator(client);

            if (coordFlag && (Config.get(Config.getPlayerCoordinatesKey()) || Config.get(Config.getPlayerDirectionKey()))) {
                new PositionAndDirectionOverlay(client);
            }

            new DirectionNarrator(client);

            biomeIndicatorHandler();

        }

    }

    private void biomeIndicatorHandler() {
        if(Config.get(Config.getBiome_Indicator_Key())) {
            if (!biomeIndicator.isAlive()) {
                biomeIndicator = new BiomeIndicator();
                biomeIndicator.startThread();
            }
        } else if(biomeIndicator.isAlive()){
            biomeIndicator.stopThread();
            biomeIndicator = new BiomeIndicator();
        }
    }

    private void keyPressHandler(){

        if(client.currentScreen == null) {
            assert client.player != null;
            if(KeyBinds.CONFIG_MENU_KEY.getKeyBind().wasPressed()){
                Screen screen = new ConfigScreen(new ConfigGui(client.player,client), "Easy Life Configuration", client.player);
                client.setScreen(screen);
                return;
            }

            if(KeyBinds.F4_MENU_KEY.getKeyBind().wasPressed()){
                Screen screen = new ConfigScreen(new NarratorMenuGui(client.player,client), "F4 Menu", client.player);
                client.setScreen(screen);
                return;
            }

            // Toggle Position and Direction Overlay
            if (KeyBinds.PLAYER_COORDINATES_AND_DIRECTION_OVERLAY_KEY.getKeyBind().wasPressed() && (Config.get(Config.getPlayerCoordinatesKey()) || Config.get(Config.getPlayerDirectionKey()))) {
                coordFlag = !coordFlag;
            }

            if (KeyBinds.HEALTH_N_HUNGER_KEY.getKeyBind().wasPressed()) {
                assert client.player != null;
                double health = client.player.getHealth();
                double hunger = client.player.getHungerManager().getFoodLevel();
                String toNarrate = "health is " + ((double) Math.round((health / 2) * 10) / 10) + " Hunger is " + ((double) Math.round((hunger / 2) * 10) / 10);
                Initial.narrate(toNarrate);
            }
        }
    }

    private void initializeData() {
        isAltPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode())||InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.right.alt").getCode()));
        isXPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.x").getCode()));
        isCPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.c").getCode()));
        isZPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.z").getCode()));
    }

}