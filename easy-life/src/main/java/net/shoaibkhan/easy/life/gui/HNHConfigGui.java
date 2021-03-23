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
import net.shoaibkhan.easy.life.gui.widgets.CustomColorButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.ScaleButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitColorButton;

public class HNHConfigGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;
    
    public HNHConfigGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(320, 220);

        WButton doneButton = new WButton(new LiteralText("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 11, 7, 1);

        WLabel label = new WLabel(new LiteralText("Health n Hunger Configuration"), ClientMod.colors("red",100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        WButton nsbutton = new WButton(new LiteralText("Back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 11, 7, 1);



        WLabel hnh_color_label = new WLabel(new LiteralText("Text Color :-"), ClientMod.colors("black",100));
        hnh_color_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnh_color_label, 1, 1, 3, 1);

        ColorButton hnhcolor = new ColorButton("Color",ELConfig.getHnhColor());
        if(ELConfig.get(ELConfig.getHnhColorCustom())) hnhcolor.setEnabled(false);
        else hnhcolor.setEnabled(true);
        root.add(hnhcolor, 12, 1, 5, 1);

        WTextField hnhcolortf = new WTextField(new LiteralText(""+ELConfig.getString(ELConfig.getHnhColorCustomVal())));
        if(ELConfig.get(ELConfig.getHnhColorCustom())) hnhcolortf.setEditable(true);
        else hnhcolortf.setEditable(false);
        hnhcolortf.setDisabledColor(0x2c2c2c);
        root.add(hnhcolortf, 5, 3, 4, 1);

        SubmitColorButton hnhcolorsubmit = new SubmitColorButton("Set", hnhcolortf, ELConfig.getHnhColorCustomVal());
        if(ELConfig.get(ELConfig.getHnhColorCustom())) hnhcolorsubmit.setEnabled(true);
        else hnhcolorsubmit.setEnabled(false);
        root.add(hnhcolorsubmit, 11, 3, 2, 1);

        CustomColorButton hnh_color_custom_button = new CustomColorButton("Custom Color", ELConfig.getHnhColorCustom(), hnhcolor, hnhcolortf, hnhcolorsubmit);
        root.add(hnh_color_custom_button, 5, 1, 6, 1);

        WLabel hnh_color_opacity_label = new WLabel(new LiteralText("Opacity:-"));
        hnh_color_opacity_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnh_color_opacity_label, 5, 5, 3, 1);

        WTextField hnh_color_opacity_field = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getHnhColorOpacity())));
        root.add(hnh_color_opacity_field, 9, 5, 2, 1);

        SubmitButton hnh_color_opacity_submit = new SubmitButton("Set", hnh_color_opacity_field, ELConfig.getHnhColorOpacity());
        root.add(hnh_color_opacity_submit, 12, 5, 3, 1);

        ScaleButton hnhscale = new ScaleButton("Scale", ELConfig.getHnhScale());
        root.add(hnhscale, 1, 9, 6, 1);

        WLabel hnhpos = new WLabel(new LiteralText("Position :-"), ClientMod.colors("black",100));
        hnhpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhpos, 1, 7, 3, 1);

        WLabel hnhx = new WLabel(new LiteralText("X="));
        hnhx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhx, 5, 7, 1, 1);

        WTextField hnhxf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getHnhPositionX())));
        root.add(hnhxf, 6, 7, 2, 1);

        WLabel hnhy = new WLabel(new LiteralText("Y="));
        hnhy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(hnhy, 9, 7, 1, 1);

        WTextField hnhyf = new WTextField(new LiteralText(ELConfig.getString(ELConfig.getHnhPositionY())));
        root.add(hnhyf, 10, 7, 2, 1);

        DoubleSubmitButton hnhpossubmit = new DoubleSubmitButton("Submit",hnhxf,hnhyf,ELConfig.getHnhPositionX(),ELConfig.getHnhPositionY());
        root.add(hnhpossubmit, 14, 7, 3, 1);
        
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
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey",100)));
    }
    
}
