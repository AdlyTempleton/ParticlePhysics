package pixlepix.particlephysics.common.tile;

import java.util.EnumSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.api.IParticleReceptor;
import universalelectricity.prefab.tile.TileEntityElectrical;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class SeriesReceptorTileEntity extends TileEntityElectrical implements IParticleReceptor, IPowerEmitter, IPowerReceptor {

	
	public int excitedTicks=0;

	protected PowerHandler powerHandler;
	public float powerToDischarge=0;
	@Override
	public void onContact(BaseParticle particle) {
		if(this.excitedTicks>0){
			this.receiveElectricity(0.00012F*particle.potential,true);
		}else{
			this.receiveElectricity(0.00004F*particle.potential, true);
		}
		this.excitedTicks=20;
		particle.setDead();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		this.sendPower();
		this.produce();
		this.excitedTicks--;
	}	
	
	@Override
	public float getRequest(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		return 2000;
	}

	@Override
	public float getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return 100000;
	}
	
	@Override
	public EnumSet<ForgeDirection> getOutputDirections()
	{
		return EnumSet.allOf(ForgeDirection.class);
	}

	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	//Stole from BC TileEngine
	public boolean isPoweredTile(TileEntity tile, ForgeDirection side) {
		if (tile instanceof IPowerReceptor)
			return ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite()) != null;

		return false;
	}
	public float extractEnergy(float min, float max, boolean doExtract) {
		if (this.energyStored < min)
			return 0;

		float actualMax;

		//Horrible code
		actualMax = max;

		if (actualMax < min)
			return 0;

		float extracted;

		if (this.energyStored >= actualMax) {
			extracted = actualMax;
			if (doExtract)
				this.energyStored -= actualMax;
		} else {
			extracted = this.energyStored;
			if (doExtract)
				this.energyStored = 0;
		}

		return extracted;
	}
	private float getPowerToExtract(TileEntity tile,PowerReceiver receptor) {
		return extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false); // Comment out for constant power

	}
	private void sendPower() {
		for(ForgeDirection orientation:ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = worldObj.getBlockTileEntity(xCoord+orientation.offsetX, yCoord+orientation.offsetY, zCoord+orientation.offsetZ);
			if (isPoweredTile(tile, orientation)) {
				PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(orientation.getOpposite());
	
				float extracted = getPowerToExtract(tile,receptor);
				if (extracted > 0) {
					float needed = receptor.receiveEnergy(PowerHandler.Type.ENGINE, extracted, orientation.getOpposite());
					extractEnergy(receptor.getMinEnergyReceived(), needed, true);
				}
			}
		}	
	}
	
	
	
	
	
	
}
