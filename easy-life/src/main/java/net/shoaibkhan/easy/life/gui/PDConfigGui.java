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
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.gui.widgets.ColorButton;
import net.shoaibkhan.easy.life.gui.widgets.CustomColorButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitColorButton;
import net.shoaibkhan.easy.life.utils.Colors;

public class PDConfigGui extends LightweightGuiDescription {
    private final ClientPlayerEntity player;
    private final MinecraftClient client;

    public PDConfigGui(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(320, 220);

        WButton doneButton = new WButton(Text.of("Done"));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 13, 7, 1);

        WLabel label = new WLabel(Text.of("Player Direction Configuration"), Colors.colors("red", 100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        WButton nsbutton = new WButton(Text.of("Back"));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 13, 7, 1);


        WLabel pd_color_label = new WLabel(Text.of("Text Color :-"), Colors.colors("black", 100));
        pd_color_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pd_color_label, 1, 1, 3, 1);

        ColorButton pdcolor = new ColorButton("Color", Config.getPdColor());
        pdcolor.setEnabled(!Config.get(Config.getPdColorCustom()));
        root.add(pdcolor, 12, 1, 5, 1);

        WTextField pdcolortf = new WTextField(Text.of("" + Config.getString(Config.getPdColorCustomVal())));
        pdcolortf.setEditable(Config.get(Config.getPdColorCustom()));
        pdcolortf.setDisabledColor(0x2c2c2c);
        root.add(pdcolortf, 5, 3, 4, 1);

        SubmitColorButton pdcolorsubmit = new SubmitColorButton("Set", pdcolortf, Config.getPdColorCustomVal());
        pdcolorsubmit.setEnabled(Config.get(Config.getPdColorCustom()));
        root.add(pdcolorsubmit, 11, 3, 2, 1);

        CustomColorButton pd_color_custom_button = new CustomColorButton("Custom Color", Config.getPdColorCustom(), pdcolor, pdcolortf, pdcolorsubmit);
        root.add(pd_color_custom_button, 5, 1, 6, 1);

        WLabel pd_color_opacity_label = new WLabel(Text.of("Opacity:-"));
        pd_color_opacity_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pd_color_opacity_label, 5, 5, 3, 1);

        WTextField pd_color_opacity_field = new WTextField(Text.of(Config.getString(Config.getPdColorOpacity())));
        root.add(pd_color_opacity_field, 9, 5, 2, 1);

        SubmitButton pd_color_opacity_submit = new SubmitButton("Set", pd_color_opacity_field, Config.getPdColorOpacity());
        root.add(pd_color_opacity_submit, 12, 5, 3, 1);


        WLabel pd_background_color_label = new WLabel(Text.of("Background Color :-"), Colors.colors("black", 100));
        pd_background_color_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pd_background_color_label, 1, 7, 5, 1);

        ColorButton pdbgcolor = new ColorButton("Color", Config.getPdBgColor());
        root.add(pdbgcolor, 7, 7, 5, 1);

        WLabel pd_bg_color_opacity_label = new WLabel(Text.of("Opacity:-"));
        pd_bg_color_opacity_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pd_bg_color_opacity_label, 5, 9, 3, 1);

        WTextField pd_bg_color_opacity_field = new WTextField(Text.of(Config.getString(Config.getPdBgColorOpacity())));
        root.add(pd_bg_color_opacity_field, 9, 9, 2, 1);

        SubmitButton pd_bg_color_opacity_submit = new SubmitButton("Set", pd_bg_color_opacity_field, Config.getPdBgColorOpacity());
        root.add(pd_bg_color_opacity_submit, 12, 9, 3, 1);


        WLabel pdpos = new WLabel(Text.of("Position :-"), Colors.colors("black", 100));
        pdpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdpos, 1, 11, 3, 1);

        WLabel pdx = new WLabel(Text.of("X="));
        pdx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdx, 5, 11, 1, 1);

        WTextField pdxf = new WTextField(Text.of(Config.getString(Config.getPdPositionX())));
        root.add(pdxf, 6, 11, 2, 1);

        WLabel pdy = new WLabel(Text.of("Y="));
        pdy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pdy, 9, 11, 1, 1);

        WTextField pdyf = new WTextField(Text.of(Config.getString(Config.getPdPositionY())));
        root.add(pdyf, 10, 11, 2, 1);

        DoubleSubmitButton pdpossubmit = new DoubleSubmitButton("gui.easylife.submit", pdxf, pdyf, Config.getPdPositionX(), Config.getPdPositionY());
        root.add(pdpossubmit, 14, 11, 3, 1);

        root.validate(this);
    }

    private void onBackClick() {
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new ConfigGui(this.player, this.client), "configuration");
        client.setScreen(screen); // post 1.18
//        client.openScreen(screen); // pre 1.18
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey", 50)));
    }

}
