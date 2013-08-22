package pixlepix.particlephysics.common.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.entity.BaseParticle;
import pixlepix.particlephysics.common.entity.CoalParticle;
import pixlepix.particlephysics.common.entity.ClayParticle;
import pixlepix.particlephysics.common.helper.BasicComplexTileEntity;

public class EmitterTileEntity extends BasicComplexTileEntity implements IInventory {

	
	public ItemStack inventory;
	public static int[] validFuel={Item.coal.itemID,Item.clay.itemID};
	@Override
	public float getRequest(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getProvide(ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getMaxEnergyStored() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Item fuelType;

	public int fuelMeta;
	public int fuelStored=0;
	public void updateEntity(){
		if(!worldObj.isRemote&&worldObj.getTotalWorldTime()%40==0){
			if(fuelStored<1){
				if(this.inventory!=null){
					if(isValidFuel(this.inventory)){
						this.fuelStored=100;
						this.fuelType=this.inventory.getItem();
						this.fuelMeta=this.fuelType.getDamage(this.inventory);
						this.decrStackSize(0, 1);
					}
				}
			}
			if(fuelStored>0){
				this.fuelStored--;
				
				ForgeDirection[] outputDirections={ForgeDirection.SOUTH,ForgeDirection.NORTH,ForgeDirection.WEST,ForgeDirection.EAST};
				for(ForgeDirection dir:outputDirections){
					
					BaseParticle particle=getParticleFromFuel(fuelType);
					if(particle==null){
						return;
					}
					particle.addVelocity(dir.offsetX, dir.offsetY, dir.offsetZ);
					particle.setPosition(xCoord+dir.offsetX+0.375,yCoord+dir.offsetY+0.375,zCoord+dir.offsetZ+0.375);
					worldObj.spawnEntityInWorld(particle);
					
				}
			}
		}
	}
	public BaseParticle getParticleFromFuel(Item fuel){
		if(fuel==Item.coal){
			return new CoalParticle(worldObj);
		}
		if(fuel==Item.clay){
			return new ClayParticle(worldObj);
		}
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		this.fuelStored=nbt.getInteger("Fuel");
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("Fuel", this.fuelStored);
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		// TODO Auto-generated method stub
		return this.inventory;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		// TODO Auto-generated method stub
		return this.inventory.splitStack(j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		this.inventory=itemStack;
		
	}

	@Override
	public String getInvName() {
		// TODO Auto-generated method stub
		return "Emitter";
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
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		
		return isValidFuel(itemstack);
	}

	public static boolean isValidFuel(ItemStack itemstack){
		for(int fuel : validFuel){
			if (itemstack.itemID==fuel){
				return true;
			}
		}	
		return false;
	}

}