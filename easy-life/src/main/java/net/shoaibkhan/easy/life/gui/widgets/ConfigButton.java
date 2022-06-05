package net.shoaibkhan.easy.life.gui.widgets;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.shoaibkhan.easy.life.config.Config;

public class ConfigButton extends WButton {
    private String translateKey;
    private String jsonKey;

	public ConfigButton(String translationKey, String jsonKey) {
		super(getTitle(translationKey, Config.get(jsonKey)));
		this.translateKey = translationKey;
		this.jsonKey = jsonKey;
	}

//    // 1.16
//    @Override
//    public void onClick(int x, int y, int button) {
//        super.onClick(x, y, button);
//        if(this.isEnabled()){
//            boolean enabled = Config.toggle(this.jsonKey);
//            TranslatableText newButtonText = new TranslatableText(this.translateKey + (enabled ? " : on" : " : off"));
//            this.setLabel(newButtonText);
//        }
//    }

  // 1.17
    @Override
    public InputResult onClick(int x, int y, int button) {
        super.onClick(x, y, button);
        if(this.isEnabled()){
            boolean enabled = Config.toggle(this.jsonKey);
            this.setLabel(getTitle(translateKey, enabled));
        }
        return InputResult.PROCESSED;
    }
    
	private static Text getTitle(String key, boolean enabled) {
		String s = I18n.translate("gui.easylife." + (enabled ? "on" : "off"));
		return new TranslatableText("gui.easylife." + key).append(s);
	}
}