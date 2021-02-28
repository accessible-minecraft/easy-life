package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.config.ELConfig;
import net.shoaibkhan.easy.life.config.SerializableConfig;
import net.shoaibkhan.easy.life.gui.widgets.ColorButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.ScaleButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitButton;

public class PDConfigGui extends LightweightGuiDescription {
    private SerializableConfig tempConfig;
    private ClientPlayerEntity player;
    private MinecraftClient client;
    
    public PDConfigGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 120);

        WButton doneButton = new WButton(new LiteralText("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 5, 7, 1);

        WLabel label = new WLabel(new LiteralText("Player Direction Configuration"), ClientMod.colors("red"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        WButton nsbutton = new WButton(new LiteralText("Back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 5, 7, 1);



        ColorButton pdcolor = new ColorButton("Color",ELConfig.getPdColor());
        root.add(pdcolor, 1, 1, 6, 1);

        ColorButton pdbgcolor = new ColorButton("Background Color",ELConfig.getPdBgColor());
        root.add(pdbgcolor, 8, 1, 8, 1);

        WLabel pdpos = new WLabel(new LiteralText("Position :-"), ClientMod.colors("black"));
        pdpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdpos, 1, 3, 3, 1);

        WLabel pdx = new WLabel(new LiteralText("X="));
        pdx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdx, 5, 3, 1, 1);

        WTextField pdxf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getPdPositionX())));
        root.add(pdxf, 6, 3, 2, 1);

        WLabel pdy = new WLabel(new LiteralText("Y="));
        pdy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdy, 9, 3, 1, 1);

        WTextField pdyf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getPdPositionY())));
        root.add(pdyf, 10, 3, 2, 1);

        DoubleSubmitButton pdpossubmit = new DoubleSubmitButton("Submit",pdxf,pdyf,ELConfig.getPdPositionX(),ELConfig.getPdPositionY());
        root.add(pdpossubmit, 14, 3, 3, 1);
        
        root.validate(this);
    }

    private void onBackClick(){
        this.player.closeScreen();
        this.client.openScreen(new ConfigScreen(new ConfigGui(this.player,this.client)));        
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey")));
    }
    
}
