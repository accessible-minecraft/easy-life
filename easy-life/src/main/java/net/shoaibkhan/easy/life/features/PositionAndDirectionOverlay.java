package net.shoaibkhan.easy.life.features;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.shoaibkhan.easy.life.config.Config;
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

        if(Config.get(Config.getPlayerCoordinatesKey())) printPlayerCoordinatesOnScreen();

        if(Config.get(Config.getPlayerDirectionKey())) printPlayerDirectionOnScreen();
    }

    private void printPlayerCoordinatesOnScreen() {
        String posX = new PlayerPosition(client).getNarratableXPos();
        String posY = new PlayerPosition(client).getNarratableYPos();
        String posZ = new PlayerPosition(client).getNarratableZPos();
        String posString = "Position: " + posX + " | " + posY + " | " + posZ + "  ";

        int x = Integer.parseInt(Config.getString(Config.getPcPositionX())), y = Integer.parseInt(Config.getString(Config.getPcPositionY()));
        int bgColor = colors(Config.getString(Config.getPcBgColor()), Config.getOpacity(Config.getPcBgColorOpacity()));
        int color = colors(Config.getString(Config.getPcColor()), Config.getOpacity(Config.getPcColorOpacity()));
        try {
            if (Config.get(Config.getPcColorCustom())) {
                color = colors(Config.getString(Config.getPcColorCustomVal()), Config.getOpacity(Config.getPcColorOpacity()));
            }
        } catch (Exception e) {
            color = colors(Config.getString(Config.getPcColor()), Config.getOpacity(Config.getPcColorOpacity()));
        }

        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        DrawableHelper.fill(matrixStack, x, y, (posString.length() * 5) - 2 + x, y + 14, bgColor);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.scale(1, 1, inGameHud.getZOffset());
        textRenderer.draw(matrixStack, posString, Integer.parseInt(Config.getString(Config.getPcPositionX())) + 3, Integer.parseInt(Config.getString(Config.getPcPositionY())) + 3, color);
        matrixStack.pop();
    }

    private void printPlayerDirectionOnScreen(){
        int x = Integer.parseInt(Config.getString(Config.getPdPositionX())),y = Integer.parseInt(Config.getString(Config.getPdPositionY()));
        int bgColor = colors(Config.getString(Config.getPdBgColor()), Config.getOpacity(Config.getPdBgColorOpacity()));
        int color = colors(Config.getString(Config.getPdColor()), Config.getOpacity(Config.getPdColorOpacity()));
        try {
            if(Config.get(Config.getPdColorCustom())) color = colors(Config.getString(Config.getPdColorCustomVal()), Config.getOpacity(Config.getPdColorOpacity()));
        } catch (Exception e) {
            color = colors(Config.getString(Config.getPdColor()), Config.getOpacity(Config.getPdColorOpacity()));
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
