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
import net.shoaibkhan.easy.life.gui.widgets.ColorButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.ScaleButton;

public class HNHConfigGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;
    
    public HNHConfigGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 120);

        WButton doneButton = new WButton(new LiteralText("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 5, 7, 1);

        WLabel label = new WLabel(new LiteralText("Health n Hunger Configuration"), ClientMod.colors("red"));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        WButton nsbutton = new WButton(new LiteralText("Back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 5, 7, 1);


        ColorButton hnhcolor = new ColorButton("Color",ELConfig.getHnhColor());
        root.add(hnhcolor, 1, 1, 6, 1);

        ScaleButton hnhscale = new ScaleButton("Scale", ELConfig.getHnhScale());
        root.add(hnhscale, 8, 1, 6, 1);

        WLabel hnhpos = new WLabel(new LiteralText("Position :-"), ClientMod.colors("black"));
        hnhpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhpos, 1, 3, 3, 1);

        WLabel hnhx = new WLabel(new LiteralText("X="));
        hnhx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhx, 5, 3, 1, 1);

        WTextField hnhxf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getHnhPositionX())));
        root.add(hnhxf, 6, 3, 2, 1);

        WLabel hnhy = new WLabel(new LiteralText("Y="));
        hnhy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhy, 9, 3, 1, 1);

        WTextField hnhyf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getHnhPositionY())));
        root.add(hnhyf, 10, 3, 2, 1);

        DoubleSubmitButton hnhpossubmit = new DoubleSubmitButton("Submit",hnhxf,hnhyf,ELConfig.getHnhPositionX(),ELConfig.getHnhPositionY());
        root.add(hnhpossubmit, 14, 3, 3, 1);
        
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
