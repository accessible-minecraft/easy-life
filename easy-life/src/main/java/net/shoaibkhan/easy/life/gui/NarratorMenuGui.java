package net.shoaibkhan.easy.life.gui;

import java.util.Hashtable;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
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
        
        NarratorMenuButton wb11 = new NarratorMenuButton(new LiteralText("Target Block Distance"));
        wb11.setOnClick(this::target_block_distance);
        root.add(wb11, 0, 0, 7, 1);
        
        NarratorMenuButton wb12 = new NarratorMenuButton(new LiteralText("Target Block Type"));
        wb12.setOnClick(this::target_block_type);
        root.add(wb12, 8, 0, 7, 1);

        NarratorMenuButton wb13 = new NarratorMenuButton(new LiteralText("Target Block Position"));
        wb13.setOnClick(this::target_block_position);
        root.add(wb13, 16, 0, 7, 1);



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

    private void target_block_distance() {
    	this.player.closeScreen();
		int width = client.getWindow().getScaledWidth();
    	int height = client.getWindow().getScaledHeight();
    	Vec3d cameraDirection = client.cameraEntity.getRotationVec(client.getTickDelta());
    	double fov = client.options.fov;
    	double angleSize = fov/height;
    	Vector3f verticalRotationAxis = new Vector3f(cameraDirection);
    	verticalRotationAxis.cross(Vector3f.POSITIVE_Y);
    	if(!verticalRotationAxis.normalize()) {
    	    return;//The camera is pointing directly up or down, you'll have to fix this one
    	}
    	 
    	Vector3f horizontalRotationAxis = new Vector3f(cameraDirection);
    	horizontalRotationAxis.cross(verticalRotationAxis);
    	horizontalRotationAxis.normalize();
    	 
    	verticalRotationAxis = new Vector3f(cameraDirection);
    	verticalRotationAxis.cross(horizontalRotationAxis);
    	
    	int x = width/2;
    	int y = height/2;

		Vec3d direction = map(
				(float) angleSize,
				cameraDirection,
				horizontalRotationAxis,
				verticalRotationAxis,
				x,
				y,
				width,
				height
		);
		
		
		
		HitResult hit = raycastInDirection(client, client.getTickDelta(), direction, 3f);
		 
		switch(hit.getType()) {
		    case MISS:
		        //nothing near enough
		    	break; 
		    case BLOCK:
		        BlockHitResult blockHit = (BlockHitResult) hit;
		        BlockPos blockPos = blockHit.getBlockPos();
		        BlockState blockState = client.world.getBlockState(blockPos);
		        Block block = blockState.getBlock();
		        MutableText mutableText = new LiteralText("").append(block.getName());
		        String diffXBlockPos = ((double)player.getBlockPos().getX() - blockPos.getX()) + "";
		        String diffYBlockPos = ((double)player.getBlockPos().getY() - blockPos.getY())+ "";
		        String diffZBlockPos = ((double)player.getBlockPos().getZ() - blockPos.getZ()) + "";
		        diffXBlockPos = diffXBlockPos.substring(0, diffXBlockPos.indexOf("."));
		        diffYBlockPos = diffYBlockPos.substring(0, diffYBlockPos.indexOf("."));
		        diffZBlockPos = diffZBlockPos.substring(0, diffZBlockPos.indexOf("."));
		        String text = String.format("%s %s x %s y %s z", mutableText.getString(), diffXBlockPos, diffYBlockPos, diffZBlockPos);
		        player.sendMessage(new LiteralText(text), true);
		        break; 
		    case ENTITY:
		    	break;
		}
    	
    	
    }
    
    private static HitResult raycastInDirection(MinecraftClient client, float tickDelta, Vec3d direction, float extendReachBy) {
        Entity entity = client.getCameraEntity();
        if (entity == null || client.world == null) {
            return null;
        }
     
        double reachDistance = client.interactionManager.getReachDistance() * extendReachBy;//Change this to extend the reach
        HitResult target = raycast(entity, reachDistance, tickDelta, false, direction);
        boolean tooFar = false;
        double extendedReach = reachDistance;
        if (client.interactionManager.hasExtendedReach()) {
            extendedReach = 6.0D;//Change this to extend the reach
            reachDistance = extendedReach;
        } else {
            if (reachDistance > 3.0D) {
                tooFar = true;
            }
        }
     
        Vec3d cameraPos = entity.getCameraPosVec(tickDelta);
     
        extendedReach = extendedReach * extendedReach;
        if (target != null) {
            extendedReach = target.getPos().squaredDistanceTo(cameraPos);
        }
     
        Vec3d vec3d3 = cameraPos.add(direction.multiply(reachDistance));
        Box box = entity
                .getBoundingBox()
                .stretch(entity.getRotationVec(1.0F).multiply(reachDistance))
                .expand(1.0D, 1.0D, 1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(
                entity,
                cameraPos,
                vec3d3,
                box,
                (entityx) -> !entityx.isSpectator() && entityx.collides(),
                extendedReach
        );
     
        if (entityHitResult == null) {
            return target;
        }
     
        Entity entity2 = entityHitResult.getEntity();
        Vec3d vec3d4 = entityHitResult.getPos();
        double g = cameraPos.squaredDistanceTo(vec3d4);
        if (tooFar && g > 9.0D) {
            return null;
        } else if (g < extendedReach || target == null) {
            target = entityHitResult;
            if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrameEntity) {
                client.targetedEntity = entity2;
            }
        }
     
        return target;
    }
     
    private static HitResult raycast(
            Entity entity,
            double maxDistance,
            float tickDelta,
            boolean includeFluids,
            Vec3d direction
    ) {
        Vec3d end = entity.getCameraPosVec(tickDelta).add(direction.multiply(maxDistance));
        return entity.world.raycast(new RaycastContext(
                entity.getCameraPosVec(tickDelta),
                end,
                RaycastContext.ShapeType.OUTLINE,
                includeFluids ? RaycastContext.FluidHandling.ANY : RaycastContext.FluidHandling.NONE,
                entity
        ));
    }
    
    private static Vec3d map(float anglePerPixel, Vec3d center, Vector3f horizontalRotationAxis,
    	    Vector3f verticalRotationAxis, int x, int y, int width, int height) {
    	    float horizontalRotation = (x - width/2f) * anglePerPixel;
    	    float verticalRotation = (y - height/2f) * anglePerPixel;
    	 
    	    final Vector3f temp2 = new Vector3f(center);
    	    temp2.rotate(verticalRotationAxis.getDegreesQuaternion(verticalRotation));
    	    temp2.rotate(horizontalRotationAxis.getDegreesQuaternion(horizontalRotation));
    	    return new Vec3d(temp2);
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
