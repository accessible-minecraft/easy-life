package net.shoaibkhan.easy.life;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.features.BiomeIndicator;
import net.shoaibkhan.easy.life.features.DirectionNarrator;
import net.shoaibkhan.easy.life.features.PlayerWarnings;
import net.shoaibkhan.easy.life.features.PositionAndDirectionOverlay;
import net.shoaibkhan.easy.life.features.PositionNarrator;
import net.shoaibkhan.easy.life.gui.ConfigGui;
import net.shoaibkhan.easy.life.gui.ConfigScreen;
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;
import net.shoaibkhan.easy.life.utils.KeyBinds;

public class ClientMod {
    public static boolean isAltPressed, isXPressed, isCPressed, isZPressed;
    private final MinecraftClient client;
    private BiomeIndicator biomeIndicator = new BiomeIndicator();
    private boolean coordFlag = false;

    public ClientMod() {
        this.client = MinecraftClient.getInstance();
        HudRenderCallback.EVENT.register(this::hudRenderCallbackMethod);
    }

    private void hudRenderCallbackMethod(MatrixStack matrixStack, float tickDelta) {

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
        if (Config.get(Config.getBiome_Indicator_Key())) {
            if (!biomeIndicator.isAlive()) {
                biomeIndicator = new BiomeIndicator();
                biomeIndicator.startThread();
            }
        } else if (biomeIndicator.isAlive()) {
            biomeIndicator.stopThread();
            biomeIndicator = new BiomeIndicator();
        }
    }

    private void keyPressHandler() {

        if (client.currentScreen == null) {
            assert client.player != null;
            if (KeyBinds.CONFIG_MENU_KEY.getKeyBind().wasPressed()) {
                Screen screen = new ConfigScreen(new ConfigGui(client.player, client), "configuration");
                client.setScreen(screen); // post 1.18
//                client.openScreen(screen); // pre 1.18
                return;
            }

            if (KeyBinds.F4_MENU_KEY.getKeyBind().wasPressed()) {
                Screen screen = new ConfigScreen(new NarratorMenuGui(client.player, client), "f4Menu");
                client.setScreen(screen); // post 1.18
//                client.openScreen(screen); // pre 1.18
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
                String toNarrate = I18n.translate("narrate.easylife.healthHunger", (double) Math.round((health / 2) * 10) / 10, (double) Math.round((hunger / 2) * 10) / 10);
                Initial.narrate(toNarrate);
            }
        }
    }

    private void initializeData() {
        isAltPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode()) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.right.alt").getCode()));
        isXPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.x").getCode()));
        isCPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.c").getCode()));
        isZPressed = (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.z").getCode()));
    }

}