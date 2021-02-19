package net.shoaibkhan.health.n.hunger;

import com.mojang.brigadier.CommandDispatcher;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.LiteralText;

public class MyCommands implements ClientCommandPlugin {
    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {

        dispatcher.register(ArgumentBuilders.literal("updateConfigFile").executes(source -> {
            Config config = new Config();
                source.getSource().sendFeedback(new LiteralText(""+config.getHealth_n_hunger_status().equals("on")));
                return 1;
            }
        ));
    }
}