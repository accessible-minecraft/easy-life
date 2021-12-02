package net.shoaibkhan.easy.life.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
//import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Vec3f;
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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.utils.Colors;

public class NarratorMenuGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    public MinecraftClient client;

    public NarratorMenuGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;


        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        
        WButton wb11 = new WButton(new LiteralText("Target Information"));
        wb11.setOnClick(this::target_information);
        root.add(wb11, 1, 1, 7, 1);
        
        WButton wb12 = new WButton(new LiteralText("Target Position"));
        wb12.setOnClick(this::target_position);
        root.add(wb12, 9, 1, 7, 1);;

        WButton wb22 = new WButton(new LiteralText("Light Level"));
        wb22.setOnClick(this::light_level);
        root.add(wb22, 1, 3, 7, 1);

        WButton wb23 = new WButton(new LiteralText("Biome"));
        wb23.setOnClick(this::getBiome);
        root.add(wb23, 9, 3, 7, 1);

		WLabel labelForPadding = new WLabel(new LiteralText(""), Colors.colors("red",100));
		root.add(labelForPadding, 0, 4, 17, 1);

		root.validate(this);
        
//        WButton wb31 = new WButton(new LiteralText("Time Of Day"));
//        wb31.setOnClick(this::getTimeOfDay);
//        root.add(wb31, 4, 4, 7, 1);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Colors.colors("lightgrey",50)));
    }
    
    private void target_information() {
    	try {
			this.player.closeScreen();

			HitResult hit = get_target();
			 
			switch(hit.getType()) {
			    case MISS:
	                Initial.narrate("Too far");;
			    	break; 
			    case BLOCK:{
			        try {
						BlockHitResult blockHit = (BlockHitResult) hit;
						BlockPos blockPos = blockHit.getBlockPos();
						BlockState blockState = client.world.getBlockState(blockPos);
						Block block = blockState.getBlock();
						MutableText mutableText = new LiteralText("").append(block.getName());
						String text = "" + mutableText.getString() + ", " + get_position_difference(blockPos);
						Initial.narrate(text);
					} catch (Exception e) {
						e.printStackTrace();
					}
			        
			        break;
			    }
			    case ENTITY:{
			    	try {
	                    EntityHitResult entityHitResult = (EntityHitResult) hit;
	                    String name = "";
	                    MutableText mutableText = new LiteralText("").append(entityHitResult.getEntity().getName());
	                    name = mutableText.getString();
	                    BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
	                    String text = "" + name + ", " + get_position_difference(blockPos);
						Initial.narrate(text);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
                    break;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
  
    private void target_position(){
        try {
			this.player.closeScreen();
			HitResult hit = get_target();
			switch (hit.getType()) {
			    case MISS:
			        	Initial.narrate("Too far");;
			        break;
			    case BLOCK: {
			        try {
						BlockHitResult blockHitResult = (BlockHitResult) hit;
						BlockPos blockPos = blockHitResult.getBlockPos();
						Initial.narrate(get_position(blockPos));
					} catch (Exception e) {
						e.printStackTrace();
					}
			        break;
			    }
			    case ENTITY: {
			    	try {
						EntityHitResult entityHitResult = (EntityHitResult) hit;
						BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
						Initial.narrate(get_position(blockPos));
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			    default:
			        break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void light_level(){
        try {
			this.player.closeScreen();
			int light = this.client.world.getLightLevel(this.player.getBlockPos());
			Initial.narrate("Light level is, "+ light);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void getBiome() {
        try {
			this.player.closeScreen();
			Identifier id = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(client.world.getBiome(player.getBlockPos()));
			String name = I18n.translate("biome." + id.getNamespace() + "." + id.getPath());
			Initial.narrate(""+name+" biome");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private String get_position_difference(BlockPos blockPos) {
    	String dir = client.player.getHorizontalFacing().asString();
        dir = dir.toLowerCase().trim();
        
        String diffXBlockPos = ((double)player.getBlockPos().getX() - blockPos.getX()) + "";
        String diffYBlockPos = ((double)(player.getBlockPos().getY()+1) - blockPos.getY())+ "";
        String diffZBlockPos = ((double)player.getBlockPos().getZ() - blockPos.getZ()) + "";
        
        diffXBlockPos = diffXBlockPos.substring(0, diffXBlockPos.indexOf("."));
        diffYBlockPos = diffYBlockPos.substring(0, diffYBlockPos.indexOf("."));
        diffZBlockPos = diffZBlockPos.substring(0, diffZBlockPos.indexOf("."));
        
        if(!diffXBlockPos.equalsIgnoreCase("0")) {
        	if(dir.contains("east")||dir.contains("west")) {
        		if(diffXBlockPos.contains("-")) diffXBlockPos = diffXBlockPos.replace("-", "");
        		diffXBlockPos += " blocks away";
        	} else if(dir.contains("north")) {
        		if(diffXBlockPos.contains("-")) diffXBlockPos += " blocks to left";
        		else diffXBlockPos += " blocks to right";
        		if(diffXBlockPos.contains("-")) diffXBlockPos = diffXBlockPos.replace("-", "");
        	} else if(dir.contains("south")) {
        		if(diffXBlockPos.contains("-")) diffXBlockPos += " blocks to right";
        		else diffXBlockPos += " blocks to left";
        		if(diffXBlockPos.contains("-")) diffXBlockPos = diffXBlockPos.replace("-", "");
        	}
        } else {
        	diffXBlockPos = "";
        }
        
        if(!diffYBlockPos.equalsIgnoreCase("0")) {
        	if(diffYBlockPos.contains("-")) {
        		diffYBlockPos = diffYBlockPos.replace("-", "");
        		diffYBlockPos += " blocks up";
        	} else {
        		diffYBlockPos += " blocks down";
        	}
        } else {
        	diffYBlockPos = "";
        }
        
        if(!diffZBlockPos.equalsIgnoreCase("0")) {
        	if(dir.contains("north")||dir.contains("south")) {
        		if(diffZBlockPos.contains("-")) diffZBlockPos = diffZBlockPos.replace("-", "");
        		diffZBlockPos += " blocks away";
        	} else if(dir.contains("east")) {
        		if(diffZBlockPos.contains("-")) diffZBlockPos += " blocks to right";
        		else diffZBlockPos += " blocks to left";
        		if(diffZBlockPos.contains("-")) diffZBlockPos = diffZBlockPos.replace("-", "");
        	} else if(dir.contains("west")) {
        		if(diffZBlockPos.contains("-")) diffZBlockPos += " blocks to left";
        		else diffZBlockPos += " blocks to right";
        		if(diffZBlockPos.contains("-")) diffZBlockPos = diffZBlockPos.replace("-", "");
        	}
        } else {
        	diffZBlockPos = "";
        }
        
        String text = "";
        if(dir.contains("north")||dir.contains("south")) text = String.format("%s  %s  %s", diffZBlockPos, diffYBlockPos, diffXBlockPos);
        else text = String.format("%s  %s  %s", diffXBlockPos, diffYBlockPos, diffZBlockPos);
        return text;
    }
    
    private String get_position(BlockPos blockPos) {
    	try {
			String posX = ((double)blockPos.getX())+"";
			String posY = ((double)blockPos.getY())+"";
			String posZ = ((double)blockPos.getZ())+"";
			posX = posX.substring(0, posX.indexOf("."));
			posY = posY.substring(0, posY.indexOf("."));
			posZ = posZ.substring(0, posZ.indexOf("."));
			if(posX.contains("-")) posX = posX.replace("-", "negative");
			if(posY.contains("-")) posY = posY.replace("-", "negative");
			if(posZ.contains("-")) posZ = posZ.replace("-", "negative");
			return String.format("%s x %s y %s z", posX, posY, posZ);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
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

	// 1.16
	/*
    private static Vec3d map(float anglePerPixel, Vec3d center, Vector3f horizontalRotationAxis, Vector3f verticalRotationAxis, int x, int y, int width, int height) {
    	    float horizontalRotation = (x - width/2f) * anglePerPixel;
    	    float verticalRotation = (y - height/2f) * anglePerPixel;

    	    final Vector3f temp2 = new Vector3f(center);
    	    temp2.rotate(verticalRotationAxis.getDegreesQuaternion(verticalRotation));
    	    temp2.rotate(horizontalRotationAxis.getDegreesQuaternion(horizontalRotation));
    	    return new Vec3d(temp2);
    	}

    private HitResult get_target() {
		int width = client.getWindow().getScaledWidth();
		int height = client.getWindow().getScaledHeight();
		Vec3d cameraDirection = client.cameraEntity.getRotationVec(client.getTickDelta());
		double fov = client.options.fov;
		double angleSize = fov/height;
		Vector3f verticalRotationAxis = new Vector3f(cameraDirection);
		verticalRotationAxis.cross(Vector3f.POSITIVE_Y);

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
		return raycastInDirection(client, client.getTickDelta(), direction, 5f);
    }*/

	// 1.17
	private static Vec3d map(float anglePerPixel, Vec3d center, Vec3f horizontalRotationAxis, Vec3f verticalRotationAxis, int x, int y, int width, int height) {
		float horizontalRotation = (x - width/2f) * anglePerPixel;
		float verticalRotation = (y - height/2f) * anglePerPixel;

		final Vec3f temp2 = new Vec3f(center);
		temp2.rotate(verticalRotationAxis.getDegreesQuaternion(verticalRotation));
		temp2.rotate(horizontalRotationAxis.getDegreesQuaternion(horizontalRotation));
		return new Vec3d(temp2);
	}

	private HitResult get_target() {
		int width = client.getWindow().getScaledWidth();
		int height = client.getWindow().getScaledHeight();
		Vec3d cameraDirection = client.cameraEntity.getRotationVec(client.getTickDelta());
		double fov = client.options.fov;
		double angleSize = fov/height;
		Vec3f verticalRotationAxis = new Vec3f(cameraDirection);
		verticalRotationAxis.cross(Vec3f.POSITIVE_Y);

		Vec3f horizontalRotationAxis = new Vec3f(cameraDirection);
		horizontalRotationAxis.cross(verticalRotationAxis);
		horizontalRotationAxis.normalize();

		verticalRotationAxis = new Vec3f(cameraDirection);
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
		return raycastInDirection(client, client.getTickDelta(), direction, 5f);
	}
}
