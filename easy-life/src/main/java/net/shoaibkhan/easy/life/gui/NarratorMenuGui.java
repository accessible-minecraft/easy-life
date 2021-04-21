package net.shoaibkhan.easy.life.gui;

import java.util.Hashtable;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.shoaibkhan.easy.life.ClientMod;
import net.shoaibkhan.easy.life.gui.widgets.NarratorMenuButton;

public class NarratorMenuGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    public static MinecraftClient client;
    public static Hashtable<String,Boolean> focused = new Hashtable<String,Boolean>();
    public static int timeout = 0;
    public static String curFocused = "";

    public NarratorMenuGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        NarratorMenuGui.client = client;
        focused.put("Target Block Type", false);
        focused.put("Target Block Position", false);
        focused.put("Chunk Position", false);
        focused.put("Light Level", false);
        focused.put("Target Entity", false);


        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        
        NarratorMenuButton wb11 = new NarratorMenuButton(new LiteralText("Target Block Type"));
        wb11.setOnClick(this::target_block_type);
        root.add(wb11, 4, 0, 7, 1);

        NarratorMenuButton wb12 = new NarratorMenuButton(new LiteralText("Target Block Position"));
        wb12.setOnClick(this::target_block_position);
        root.add(wb12, 12, 0, 7, 1);



        NarratorMenuButton wb21 = new NarratorMenuButton(new LiteralText("Chunk Position"));
        wb21.setOnClick(this::chunk_position);
        root.add(wb21, 0, 2, 7, 1);

        NarratorMenuButton wb22 = new NarratorMenuButton(new LiteralText("Light Level"));
        wb22.setOnClick(this::light_level);
        root.add(wb22, 8, 2, 7, 1);

        NarratorMenuButton wb23 = new NarratorMenuButton(new LiteralText("Target Entity"));
        wb23.setOnClick(this::target_entity);
        root.add(wb23, 16, 2, 7, 1);
        
        
        
        NarratorMenuButton wb31 = new NarratorMenuButton(new LiteralText("Time Of Day"));
        wb31.setOnClick(this::getTimeOfDay);
        root.add(wb31, 4, 4, 7, 1);

        NarratorMenuButton wb32 = new NarratorMenuButton(new LiteralText("Biome"));
        wb32.setOnClick(this::getBiome);
        root.add(wb32, 12, 4, 7, 1);



        root.validate(this);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(ClientMod.colors("lightgrey",50)));
    }

    private void target_block_type(){
        this.player.closeScreen();
        HitResult hit = NarratorMenuGui.client.crosshairTarget;
        switch (hit.getType()) {
            case MISS:
                this.player.sendMessage(new LiteralText("Target is too far"), true);
                break;
            case BLOCK:
                BlockHitResult blockHitResult = (BlockHitResult) hit;
                String name = NarratorMenuGui.client.world.getBlockState(blockHitResult.getBlockPos()).getBlock()+"";
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
        HitResult hit = NarratorMenuGui.client.crosshairTarget;
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
        int light = NarratorMenuGui.client.world.getLightLevel(this.player.getBlockPos());
        this.player.sendMessage(new LiteralText("Light level is, "+ light) ,true);
    }

    private void target_entity(){
        this.player.closeScreen();
        HitResult hit = NarratorMenuGui.client.crosshairTarget;
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
    
    private void getBiome() {
        this.player.closeScreen();
    	Identifier id = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(client.world.getBiome(player.getBlockPos()));
    	String name = I18n.translate("biome." + id.getNamespace() + "." + id.getPath());
    	this.player.sendMessage(new LiteralText(""+name+" biome") ,true);
    }
    
    private void getTimeOfDay() {
        this.player.closeScreen();
        String string = "";
        long time = client.world.getTimeOfDay();
        System.out.print(time);
        int hour = (int)Math.floor(time/1000);
        hour += 6;
        if(hour>=24) hour-=24;
        if(hour>=0 && hour<12) string += " " + hour + " A.M.";
        else if(hour>=13 && hour<24) string += " " + ((int)hour-12) + " P.M.";
        else if(hour==12) string += " 12 P.M.";
        int minutes = (int)time%1000;
        minutes = (int)(minutes/16.6);
        if(minutes>=15) string += " " + minutes + " minutes";
    	this.player.sendMessage(new LiteralText("Time is,"+string) ,true);
    }
    
}
