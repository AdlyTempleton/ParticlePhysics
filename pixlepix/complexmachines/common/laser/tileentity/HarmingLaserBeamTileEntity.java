package pixlepix.complexmachines.common.laser.tileentity;

import java.util.List;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class HarmingLaserBeamTileEntity extends LaserBeamTileEntity {

public int ticks=0;
	public void updateEntity(){
		ticks++;
		if (ticks > 40) {
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		}
		super.updateEntity();
		List<EntityLiving> entities=worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getBoundingBox(xCoord-.5, yCoord-.5, zCoord-.5, xCoord+.5, yCoord+.5, zCoord+.5));
		//System.out.println(entities);
		for(int i=0;i<entities.size();i++){
			
			entities.get(i).attackEntityFrom(DamageSource.onFire, 3);
			
		}
	}
}
