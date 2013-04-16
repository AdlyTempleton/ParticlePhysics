package pixlepix.complexmachines.common;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.common.MinecraftForge;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.block.IConductor;
import universalelectricity.core.block.IElectricityStorage;
import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.electricity.IElectricityNetwork;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.tile.TileEntityElectricityRunnable;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.Loader;

public class GrinderTileEntity extends TileEntityElectricityRunnable implements
		IPacketReceiver, IElectricityStorage, IInventory {
	public final double WATTS_PER_TICK = 5000;
	public final double TRANSFER_LIMIT = 12500;
	private int drawingTicks = 0;
	private double joulesStored = 0;
	public static double maxJoules = 200000;
	public int ticks = 10000;
	public int ticksOfPowerRemaining = 0;
	public int watts = 0;

	public static HashMap<Item, GrinderFuelData> values = new HashMap<Item, GrinderFuelData>();

	private IConductor connectedElectricUnit;
	/**
	 * The ItemStacks that hold the items currently being used in the wire mill;
	 * 0 = battery; 1 = input; 2 = output;
	 */
	private ItemStack[] inventory = new ItemStack[6];

	private int playersUsing = 0;
	public int orientation;
	private int targetID = 0;
	private int targetMeta = 0;

	private boolean initialized;

	public void initiateMappings() {
		values.put(Item.appleRed, new GrinderFuelData(20000, 1000));

		values.put(Item.coal, new GrinderFuelData(20, 200000));

		values.put(Item.ingotIron, new GrinderFuelData(1200, 50000));

		values.put(Item.diamond, new GrinderFuelData(72000, 10000));

		values.put(Item.ingotGold, new GrinderFuelData(720000, 10000));

	}

	@Override
	public void initiate() {
		this.initialized = true;
	}

	public void producePower() {

		ForgeDirection outputDirection = ForgeDirection.getOrientation(this
				.getBlockMetadata() + 2);
		TileEntity outputTile = VectorHelper.getConnectorFromSide(
				this.worldObj, new Vector3(this.xCoord, this.yCoord,
						this.zCoord), outputDirection);

		IElectricityNetwork network = ElectricityNetworkHelper
				.getNetworkFromTileEntity(outputTile, outputDirection);

		if (network != null) {
			if (network.getRequest().getWatts() > 0) {
				this.connectedElectricUnit = (IConductor) outputTile;
			} else {
				this.connectedElectricUnit = null;
			}
		} else {
			this.connectedElectricUnit = null;
		}

		if (!this.isDisabled()) {

			if (this.connectedElectricUnit != null) {

				this.connectedElectricUnit.getNetwork().startProducing(this,
						(10000 / this.getVoltage()) / 20, this.getVoltage());
				if (ticksOfPowerRemaining <= 0) {
					this.connectedElectricUnit.getNetwork().stopProducing(this);
				}

			}
		}

	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		// System.out.println(ticksOfPowerRemaining);
		if (!this.worldObj.isRemote) {

			if (ticksOfPowerRemaining != 0) {
				ticksOfPowerRemaining--;
				producePower();
			} else {

				for (int i = 0; i < inventory.length; i++) {
					ItemStack toGrind = inventory[i];
					if (inventory[i] != null) {
						if (toGrind.stackSize == 1) {
							inventory[i] = null;
						} else {
							inventory[i].stackSize--;
						}
						ticksOfPowerRemaining = 5;

					} else {

					}
				}
			}

		}

		if (!this.worldObj.isRemote) {
			if (this.ticks % 3 == 0 && this.playersUsing > 0) {
				PacketManager.sendPacketToClients(this.getDescriptionPacket(),
						this.worldObj, new Vector3(this), 12);
			}
		}

		this.joulesStored = Math.min(this.joulesStored, this.getMaxJoules());
		this.joulesStored = Math.max(this.joulesStored, 0d);
	}

	@Override
	public void handlePacketData(INetworkManager inputNetwork, int type,
			Packet250CustomPayload packet, EntityPlayer player,
			ByteArrayDataInput dataStream) {
		try {
			this.drawingTicks = dataStream.readInt();
			this.disabledTicks = dataStream.readInt();
			this.joulesStored = dataStream.readDouble();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads a tile entity from NBT.
	 */

	/**
	 * Writes a tile entity to NBT.
	 */

	@Override
	public double getVoltage() {
		return 120;
	}

	/**
	 * @return The amount of ticks required to draw this item
	 */

	public int getDrawingTimeLeft() {
		return this.drawingTicks;
	}

	@Override
	public double getJoules() {
		return this.joulesStored;
	}

	@Override
	public void setJoules(double joules) {
		this.joulesStored = joules;
	}

	@Override
	public double getMaxJoules() {
		return GrinderTileEntity.maxJoules;
	}

	@Override
	public boolean canConnect(ForgeDirection direction) {
		return direction.ordinal() == this.getBlockMetadata() + 2;
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {

		if (this.inventory[par1] != null) {
			ItemStack var3;

			if (this.inventory[par1].stackSize <= par2) {
				var3 = this.inventory[par1];
				this.inventory[par1] = null;
				return var3;
			} else {
				var3 = this.inventory[par1].splitStack(par2);

				if (this.inventory[par1].stackSize == 0) {
					this.inventory[par1] = null;
				}

				return var3;
			}
		} else
			return null;

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.inventory[par1] != null) {
			ItemStack var2 = this.inventory[par1];
			this.inventory[par1] = null;
			return var2;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		if (par1 < inventory.length) {
			this.inventory[par1] = par2ItemStack;

			if (par2ItemStack != null
					&& par2ItemStack.stackSize > this.getInventoryStackLimit()) {
				par2ItemStack.stackSize = this.getInventoryStackLimit();
			}
		}
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "Grinder";
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {

		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord,
				this.zCoord) != this ? false
				: par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D,
						this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openChest() {
		this.playersUsing++;

	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.inventory = new ItemStack[this.getSizeInventory()];
		try {
			this.joulesStored = par1NBTTagCompound.getDouble("joulesStored");
		} catch (Exception e) {
		}

		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setDouble("joulesStored", this.getJoules());

		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.inventory.length; ++var3) {
			if (this.inventory[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
	}

}