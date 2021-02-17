package net.shoaibkhan.health.n.hunger;

import com.mojang.brigadier.CommandDispatcher;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.text.LiteralText;

public class MyCommands implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {

        

        
        System.out.println("reached");

        dispatcher.register(ArgumentBuilders.literal("client-commands").executes(
            source -> {
                // String value = (String)health_n_hunger.get("status").toString();
                // System.out.println(value);
                source.getSource().sendFeedback(new LiteralText("value"));
                return 1;
            }
        ));
    }
}