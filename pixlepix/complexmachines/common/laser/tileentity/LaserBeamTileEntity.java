package pixlepix.complexmachines.common.laser.tileentity;

import net.minecraft.tileentity.TileEntity;

public class LaserBeamTileEntity extends TileEntity {
	public int ticks = 0;
	private boolean initialized;

	public void updateEntity() {
		//System.out.println(ticks);
		ticks++;
		if (ticks > 100) {
			worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			
		}

	}
	
	public void assure(){
		//System.out.println("Assuring");
		this.ticks=0;
		//System.out.println("Ticks at reassure:"+ticks);
	}

	public void initiate() {
		this.initialized = true;
	}
}
