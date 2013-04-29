package pixlepix.complexmachines.common.container;

import pixlepix.complexmachines.common.tileentity.GrinderTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;

public class ContainerGrinder extends Container {
	private GrinderTileEntity tileEntity;

	public ContainerGrinder(InventoryPlayer par1InventoryPlayer,
			GrinderTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		// this.addSlotToContainer(new Slot(tileEntity, 0, 55, 25)); // To be
		// drawn into wire

		int var3;

		for (var3 = 0; var3 < 3; ++var3) {
			for (int var4 = 0; var4 < 9; ++var4) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var4
						+ var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for (var3 = 0; var3 < 9; ++var3) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var3,
					8 + var3 * 18, 142));
		}

		for (var3 = 0; var3 < 6; ++var3) {
			this.addSlotToContainer(new Slot(tileEntity, var3, 8 + var3 * 18,
					16));
		}

		tileEntity.openChest();
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		this.tileEntity.closeChest();
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking.
	 */

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (par2 < 36)
            {
                if (!this.mergeItemStack(itemstack1, 36, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else 
            if (!this.mergeItemStack(itemstack1, 0, 36, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
	}

}