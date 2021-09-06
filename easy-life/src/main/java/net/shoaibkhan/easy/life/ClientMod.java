package net.shoaibkhan.easy.life;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.features.DirectionNarrator;
import net.shoaibkhan.easy.life.features.PlayerWarnings;
import net.shoaibkhan.easy.life.features.PositionAndDirectionOverlay;
import net.shoaibkhan.easy.life.features.PositionNarrator;
import net.shoaibkhan.easy.life.gui.ConfigGui;
import net.shoaibkhan.easy.life.gui.ConfigScreen;
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;
import net.shoaibkhan.easy.life.utils.KeyBinds;

//import net.minecraft.client.options.KeyBinding;


@Environment(EnvType.CLIENT)
public class ClientMod {
    private final MinecraftClient client;
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

            if (coordFlag && (ELConfig.get(ELConfig.getPlayerCoordinatesKey()) || ELConfig.get(ELConfig.getPlayerDirectionKey()))) {
                new PositionAndDirectionOverlay(client);
            }

            new DirectionNarrator(client);

        }

    }

    private void keyPressHandler(){

        if(client.currentScreen == null) {
            if(KeyBinds.CONFIG_MENU_KEY.getKeyBind().wasPressed()){
                Screen screen = new ConfigScreen(new ConfigGui(client.player,client), "Easy Life Configuration", client.player);
                client.openScreen(screen);
                return;
            }

            if(KeyBinds.F4_MENU_KEY.getKeyBind().wasPressed()){
                Screen screen = new ConfigScreen(new NarratorMenuGui(client.player,client), "F4 Menu", client.player);
                client.openScreen(screen);
                return;
            }

            // Toggle Position and Direction Overlay
            while (KeyBinds.PLAYER_COORDINATES_AND_DIRECTION_OVERLAY_KEY.getKeyBind().wasPressed() && (ELConfig.get(ELConfig.getPlayerCoordinatesKey()) || ELConfig.get(ELConfig.getPlayerDirectionKey()))) {
                coordFlag = !coordFlag;
            }

            while (KeyBinds.HEALTH_N_HUNGER_KEY.getKeyBind().wasPressed() && ELConfig.get(ELConfig.getHealthNHungerKey())) {
                if (ELConfig.get(ELConfig.getNarratorSupportKey())) {
                    assert client.player != null;
                    double health = client.player.getHealth();
                    double hunger = client.player.getHungerManager().getFoodLevel();
                    client.player.sendMessage(new LiteralText("health is " + ((double) Math.round((health / 2) * 10) / 10) + " Hunger is " + ((double) Math.round((hunger / 2) * 10) / 10)), true);
                }
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