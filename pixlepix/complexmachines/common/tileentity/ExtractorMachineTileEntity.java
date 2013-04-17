package pixlepix.complexmachines.common.tileentity;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.common.MinecraftForge;
import universalelectricity.core.UniversalElectricity;
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

public class ExtractorMachineTileEntity extends TileEntityElectricityRunnable
		implements IPacketReceiver, IElectricityStorage, IInventory {
	public final double WATTS_PER_TICK = 5000;
	public final double TRANSFER_LIMIT = 125000;
	private int drawingTicks = 0;
	private double joulesStored = 0;
	public static double maxJoules = 1000000;
	public final int COST_ON_ORE = 1000000;
	public final int COST_ON_MISS = 15000;
	public int ticks;
	/**
	 * The ItemStacks that hold the items currently being used in the wire mill;
	 * 0 = battery; 1 = input; 2 = output;
	 */
	private ItemStack[] inventory = new ItemStack[24];
	Random rand = new Random();
	private int playersUsing = 0;
	public int orientation;
	private int targetID = 0;
	private int targetMeta = 0;
	public final int[] ORE_LIST = { 14, 15, 16, 21, 56, 73, 129, 458, 688,
			3002, 3880, 3970, 3989 };
	private boolean initialized;

	public int isOre(int id) {

		if (id == 0) {
			return -1;
		}

		int[] targets = new int[9];
		targets[0] = Block.oreCoal.blockID;

		targets[1] = Block.oreRedstone.blockID;

		targets[2] = Block.oreDiamond.blockID;

		for (int i = 3; i < 9; i++) {
			if (inventory[i - 3] != null) {
				targets[i] = inventory[i - 3].itemID;
			}
		}
		for (int i = 0; i < 9; i++) {
			if (targets[i] == id) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void initiate() {
		this.initialized = true;
	}

	@Override
	public void updateEntity() {
		// System.out.println("Recieving ticks");
		super.updateEntity();

		if (!this.worldObj.isRemote) {
			ForgeDirection inputDirection = ForgeDirection.getOrientation(this
					.getBlockMetadata() + 2);
			TileEntity inputTile = VectorHelper.getTileEntityFromSide(
					this.worldObj, new Vector3(this), inputDirection);

			IElectricityNetwork inputNetwork = ElectricityNetworkHelper
					.getNetworkFromTileEntity(inputTile,
							inputDirection.getOpposite());

			if (inputNetwork != null) {
				if (this.joulesStored < ExtractorMachineTileEntity.maxJoules) {

					inputNetwork.startRequesting(
							this,
							Math.min(this.getMaxJoules() - this.getJoules(),
									this.TRANSFER_LIMIT) / this.getVoltage(),
							this.getVoltage());
					ElectricityPack electricityPack = inputNetwork
							.consumeElectricity(this);
					this.setJoules(this.joulesStored
							+ electricityPack.getWatts());

					if (UniversalElectricity.isVoltageSensitive) {
						if (electricityPack.voltage > this.getVoltage()) {
							this.worldObj.createExplosion(null, this.xCoord,
									this.yCoord, this.zCoord, 2f, true);
						}
					}
				} else {
					boolean oreFound = false;
					while (!oreFound && getJoules() > 1000) {

						// System.out.println("Scanning");
						ticks = 0;

						int targetX = xCoord - 100;

						int targetZ = zCoord - 100;

						targetX += (rand.nextGaussian() * 200);
						targetZ += (rand.nextGaussian() * 200);

						int targetY = rand.nextInt(15) + 5;

						//
						// Standard generator
						//
						/*
						 * targetX-=rand.nextInt(2000);
						 * 
						 * targetY-=rand.nextInt(100);
						 * 
						 * targetZ-=rand.nextInt(2000);
						 */
						int targetId = worldObj.getBlockId(targetX, targetY,
								targetZ);
						// System.out.println("X: "+targetX+"  Y:"+targetY+"   Z:"+targetZ+"   id:"+worldObj.getBlockId(targetX,targetY,targetZ));
						int matchedSlot = isOre(targetId);
						// System.out.println(targetId);
						if (worldObj.getChunkFromBlockCoords(targetX, targetZ).isChunkLoaded
								&& matchedSlot != -1) {
							oreFound = true;
							setJoules(getJoules() - COST_ON_ORE);

							ItemStack drop = Block.blocksList[targetId]
									.getBlockDropped(
											worldObj,
											targetX,
											targetY,
											targetZ,
											worldObj.getBlockMetadata(targetX,
													targetY, targetZ), 0)
									.get(0);
							worldObj.setBlock(targetY, targetY, targetZ, 0);
							dropItems(drop, matchedSlot);
							// System.out.println(drops);

						}
						;

					}
					// inputNetwork.stopRequesting(this);
				}
			}

		}

		if (!this.worldObj.isRemote) {
			if (this.ticks % 3 == 0 && this.playersUsing > 0) {
				// PacketManager.sendPacketToClients(this.getDescriptionPacket(),
				// this.worldObj, new Vector3(this), 12);
			}
		}

		this.joulesStored = Math.min(this.joulesStored, this.getMaxJoules());
		this.joulesStored = Math.max(this.joulesStored, 0d);
	}

	private void dropItems(ItemStack drop, int matchedSlot) {
		// Find nearby chests

		// System.out.println(drop);

		int posX = xCoord;
		int posY = yCoord;
		int posZ = zCoord;

		TileEntityChest chestFound = findChest();
		System.out.println(chestFound);

		int shiftedMatchedSlot = matchedSlot - 3;
		int firstSlot = shiftedMatchedSlot + 9;
		int secondSlot = shiftedMatchedSlot + 18;
		if (inventory[firstSlot] != null) {
			if (inventory[firstSlot].stackSize + drop.stackSize <= 64) {
				inventory[firstSlot].stackSize += drop.stackSize;
				return;
			}
		} else {
			inventory[firstSlot] = drop;
			return;
		}

		if (inventory[secondSlot] != null) {

			if (inventory[secondSlot].stackSize + drop.stackSize <= 64) {
				inventory[secondSlot].stackSize += drop.stackSize;
				return;
			}

		} else {
			inventory[firstSlot] = drop;
			return;
		}

		/*
		 * if(chestFound != null){
		 * 
		 * for(int i=0;i<chestFound.getSizeInventory();i++){
		 * if(chestFound.isStackValidForSlot(i,drop)){
		 * if(chestFound.getStackInSlot(i)!=null){ int
		 * itemsToAdd=drop.stackSize; int
		 * currentItems=chestFound.getStackInSlot(i).stackSize; int
		 * newTotal=itemsToAdd+currentItems;
		 * chestFound.setInventorySlotContents(i, new
		 * ItemStack(chestFound.getStackInSlot(i).getItem(),newTotal)); }else{
		 * 
		 * chestFound.setInventorySlotContents(i, drop);
		 * 
		 * } break; } }
		 * 
		 * 
		 * } else{
		 * 
		 * 
		 * }
		 */

	}

	private TileEntityChest findChest() {

		TileEntity entityBeingChecked = worldObj.getBlockTileEntity(xCoord,
				yCoord, zCoord);
		if (worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord + 1,
					yCoord, zCoord);
		}
		if (worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord - 1,
					yCoord, zCoord);
		}
		if (worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord,
					yCoord + 1, zCoord);
		}
		if (worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord,
					yCoord - 1, zCoord);
		}
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord,
					yCoord, zCoord + 1);
		}
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1) instanceof TileEntityChest) {
			return (TileEntityChest) worldObj.getBlockTileEntity(xCoord,
					yCoord, zCoord - 1);
		}
		return null;
	}

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
	public double getVoltage() {
		return 480;
	}

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
		return ExtractorMachineTileEntity.maxJoules;
	}

	@Override
	public boolean canConnect(ForgeDirection direction) {

		return direction.ordinal() == this.getBlockMetadata() + 2;
	}

	@Override
	public int getSizeInventory() {

		return 24;
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
		return "Extractor";
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

		this.playersUsing--;

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
