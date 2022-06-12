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
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.shoaibkhan.easy.life.config.Config;
import net.shoaibkhan.easy.life.gui.widgets.ColorButton;
import net.shoaibkhan.easy.life.gui.widgets.CustomColorButton;
import net.shoaibkhan.easy.life.gui.widgets.DoubleSubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitButton;
import net.shoaibkhan.easy.life.gui.widgets.SubmitColorButton;
import net.shoaibkhan.easy.life.utils.Colors;

public class PCConfigGui extends LightweightGuiDescription {
    private final ClientPlayerEntity player;
    private final MinecraftClient client;

    public PCConfigGui(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.client = client;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(320, 220);

        WLabel pc_color_label = new WLabel(Text.of("Text Color :-"), Colors.colors("black", 100));
        pc_color_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pc_color_label, 1, 1, 3, 1);

        ColorButton pccolor = new ColorButton("Color", Config.getPcColor());
        pccolor.setEnabled(!Config.get(Config.getPcColorCustom()));
        root.add(pccolor, 12, 1, 5, 1);

        WTextField pccolortf = new WTextField(Text.of(Config.getString(Config.getPcColorCustomVal())));
        pccolortf.setEditable(Config.get(Config.getPcColorCustom()));
        pccolortf.setDisabledColor(0x2c2c2c);
        root.add(pccolortf, 5, 3, 4, 1);

        SubmitColorButton pccolorsubmit = new SubmitColorButton("Set", pccolortf, Config.getPcColorCustomVal());
        pccolorsubmit.setEnabled(Config.get(Config.getPcColorCustom()));
        root.add(pccolorsubmit, 11, 3, 2, 1);

        CustomColorButton pc_color_custom_button = new CustomColorButton("Custom Color", Config.getPcColorCustom(), pccolor, pccolortf, pccolorsubmit);
        root.add(pc_color_custom_button, 5, 1, 6, 1);

        WLabel pc_color_opacity_label = new WLabel(Text.of("Opacity:-"));
        pc_color_opacity_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pc_color_opacity_label, 5, 5, 3, 1);

        WTextField pc_color_opacity_field = new WTextField(Text.of(Config.getString(Config.getPcColorOpacity())));
        root.add(pc_color_opacity_field, 9, 5, 2, 1);

        SubmitButton pc_color_opacity_submit = new SubmitButton("Set", pc_color_opacity_field, Config.getPcColorOpacity());
        root.add(pc_color_opacity_submit, 12, 5, 3, 1);


        WLabel pc_background_color_label = new WLabel(Text.of("Background Color :-"), Colors.colors("black", 100));
        pc_background_color_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pc_background_color_label, 1, 7, 5, 1);

        ColorButton pcbgcolor = new ColorButton("Color", Config.getPcBgColor());
        root.add(pcbgcolor, 7, 7, 5, 1);

        WLabel pc_bg_color_opacity_label = new WLabel(Text.of("Opacity:-"));
        pc_bg_color_opacity_label.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pc_bg_color_opacity_label, 5, 9, 3, 1);

        WTextField pc_bg_color_opacity_field = new WTextField(Text.of(Config.getString(Config.getPcBgColorOpacity())));
        root.add(pc_bg_color_opacity_field, 9, 9, 2, 1);

        SubmitButton pc_bg_color_opacity_submit = new SubmitButton("Set", pc_bg_color_opacity_field, Config.getPcBgColorOpacity());
        root.add(pc_bg_color_opacity_submit, 12, 9, 3, 1);


        WLabel pcpos = new WLabel(Text.of("Position :-"), Colors.colors("black", 100));
        pcpos.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pcpos, 1, 11, 3, 1);

        WLabel pcx = new WLabel(Text.of("X="));
        pcx.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pcx, 5, 11, 1, 1);

        WTextField pcxf = new WTextField(Text.of(Config.getString(Config.getPcPositionX())));
        root.add(pcxf, 6, 11, 2, 1);

        WLabel pcy = new WLabel(Text.of("Y="));
        pcy.setVerticalAlignment(VerticalAlignment.CENTER);
        root.add(pcy, 9, 11, 1, 1);

        WTextField pcyf = new WTextField(Text.of(Config.getString(Config.getPcPositionY())));
        root.add(pcyf, 10, 11, 2, 1);

        DoubleSubmitButton pcpossubmit = new DoubleSubmitButton("gui.easylife.submit", pcxf, pcyf, Config.getPcPositionX(), Config.getPcPositionY());
        root.add(pcpossubmit, 14, 11, 3, 1);


        WButton nsbutton = new WButton(Text.of(I18n.translate("gui.easylife.back")));
        nsbutton.setOnClick(this::onBackClick);
        root.add(nsbutton, 3, 13, 7, 1);

        WButton doneButton = new WButton(Text.of(I18n.translate("gui.easylife.done")));
        doneButton.setOnClick(this::onDoneClick);
        root.add(doneButton, 12, 13, 7, 1);

        WLabel label = new WLabel(Text.of(I18n.translate("gui.easylife.playerCoordinatesSettings")), Colors.colors("red", 100));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 21, 1);

        root.validate(this);
    }

    private void onBackClick() {
        this.player.closeScreen();
        Screen screen = new ConfigScreen(new ConfigGui(this.player, this.client), "configuration");
        this.client.setScreen(screen);
    }

    private void onDoneClick() {
        this.player.closeScreen();
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey", 50)));
    }

}
