package pixlepix.complexmachines.common.block;

import net.minecraft.tileentity.TileEntity;

public class IntermediateFluxTileEntity extends TileEntity {

	private boolean initialized;

	public void initiate() {
		this.initialized = true;
	}
	
}
