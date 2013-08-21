package pixlepix.particlephysics.common.tile;

import java.util.EnumSet;

import ic2.api.energy.tile.IEnergyTile;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import pixlepix.particlephysics.common.entity.BaseParticle;
import pixlepix.particlephysics.common.helper.IParticleReceptor;
import pixlepix.particlephysics.common.helper.PowerProducerComplexTileEntity;
import universalelectricity.core.block.IConnector;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.block.IElectricalStorage;
import universalelectricity.prefab.tile.TileEntityElectrical;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class SeriesReceptorTileEntity extends TileEntityElectrical implements IParticleReceptor {

	
	public int excitedTicks=0;
	public float powerToDischarge=0;
	@Override
	public void onContact(BaseParticle particle) {
		System.out.println("Collide");
		if(this.excitedTicks>0){
			this.receiveElectricity(0.0015F*particle.potential,true);
		}else{
			this.receiveElectricity(0.0005F*particle.potential, true);
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

}
