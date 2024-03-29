package net.shoaibkhan.easy.life.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.shoaibkhan.easy.life.Initial;

public class BiomeIndicator extends Thread {
    private boolean running;

    public void startThread() {
        this.running = true;
        this.start();
    }

    public void stopThread() {
        this.running = false;
        this.interrupt();
    }

    public void run() {
        while (running) {
            try {
                MinecraftClient client = MinecraftClient.getInstance();
                assert client.world != null;
                assert client.player != null;

                Identifier id = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(client.world.getBiome(client.player.getBlockPos()).value()); // post 1.18
//                Identifier id = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(client.world.getBiome(client.player.getBlockPos())); // pre 1.18

                if (id == null) {
                    System.out.println("\n\nUnable to detect biome!!\n\n");
                    return;
                }

                String name = I18n.translate("biome." + id.getNamespace() + "." + id.getPath());

                if (!Initial.biomeIndicatorString.equalsIgnoreCase(name)) {
                    Initial.biomeIndicatorString = name;
                    String toNarrate = name + " biome entered.";
                    Initial.narrate(toNarrate);
                }

                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
