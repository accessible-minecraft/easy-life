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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.RaycastContext;
import net.shoaibkhan.easy.life.Initial;
import net.shoaibkhan.easy.life.utils.Colors;
import net.shoaibkhan.easy.life.utils.PlayerPosition;

public class NarratorMenuGui extends LightweightGuiDescription {
    private ClientPlayerEntity player;
    public MinecraftClient client;

    public NarratorMenuGui(ClientPlayerEntity player,MinecraftClient client){
        this.player = player;
        this.client = client;


        WGridPanel root = new WGridPanel();

        setRootPanel(root);
        
        WButton wb11 = new WButton(new TranslatableText("gui.easylife.targetInfo"));
        wb11.setOnClick(this::target_information);
        root.add(wb11, 1, 1, 7, 1);
        
        WButton wb12 = new WButton(new TranslatableText("gui.easylife.targetPosition"));
        wb12.setOnClick(this::target_position);
        root.add(wb12, 9, 1, 7, 1);;

        WButton wb22 = new WButton(new TranslatableText("gui.easylife.light"));
        wb22.setOnClick(this::light_level);
        root.add(wb22, 1, 3, 7, 1);

        WButton wb23 = new WButton(new TranslatableText("gui.easylife.biome"));
        wb23.setOnClick(this::getBiome);
        root.add(wb23, 9, 3, 7, 1);

		WLabel labelForPadding = new WLabel(LiteralText.EMPTY, Colors.colors("red",100));
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
			if (hit == null)
				return;
			 
			switch(hit.getType()) {
			    case MISS:
	                Initial.narrate(I18n.translate("narrate.easylife.tooFar"));
			    	break; 
			    case BLOCK:{
			        try {
						BlockHitResult blockHit = (BlockHitResult) hit;
						BlockPos blockPos = blockHit.getBlockPos();
						BlockState blockState = client.world.getBlockState(blockPos);
						Block block = blockState.getBlock();
						MutableText mutableText = block.getName();
						String text = mutableText.getString() + ", " + get_position_difference(blockPos);
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
	                    name = entityHitResult.getEntity().getName().getString();
	                    BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
	                    String text = name + ", " + get_position_difference(blockPos);
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
			if (hit == null)
				return;
			
			switch (hit.getType()) {
			    case MISS:
	                Initial.narrate(I18n.translate("narrate.easylife.tooFar"));
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
			Initial.narrate(I18n.translate("narrate.easylife.light", light));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    private void getBiome() {
        try {
			this.player.closeScreen();
			Identifier id = client.world.getRegistryManager().get(Registry.BIOME_KEY).getId(client.world.getBiome(player.getBlockPos()));
			String name = I18n.translate("biome." + id.getNamespace() + "." + id.getPath());
			Initial.narrate(I18n.translate("narrate.easylife.biome", name));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public String get_position_difference(BlockPos blockPos) {
		ClientPlayerEntity player = client.player;
		Direction dir = client.player.getHorizontalFacing();

		Vec3d diff = player.getEyePos().subtract(Vec3d.ofCenter(blockPos));
		BlockPos diffBlockPos = new BlockPos(Math.round(diff.x), Math.round(diff.y), Math.round(diff.z));

		String diffXBlockPos = "";
		String diffYBlockPos = "";
		String diffZBlockPos = "";

		if (diffBlockPos.getX() != 0) {
			if (dir == Direction.NORTH) {
				diffXBlockPos = diff(diffBlockPos.getX(), "right", "left");
			} else if (dir == Direction.SOUTH) {
				diffXBlockPos = diff(diffBlockPos.getX(), "left", "right");
			} else if (dir == Direction.EAST) {
				diffXBlockPos = diff(diffBlockPos.getX(), "away", "behind");
			} else if (dir == Direction.WEST) {
				diffXBlockPos = diff(diffBlockPos.getX(), "behind", "away");
			}
		}

		if (diffBlockPos.getY() != 0) {
			diffYBlockPos = diff(diffBlockPos.getY(), "up", "down");
		}

		if (diffBlockPos.getZ() != 0) {
			if (dir == Direction.SOUTH) {
				diffZBlockPos = diff(diffBlockPos.getZ(), "away", "behind");
			} else if (dir == Direction.NORTH) {
				diffZBlockPos = diff(diffBlockPos.getZ(), "behind", "away");
			} else if (dir == Direction.EAST) {
				diffZBlockPos = diff(diffBlockPos.getZ(), "right", "left");
			} else if (dir == Direction.WEST) {
				diffZBlockPos = diff(diffBlockPos.getZ(), "left", "right");
			}
		}

		String text = "";
		if (dir == Direction.NORTH || dir == Direction.SOUTH)
			text = String.format("%s  %s  %s", diffZBlockPos, diffYBlockPos, diffXBlockPos);
		else
			text = String.format("%s  %s  %s", diffXBlockPos, diffYBlockPos, diffZBlockPos);
		return text;
	}

	private static String diff(int blocks, String key1, String key2) {
		return I18n.translate("narrate.easylife.posDiff." + (blocks < 0 ? key1 : key2), Math.abs(blocks));
	}
    
    private String get_position(BlockPos blockPos) {
    	try {
			String posX = PlayerPosition.getNarratableNumber(blockPos.getX());
			String posY = PlayerPosition.getNarratableNumber(blockPos.getY());
			String posZ = PlayerPosition.getNarratableNumber(blockPos.getZ());
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
