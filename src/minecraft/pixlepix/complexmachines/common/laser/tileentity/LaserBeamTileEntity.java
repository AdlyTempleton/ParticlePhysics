package pixlepix.complexmachines.common.laser.tileentity;

import pixlepix.complexmachines.common.laser.LaserEmitterTileEntity;
import net.minecraft.tileentity.TileEntity;

public class LaserBeamTileEntity extends TileEntity {
	public int ticks = 0;
	private boolean initialized;

	public LaserEmitterTileEntity entity;
	public void setEntity(LaserEmitterTileEntity entity){
		this.entity=entity;
	}
	
	public void destroy(){
		worldObj.setBlock(xCoord, yCoord, zCoord, 0);

		this.invalidate();
	}
	public void updateEntity() {
		//System.out.println(ticks);
		
		ticks++;
		if (ticks > 600) {
			
			if(entity!=null){
				entity.notifyDecay();
				
			}
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);

			this.invalidate();
			
		}

	}
	
	

	public void initiate() {
		this.initialized = true;
	}
}
