package net.shoaibkhan.easy.life.features;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.utils.PlayerPosition;

import static net.shoaibkhan.easy.life.utils.Colors.colors;

public class PositionAndDirectionOverlay {
    private final MinecraftClient client;
    private final InGameHud inGameHud;
    private final TextRenderer textRenderer;
    private final MatrixStack matrixStack;

    public PositionAndDirectionOverlay(MinecraftClient client){
        this.client = client;
        this.inGameHud = client.inGameHud;
        this.textRenderer = client.textRenderer;
        this.matrixStack = new MatrixStack();
        if (client.player != null && client.currentScreen == null) {
            main();
        }
    }

    private void main(){
        RenderSystem.enableBlend();

        if(ELConfig.get(ELConfig.getPlayerCoordinatesKey())) printPlayerCoordinatesOnScreen();

        if(ELConfig.get(ELConfig.getPlayerDirectionKey())) printPlayerDirectionOnScreen();
    }

    private void printPlayerCoordinatesOnScreen() {
        String posX = new PlayerPosition(client).getNarratableXPos();
        String posY = new PlayerPosition(client).getNarratableYPos();
        String posZ = new PlayerPosition(client).getNarratableZPos();
        String posString = "Position: " + posX + " | " + posY + " | " + posZ + "  ";

        int x = Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX())), y = Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY()));
        int bgColor = colors(ELConfig.getString(ELConfig.getPcBgColor()), ELConfig.getOpacity(ELConfig.getPcBgColorOpacity()));
        int color = colors(ELConfig.getString(ELConfig.getPcColor()), ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
        try {
            if (ELConfig.get(ELConfig.getPcColorCustom())) {
                color = colors(ELConfig.getString(ELConfig.getPcColorCustomVal()), ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
            }
        } catch (Exception e) {
            color = colors(ELConfig.getString(ELConfig.getPcColor()), ELConfig.getOpacity(ELConfig.getPcColorOpacity()));
        }

        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        DrawableHelper.fill(matrixStack, x, y, (posString.length() * 5) - 2 + x, y + 14, bgColor);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        textRenderer.draw(matrixStack, posString, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionX())) + 3, Integer.parseInt(ELConfig.getString(ELConfig.getPcPositionY())) + 3, color);
        matrixStack.pop();
    }

    private void printPlayerDirectionOnScreen(){
        int x = Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionX())),y = Integer.parseInt(ELConfig.getString(ELConfig.getPdPositionY()));
        int bgColor = colors(ELConfig.getString(ELConfig.getPdBgColor()),ELConfig.getOpacity(ELConfig.getPdBgColorOpacity()));
        int color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
        try {
            if(ELConfig.get(ELConfig.getPdColorCustom())) color = colors(ELConfig.getString(ELConfig.getPdColorCustomVal()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
        } catch (Exception e) {
            color = colors(ELConfig.getString(ELConfig.getPdColor()),ELConfig.getOpacity(ELConfig.getPdColorOpacity()));
        }

        String dirString="Direction: " + new PlayerPosition(client).getHorizontalFacingDirectionInCardinal();
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
