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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.gui.widgets.ColorButton;
import net.shoaibkhan.easy.life.gui.widgets.ConfigButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.ScaleButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.THDoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.THSubmitButton;
import net.shoaibkhan.easy.life.utils.Colors;

public class PWConfigGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;
    
    public PWConfigGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 240);


        ColorButton pwcolor = new ColorButton("Color", Config.getPwColor());
        root.add(pwcolor, 1, 1, 6, 1);

        ScaleButton pwscale = new ScaleButton("Scale", Config.getPwScale());
        root.add(pwscale, 8, 1, 4, 1);

        ConfigButton pwsound = new ConfigButton("sounds", Config.getPwSoundStatus());
        root.add(pwsound, 13, 1, 5, 1);

        WLabel pwpos = new WLabel(new LiteralText("Position :-"), Colors.colors("black",100));
        pwpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwpos, 1, 3, 3, 1);

        WLabel pwx = new WLabel(new LiteralText("X="));
        pwx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwx, 5, 3, 1, 1);

        WTextField pwxf = new WTextField(new LiteralText(Config.getString(Config.getPwPositionX())));
        root.add(pwxf, 6, 3, 2, 1);

        WLabel pwy = new WLabel(new LiteralText("Y="));
        pwy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwy, 9, 3, 1, 1);

        WTextField pwyf = new WTextField(new LiteralText(Config.getString(Config.getPwPositionY())));
        root.add(pwyf, 10, 3, 2, 1);

        DoubleSubmitButton pwpossubmit = new DoubleSubmitButton("gui.easylife.submit",pwxf,pwyf, Config.getPwPositionX(), Config.getPwPositionY());
        root.add(pwpossubmit, 14, 3, 3, 1);

        WLabel pwtimeout = new WLabel(new LiteralText("Timeout (in seconds) :-"), Colors.colors("black",100));
        pwtimeout.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwtimeout, 1, 5, 5, 1);

        WTextField pwtof = new WTextField(new LiteralText(Config.getString(Config.getPwTimeout())));
        root.add(pwtof, 9, 5, 2, 1);

        SubmitButton pwtosubmit = new SubmitButton("gui.easylife.submit",pwtof, Config.getPwTimeout());
        root.add(pwtosubmit, 12, 5, 3, 1);

        WLabel pwht = new WLabel(new LiteralText("Health Threshold :-"), Colors.colors("black",100));
        pwht.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwht, 1, 7, 5, 1);

        WLabel pwhtft = new WLabel(new LiteralText("First="));
        pwhtft.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwhtft, 7, 7, 2, 1);

        WTextField pwhtftf = new WTextField(new LiteralText(Config.getString(Config.getPwHtFTh())));
        root.add(pwhtftf, 9, 7, 2, 1);

        WLabel pwhtst = new WLabel(new LiteralText("Second="));
        pwhtst.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwhtst, 12, 7, 2, 1);

        WTextField pwhtstf = new WTextField(new LiteralText(Config.getString(Config.getPwHtSTh())));
        root.add(pwhtstf, 15, 7, 2, 1);

        THDoubleSubmitButton pwhtsubmit = new THDoubleSubmitButton("gui.easylife.submit",pwhtftf,pwhtstf, Config.getPwHtFTh(), Config.getPwHtSTh());
        root.add(pwhtsubmit, 18, 7, 3, 1);

        WLabel pwft = new WLabel(new LiteralText("Food Threshold :-"), Colors.colors("black",100));
        pwft.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwft, 1, 9, 4, 1);

        WTextField pwftf = new WTextField(new LiteralText(Config.getString(Config.getPwFtth())));
        root.add(pwftf, 7, 9, 2, 1);

        THSubmitButton pwftsubmit = new THSubmitButton("gui.easylife.submit",pwftf, Config.getPwFtth());
        root.add(pwftsubmit, 10, 9, 3, 1);

        WLabel pwat = new WLabel(new LiteralText("Air Threshold :-"), Colors.colors("black",100));
        pwat.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pwat, 1, 11, 4, 1);

        WTextField pwatf = new WTextField(new LiteralText(Config.getString(Config.getPwAtth())));
        root.add(pwatf, 7, 11, 2, 1);

        THSubmitButton pwatsubmit = new THSubmitButton("gui.easylife.submit",pwatf, Config.getPwAtth());
        root.add(pwatsubmit, 10, 11, 3, 1);
        


        WButton nsbutton = new WButton(new TranslatableText("gui.easylife.back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 13, 7, 1);

        WButton doneButton = new WButton(new TranslatableText("gui.easylife.done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 13, 7, 1);

        WLabel label = new WLabel(new TranslatableText("gui.easylife.playerWarningsSettings"), Colors.colors("red",100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);
        
        root.validate(this);
    }

    private void onBackClick(){
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new ConfigGui(this.player,this.client), "configuration");
        this.client.setScreen(screen);
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }
    
    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey",50)));
    }
}
