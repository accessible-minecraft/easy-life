package net.shoaibkhan.easy.life;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
//import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.features.PlayerWarnings;
import net.shoaibkhan.easy.life.features.PositionNarrator;
import net.shoaibkhan.easy.life.gui.ConfigGui;
import net.shoaibkhan.easy.life.gui.ConfigScreen;
import net.shoaibkhan.easy.life.gui.NarratorMenuGui;

import static net.shoaibkhan.easy.life.utils.Colors.colors;


@Environment(EnvType.CLIENT)
public class ClientMod {
    private final MinecraftClient client;
    public static float tickCount = 0f;
    private boolean coordFlag = false;
    public static boolean isAltPressed,isXPressed, isCPressed, isZPressed;
    private final KeyBinding CONFIG_KEY;
    private final KeyBinding position_narrator;
    private final KeyBinding direction_narrator;
    private final KeyBinding narrator_menu;
    private final KeyBinding healthNhungerKey;
    private final KeyBinding coord;

    public ClientMod(KeyBinding healthNhungerKey, KeyBinding coord, KeyBinding CONFIG_KEY, KeyBinding position_narrator, KeyBinding direction_narrator, KeyBinding narrator_menu) {
		this.client = MinecraftClient.getInstance();
        this.CONFIG_KEY = CONFIG_KEY;
        this.position_narrator = position_narrator;
        this.direction_narrator = direction_narrator;
        this.narrator_menu = narrator_menu;
        this.healthNhungerKey = healthNhungerKey;
        this.coord = coord;
        HudRenderCallback.EVENT.register(this::hudRenderCallbackMethod);
    }

    private void hudRenderCallbackMethod(MatrixStack matrixStack, float tickDelta){

        if (client.player != null) {

            initializeData();

            keyPressHandler();

            new PlayerWarnings(client);

            new PositionNarrator(client, position_narrator);

        }

    }

    private void keyPressHandler(){

        if(client.currentScreen == null) {
            if(CONFIG_KEY.wasPressed()){
                Screen screen = new ConfigScreen(new ConfigGui(client.player,client), "Easy Life Configuration", client.player);
                client.openScreen(screen);
                return;
            }

            if(narrator_menu.wasPressed()){
                Screen screen = new ConfigScreen(new NarratorMenuGui(client.player,client), "F4 Menu", client.player);
                client.openScreen(screen);
                return;
            }


            while(direction_narrator.wasPressed()){
                final PlayerEntity player = client.player;
                assert player != null;
                if(isAltPressed){
                    int angle = (int) player.getRotationClient().x;
                    String text = "" + angle;
                    if(text.contains("-")) text = text.replace("-", "negative ");
                    player.sendMessage(new LiteralText(text), true);
                } else {
                    int angle = (int) player.getRotationClient().y;
                    String string;
                    String text = "";

                    while (angle >= 360) angle -= 360;
                    while (angle <= -360) angle += 360;

                    if(ELConfig.get(ELConfig.getCardinal_to_Degrees_Key())){
                        text += angle;
                        if(text.contains("-")) text = text.replace("-", "negative");
                    } else {
                        if ((angle >= -150 && angle <= -120) || (angle >= 210 && angle <= 240)) {
                            // Looking North East
                            string = "North East";
                        } else if ((angle >= -60 && angle <= -30) || (angle >= 300 && angle <= 330)) {
                            // Looking South East
                            string = "South East";
                        } else if ((angle >= 30 && angle <= 60) || (angle >= -330 && angle <= -300)) {
                            // Looking South West
                            string = "South West";
                        } else if ((angle >= 120 && angle <= 150) || (angle >= -240 && angle <= -210)) {
                            // Looking North West
                            string = "North West";
                        } else {
                            String dir = client.player.getHorizontalFacing().asString();
                            dir = dir.toLowerCase().trim();
                            if (dir.contains("north")) string = "North";
                            else if (dir.contains("south")) string = "South";
                            else if (dir.contains("east")) string = "East";
                            else if (dir.contains("west")) string = "West";
                            else string = "East";
                        }
                        text = "Facing " + string.toLowerCase();
                    }

                    player.sendMessage(new LiteralText(text), true);
                }
            }

            if (tickCount > 0f) {
                if (this.kbPressed() && ELConfig.get(ELConfig.getHealthNHungerKey())) {
                    tickCount -= client.getTickDelta();
                }
            }

            if (coordFlag && (ELConfig.get(ELConfig.getPlayerCoordinatesKey()) || ELConfig.get(ELConfig.getPlayerDirectionKey()))) {
                showCoord();
            }

            while (coord.wasPressed() && (ELConfig.get(ELConfig.getPlayerCoordinatesKey()) || ELConfig.get(ELConfig.getPlayerDirectionKey()))) {
                coordFlag = !coordFlag;
            }

            while (healthNhungerKey.wasPressed() && ELConfig.get(ELConfig.getHealthNHungerKey())) {
                if (this.kbPressed()) {
                    if(ELConfig.get(ELConfig.getNarratorSupportKey())){
                        assert client.player != null;
                        double health = client.player.getHealth();
                        double hunger = client.player.getHungerManager().getFoodLevel();
                        client.player.sendMessage(new LiteralText("health is "+((double) Math.round((health / 2) * 10) / 10)+" Hunger is "+((double) Math.round((hunger / 2) * 10) / 10)),true);
                    }
                    tickCount = 120f;
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
                width * reqWidth/100, height * reqHeight/100, color);
        matrixStack.pop();

        return true;
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
            String posX = pos.x +"";
            String posY = pos.y +"";
            String posZ = pos.z +"";
            posX = posX.substring(0, posX.indexOf("."));
            posY = posY.substring(0, posY.indexOf("."));
            posZ = posZ.substring(0, posZ.indexOf("."));
            String posString= "Position: "+posX+" | "+posY+" | "+posZ+"  ";
            int x = Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX())),y = Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()));
            int bgColor = colors(ELConfig.getString(ELConfig.getPcBgColor()),ELConfig.getOpacity(ELConfig.getPcBgColorOpacity()));
            int color = colors(ELConfig.getString(ELConfig.getPcColor()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
            try {
                if(ELConfig.get(ELConfig.getPcColorCustom())){
                    color = colors(ELConfig.getString(ELConfig.getPcColorCustomVal()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
                }
            } catch (Exception e) {
                color = colors(ELConfig.getString(ELConfig.getPcColor()),ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
            }
            
            
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, x, y, (posString.length()*5) - 2 + x , y+14, bgColor);
            matrixStack.pop();


            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, posString, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX()))+3, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()))+3, color);
            matrixStack.pop();

        }

        if(ELConfig.get(ELConfig.getPlayerDirectionKey())){
        	int x = Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX())),y = Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()));
        	int bgColor = colors(ELConfig.getString(ELConfig.getPdBgColor()),ELConfig.getOpacity(ELConfig.getPdBgColorOpacity()));
            int color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            try {
                if(ELConfig.get(ELConfig.getPdColorCustom())) color = colors(ELConfig.getString(ELConfig.getPdColorCustomVal()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            } catch (Exception e) {
                color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
            }
            
            
            String dirString="Direction: "+getDirection(player.getHorizontalFacing().asString(),(int)player.getRotationClient().y);
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            DrawableHelper.fill(matrixStack, x, y, (dirString.length()*5) + 5 + x, y+14, bgColor);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.scale(1, 1, inGameHud.getZOffset());
            textRenderer.draw(matrixStack, dirString, x+3, y+3, color);
            matrixStack.pop();
        }
        

    }

    private String getDirection(String dir,int angle){
        dir = dir.toLowerCase().trim();
        while(angle>=360) angle -= 360;
        while(angle<=-360) angle += 360;
        
        if((angle>=-150&&angle<=-120)||(angle>=210&&angle<=240)){
        	// Looking North East
        	return "North East (+X -Z)";
        } else if((angle>=-60&&angle<=-30)||(angle>=300&&angle<=330)){
        	// Looking South East
        	return "South East (+X +Z)";
        } else if((angle>=30&&angle<=60)||(angle>=-330&&angle<=-300)){
        	// Looking South West
        	return "South West (-X +Z)";
        } else if((angle>=120&&angle<=150)||(angle>=-240&&angle<=-210)){
        	// Looking North West
        	return "North West (-X -Z)";
        } else {
            if (dir.contains("north")) return "North (-Z)";
            else if (dir.contains("south")) return "South (+Z)";
            else if (dir.contains("east")) return "East (+X)";
            else if (dir.contains("west")) return "West (-X)";
            else return "East (+X)";
        }
    }
}