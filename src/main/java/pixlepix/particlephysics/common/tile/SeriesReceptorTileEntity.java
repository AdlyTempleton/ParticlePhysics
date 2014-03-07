package pixlepix.particlephysics.common.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.api.IParticleReceptor;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;

public class SeriesReceptorTileEntity extends TileEnergyHandler implements IParticleReceptor {
	
	public int excitedTicks=0;

	public float powerToDischarge=0;
	
	protected EnergyStorage storage = new EnergyStorage(320000000);
	
	//28000 RF per coal
	//280 RF per emission
	//70 RF per coal particle
	//Potential of 4000
	
	//keep this constant matched to equal 1 coal in steam dynamo
	public static final float constant = .02F;
	public static final float excitedConstant = constant * 3;
	
	@Override
	public void onContact(BaseParticle particle) {

		if(this.excitedTicks>0){
			//excited state, energy generated is 3 times the normal amount, state lasts for 20 ticks
			this.storage.modifyEnergyStored(((int)(excitedConstant*particle.potential)));
		}else{
			
			this.storage.modifyEnergyStored(((int)(constant*particle.potential)));
		}
		this.excitedTicks=20;
		particle.setDead();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		this.excitedTicks--;
		//send power out to adjacent storage units
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
			TileEntity te=worldObj.getBlockTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
			if(te !=null && te instanceof IEnergyHandler){
				IEnergyHandler energy=(IEnergyHandler) te;
				if(energy.canInterface(dir.getOpposite())){

					this.storage.modifyEnergyStored(-1*energy.receiveEnergy(dir.getOpposite(), this.getEnergyStored(null), false));
				}
			}
		}
	}	
	
	

}
