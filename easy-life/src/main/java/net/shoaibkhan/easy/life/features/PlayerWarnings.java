package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.config.Config;

import static net.shoaibkhan.easy.life.utils.Colors.colors;

public class PlayerWarnings {
    public static int healthWarningFlag, foodWarningFlag, airWarningFlag;
    public static int healthWarningAfterFlag, foodWarningAfterFlag, airWarningAfterFlag;

    private final MinecraftClient client;

    public PlayerWarnings(MinecraftClient client){
        this.client = client;

        initializeFields();

        handler();
    }

    private void initializeFields(){

        healthWarningFlag = Initial.counterMap.getOrDefault("healthWarningFlag", 0);

        foodWarningFlag = Initial.counterMap.getOrDefault("foodWarningFlag", 0);

        airWarningFlag = Initial.counterMap.getOrDefault("airWarningFlag", 0);

        healthWarningAfterFlag = Initial.counterMap.getOrDefault("healthWarningAfterFlag", 0);

        foodWarningAfterFlag = Initial.counterMap.getOrDefault("foodWarningAfterFlag", 0);

        airWarningAfterFlag = Initial.counterMap.getOrDefault("airWarningAfterFlag", 0);

    }

    private void handler(){
        if (!client.isPaused() && client.world!=null) {
            final PlayerEntity player = client.player;
            final InGameHud inGameHud = client.inGameHud;
            final MatrixStack matrixStack = new MatrixStack();
            final TextRenderer textRenderer = client.textRenderer;

            if (player != null && ( Config.get(Config.getPlayerWarningKey())) ) {
                final int height = client.getWindow().getScaledHeight();
                final int width = client.getWindow().getScaledWidth();
                final int reqHeight = Integer.parseInt(Config.getString(Config.getPwPositionY()));
                final int reqWidth = Integer.parseInt(Config.getString(Config.getPwPositionX()));
                final double health = player.getHealth();
                final double food = player.getHungerManager().getFoodLevel();
                final double air = player.getAir();

                if (Config.get(Config.getPlayerWarningKey())) {
                    healthWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth, health);

                    foodWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth, health, food);

                    airWarning(player, inGameHud, matrixStack, textRenderer, height, width, reqHeight, reqWidth, air);

                }

            }
        }
    }

    private void healthWarning(PlayerEntity player, InGameHud inGameHud, MatrixStack matrixStack, TextRenderer textRenderer, int height, int width, int reqHeight, int reqWidth, double health){
        double firstTH;
        double secondTH;
        if( Double.parseDouble(Config.getString(Config.getPwHtFTh())) > Double.parseDouble(Config.getString(Config.getPwHtSTh())) ){
            firstTH = Double.parseDouble(Config.getString(Config.getPwHtFTh())) * 2;
            secondTH = Double.parseDouble(Config.getString(Config.getPwHtSTh())) * 2;
        } else {
            firstTH = Double.parseDouble(Config.getString(Config.getPwHtSTh())) * 2;
            secondTH = Double.parseDouble(Config.getString(Config.getPwHtFTh())) * 2;
        }
        if( health>=firstTH && health>=secondTH && healthWarningFlag>0  && healthWarningAfterFlag<=0 ){
            Initial.counterMap.put("healthWarningAfterFlag", 10000);
        }
        if (health < firstTH && health > secondTH && healthWarningFlag <= 0 && healthWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())), Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"),
                    width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()), 100));

            Initial.narrate("Health Low");

            if (Config.get(Config.getPwSoundStatus())) {
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
            }

            matrixStack.pop();
            Initial.counterMap.put("healthWarningFlag", Integer.parseInt(Config.getString(Config.getPwTimeout())) * 1000);
        }

        if (health < secondTH && health > 0 && healthWarningFlag<=0 && healthWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())), Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"), width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()), 100));

            matrixStack.pop();
            Initial.narrate("Health Low");
            if (Config.get(Config.getPwSoundStatus())) {
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
            }
            Initial.counterMap.put("healthWarningFlag", Integer.parseInt(Config.getString(Config.getPwTimeout())) * 1000);

        }

        if (healthWarningFlag >= ((Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1000) && healthWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())),Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Health Low!"), width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()),100));

            matrixStack.pop();
        }


    }

    private void foodWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double health,double food){
        double foodTH = Double.parseDouble(Config.getString(Config.getPwFtth())) * 2;
        double firstTH;
        if(food>=foodTH && foodWarningFlag>0 && foodWarningAfterFlag<=0){
            Initial.counterMap.put("foodWarningAfterFlag", 10000);
        }
        if( Double.parseDouble(Config.getString(Config.getPwHtFTh())) > Double.parseDouble(Config.getString(Config.getPwHtSTh())) ){
            firstTH = Double.parseDouble(Config.getString(Config.getPwHtFTh())) * 2;
        } else {
            firstTH = Double.parseDouble(Config.getString(Config.getPwHtSTh())) * 2;
        }
        if (food < foodTH && food > 0 && health >=firstTH && foodWarningFlag <=0 && foodWarningAfterFlag<=0) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())), Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()), 100));
            matrixStack.pop();
            Initial.narrate("Food Low");

            if (Config.get(Config.getPwSoundStatus())) {
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
            }
            Initial.counterMap.put("foodWarningFlag", Integer.parseInt(Config.getString(Config.getPwTimeout())) * 1000);
        }

        if (foodWarningFlag >= ((Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1000) && healthWarningFlag<=0 && foodWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())),Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Food Low!"),
                    width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()),100));
            matrixStack.pop();
        }


    }

    private void airWarning(PlayerEntity player,InGameHud inGameHud,MatrixStack matrixStack,TextRenderer textRenderer,int height,int width,int reqHeight,int reqWidth,double air){
        double airTH = Double.parseDouble(Config.getString(Config.getPwAtth())) * 30;

        if(air>=airTH && airWarningFlag>0 && airWarningAfterFlag<=0){
            Initial.counterMap.put("airWarningAfterFlag", 10000);
        }

        if (air < airTH && air > 0 && foodWarningFlag<(Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1500 && healthWarningFlag<(Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1500 && airWarningFlag <=0 && airWarningAfterFlag<=0 ) {
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())), Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"), width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()), 100));
            matrixStack.pop();
            Initial.narrate("Air Low");

            if (Config.get(Config.getPwSoundStatus())) {
                player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
            }
            Initial.counterMap.put("airWarningFlag", Integer.parseInt(Config.getString(Config.getPwTimeout())) * 1000);
        }

        if (airWarningFlag >= ((Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1000) && foodWarningFlag<(Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1500 && healthWarningFlag<(Integer.parseInt(Config.getString(Config.getPwTimeout()))*1000)-1500 && airWarningAfterFlag<=0 ){
            matrixStack.push();
            matrixStack.scale(Integer.parseInt(Config.getString(Config.getPwScale())),Integer.parseInt(Config.getString(Config.getPwScale())), inGameHud.getZOffset());

            DrawableHelper.drawTextWithShadow(matrixStack, textRenderer, new LiteralText("Air Low!"), width * reqWidth / 100, height * reqHeight / 100, colors(Config.getString(Config.getPwColor()),100));

            matrixStack.pop();
        }


    }

}
