package pixlepix.complexmachines.common.laser.tileentity;

import java.util.List;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class SuctionLaserBeamTileEntity extends LaserBeamTileEntity {
	public int xDirection=0;
	public int zDirection=0;
	public void updateEntity(){
		
		super.updateEntity();
		List<Entity> entities=worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord-1.5, yCoord-1.5, zCoord-1.5, xCoord+1.5, yCoord+1.5, zCoord+1.5));
		//System.out.println(entities);
		for(int i=0;i<entities.size();i++){
			Entity entity=entities.get(i);
			
			if(xDirection!=0||zDirection!=0){
				if(entity instanceof EntityPlayer){
					EntityPlayer player=(EntityPlayer)entity;
					player.setPosition(.05*xDirection+player.posX, player.posY, .05*zDirection+player.posZ);
				}else{
					entity.addVelocity(xDirection, 0, zDirection);
				}
				
			}
			
		}
	}
	

}
