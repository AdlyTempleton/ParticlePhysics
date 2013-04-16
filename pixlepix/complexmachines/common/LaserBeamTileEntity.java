package pixlepix.complexmachines.common;

import net.minecraft.tileentity.TileEntity;

public class LaserBeamTileEntity extends TileEntity {
	public int ticks=0;
	private boolean initialized;
	public void updateEntity(){
		ticks++;
		if(ticks>190){
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);
		}
		
	}
	public void initiate() {
		this.initialized = true;
	}
}
