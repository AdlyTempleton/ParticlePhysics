package pixlepix.particlephysics.common.tile;

import java.util.EnumSet;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.api.IParticleReceptor;
import universalelectricity.prefab.tile.TileEntityElectrical;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public class SeriesReceptorTileEntity extends TileEntityElectrical implements IParticleReceptor, IPowerEmitter {

	
	public int excitedTicks=0;

	protected PowerHandler powerHandler;
	public float powerToDischarge=0;
	@Override
	public void onContact(BaseParticle particle) {
		if(this.excitedTicks>0){
			this.receiveElectricity(0.0003F*particle.potential,true);
		}else{
			this.receiveElectricity(0.0001F*particle.potential, true);
		}
		this.excitedTicks=20;
		particle.setDead();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
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

	
	
	
	
	
	
	
	
	
	
}
