package pixlepix.particlephysics.common.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.block.IElectrical;
import universalelectricity.core.block.IElectricalStorage;
import universalelectricity.prefab.network.IPacketReceiver;

import com.google.common.io.ByteArrayDataInput;

public abstract class PowerConsumerComplexTileEntity extends BasicComplexTileEntity implements IPacketReceiver,IElectrical, IElectricalStorage {

	public final double WATTS_PER_TICK = 5000;
	public final double TRANSFER_LIMIT = 12500;
	private double joulesStored = 0;
	public static double maxJoules = 2000000;
	public int ticks = 0;

	private int playersUsing = 0;
	public int orientation;
	
	@Override
	public float getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return getMaximumEnergy();
	}

	
	public int getMaximumEnergy(){
			return (int) this.getMaxJoules();
}
	@Override
	public void updateEntity() {
		super.updateEntity();
/*
		if (!this.worldObj.isRemote) {

			ForgeDirection inputDirection = ForgeDirection.getOrientation(this
					.getBlockMetadata() + 2);
			TileEntity inputTile = VectorHelper.getTileEntityFromSide(
					this.worldObj, new Vector3(this), inputDirection);

			IElectricityNetwork inputNetwork = ElectricityNetworkHelper
					.getNetworkFromTileEntity(inputTile,
							inputDirection.getOpposite());
			if (inputNetwork != null) {
				if (this.joulesStored < getMaxJoules()) {
					inputNetwork.startRequesting(this,Math.min(this.getMaxJoules() - this.getJoules(),this.TRANSFER_LIMIT) / this.getVoltage(),this.getVoltage());
					ElectricityPack electricityPack = inputNetwork.consumeElectricity(this);
					this.setJoules(this.joulesStored+ electricityPack.getWatts());

					if (UniversalElectricity.isVoltageSensitive) {
						if (electricityPack.voltage > this.getVoltage()) {
							this.worldObj.createExplosion(null, this.xCoord,
									this.yCoord, this.zCoord, 2f, true);
						}
					}
				} else {
					inputNetwork.stopRequesting(this);
				}
				

			}
		}

		

		this.joulesStored = Math.min(this.joulesStored, this.getMaxJoules());
		this.joulesStored = Math.max(this.joulesStored, 0d);
	*/}

	@Override
	public void handlePacketData(INetworkManager inputNetwork, int type,
			Packet250CustomPayload packet, EntityPlayer player,
			ByteArrayDataInput dataStream) {
		try {
			this.joulesStored = dataStream.readDouble();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public float getVoltage() {
		return 480;
	}

	/**
	 * @return The amount of ticks required to draw this item
	 */

	

	public double getJoules() {
		return this.getEnergyStored();
	}
 
	public void setJoules(double joules) {
		this.setEnergyStored((float)joules);
	}

	public abstract double getMaxJoules();

	@Override
	public boolean canConnect(ForgeDirection direction) {
		return direction.ordinal() == this.getBlockMetadata() + 2;
	}
	

	/*@Override
	public double getMaxEnergy() {
		// TODO Auto-generated method stub
		return this.getMaxJoules();
	}

	@Override
	public double transferEnergyToAcceptor(double amount) {
		double energyTransfered=Math.max(getMaxEnergy()-this.getEnergy(),amount );
		this.setEnergy(this.getEnergy()+energyTransfered);
		return amount-energyTransfered;
	}

	
	@Override
	public boolean canReceiveEnergy(ForgeDirection side) {
		// TODO Auto-generated method stub
		return this.canConnect(side);
	}
*/
	@Override
	public float getRequest(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 10000;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
