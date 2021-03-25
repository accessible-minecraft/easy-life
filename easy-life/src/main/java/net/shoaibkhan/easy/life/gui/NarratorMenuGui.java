package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.shoaibkhan.easy.life.ClientMod;

public class NarratorMenuGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    private MinecraftClient client;

    public NarratorMenuGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;
        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        
        WButton wb11 = new WButton(new LiteralText("Target Block Type"));
        wb11.setOnClick(this::target_block_type);
        root.add(wb11, 8, 0, 7, 1);

        WButton wb12 = new WButton(new LiteralText("Target Block Postion"));
        wb12.setOnClick(this::target_block_position);
        root.add(wb12, 8, 4, 7, 1);



        WButton wb21 = new WButton(new LiteralText("Chunk Position"));
        wb21.setOnClick(this::chunk_position);
        root.add(wb21, 0, 2, 7, 1);

        WButton wb22 = new WButton(new LiteralText("Light Level"));
        wb22.setOnClick(this::light_level);
        root.add(wb22, 8, 2, 7, 1);

        WButton wb23 = new WButton(new LiteralText("Target Entity"));
        wb23.setOnClick(this::target_entity);
        root.add(wb23, 16, 2, 7, 1);



        root.validate(this);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey",50)));
    }

    private void target_block_type(){
        this.player.closeScreen();
        HitResult hit = this.client.crosshairTarget;
        switch (hit.getType()) {
            case MISS:
                this.player.sendMessage(new LiteralText("Target is too far"), true);
                break;
            case BLOCK:
                BlockHitResult blockHitResult = (BlockHitResult) hit;
                String name = this.client.world.getBlockState(blockHitResult.getBlockPos()).getBlock()+"";
                name = name.toLowerCase().trim();
                if(name.contains("block{")) name = name.replace("block{", "");
                if(name.contains("minecraft:")) name = name.replace("minecraft:", "");
                if(name.contains("}")) name = name.replace("}", "");
                if(name.contains("{")) name = name.replace("{", "");
                if(name.contains("_")) name = name.replace("_", " ");
                this.player.sendMessage(new LiteralText(""+name) ,true);
                break;
            default:
                break;
        }
    }

    private void target_block_position(){
        this.player.closeScreen();
        HitResult hit = this.client.crosshairTarget;
        switch (hit.getType()) {
            case MISS:
                this.player.sendMessage(new LiteralText("Target is too far"), true);
                break;
            case BLOCK:
                BlockHitResult blockHitResult = (BlockHitResult) hit;
                Vec3d pos = blockHitResult.getPos();
                String posX = ((double)pos.x)+"";
                String posY = ((double)pos.y)+"";
                String posZ = ((double)pos.z)+"";
                posX = posX.substring(0, posX.indexOf("."));
                posY = posY.substring(0, posY.indexOf("."));
                posZ = posZ.substring(0, posZ.indexOf("."));
                if(posX.contains("-")) posX = posX.replace("-", "negative");
                if(posY.contains("-")) posY = posY.replace("-", "negative");
                if(posZ.contains("-")) posZ = posZ.replace("-", "negative");
                this.player.sendMessage(new LiteralText("Block Position is, "+ posX + "x, " + posY + "y, " + posZ + "z") ,true);
                break;
            default:
                break;
        }
    }

    private void chunk_position(){
        this.player.closeScreen();
        String posX = ""+this.player.chunkX;
        String posY = ""+this.player.chunkY;
        String posZ = ""+this.player.chunkZ;
        if(posX.contains("-")) posX = posX.replace("-", "negative");
        if(posY.contains("-")) posY = posY.replace("-", "negative");
        if(posZ.contains("-")) posZ = posZ.replace("-", "negative");
        this.player.sendMessage(new LiteralText("Chunk Position is, "+ posX + "x, " + posY + "y, " + posZ + "z") ,true);
    }

    private void light_level(){
        this.player.closeScreen();
        int light = this.client.world.getLightLevel(this.player.getBlockPos());
        this.player.sendMessage(new LiteralText("Light level is, "+ light) ,true);
    }

    private void target_entity(){
        this.player.closeScreen();
        HitResult hit = this.client.crosshairTarget;
        switch (hit.getType()) {
            case MISS:
                this.player.sendMessage(new LiteralText("Target is too far"), true);
                break;
            case ENTITY:
                try {
                    EntityHitResult entityHitResult = (EntityHitResult) hit;
                    String name = entityHitResult.getEntity().getType()+"";
                    if(name.contains("entity.minecraft.")) name = name.replace("entity.minecraft.", "");
                    this.player.sendMessage(new LiteralText(""+name) ,true);
                    break;
                } catch (Exception e) {
                    this.player.sendMessage(new LiteralText("No Entity Present") ,true);
                    break;
                }
                
            default:
                break;
        }
    }


}
