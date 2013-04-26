package pixlepix.complexmachines.common.item;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import pixlepix.complexmachines.common.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import universalelectricity.core.item.ItemElectric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class CubeFormerItem extends ItemElectric
{
	
	
	//Code from forge essentials
	public static MovingObjectPosition getPlayerLookingSpot(EntityPlayer player, boolean restrict)
	{
		float var4 = 1.0F;
		float var5 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * var4;
		float var6 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * var4;
		double var7 = player.prevPosX + (player.posX - player.prevPosX) * var4;
		double var9 = player.prevPosY + (player.posY - player.prevPosY) * var4 + 1.62D - player.yOffset;
		double var11 = player.prevPosZ + (player.posZ - player.prevPosZ) * var4;
		Vec3 var13 = player.worldObj.getWorldVec3Pool().getVecFromPool(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - (float) Math.PI);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - (float) Math.PI);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 50D;
		if (player instanceof EntityPlayerMP && restrict)
		{
			var21 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
		}
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
		return player.worldObj.rayTraceBlocks_do_do(var13, var23, false, !true);
	}
	
    public CubeFormerItem(int par1)
    {
        super(par1);
        this.setUnlocalizedName("Cube former");
        maxStackSize=1;
        this.setCreativeTab(ComplexMachines.creativeTab);
    }
    @Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4){
    	
    	super.addInformation(par1ItemStack,par2EntityPlayer, list, par4);
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			list.add("Hold " + EnumColor.AQUA + "shift" + EnumColor.GREY + " for more details.");
		}
		else {
			
			list.add(EnumColor.AQUA + "Creates a Cube of the material you point at");
			list.add(EnumColor.DARK_GREEN + "1MJ per use");
		}
	}
    
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player){
    	if(!world.isRemote){
    	if(getJoules(itemStack)>=1000000){
    		MovingObjectPosition position=getPlayerLookingSpot(player,false);
    		if(position!=null){
    			int centerX=position.blockX;
    			int centerY=position.blockY;
    			int centerZ=position.blockZ;
    			int materialId=world.getBlockId(centerX, centerY, centerZ);
    			
    			int required=countAir(centerX,centerY,centerZ, world);
    			int found=0;
    			
    			for(int i=0;i<player.inventory.getSizeInventory();i++){
    				if(player.inventory.getStackInSlot(i)!=null&&player.inventory.getStackInSlot(i).itemID==materialId){
    					found+=player.inventory.getStackInSlot(i).stackSize;
    					
    				}
    			}
    			if(found>=required){
    				found=0;
    				for(int i=0;i<required;i++){
        				player.inventory.consumeInventoryItem(materialId);
        			}
					setJoules(getJoules(itemStack)-1000000,itemStack);
					
					
					
					for(int i=-2;i<2;i++){
						for(int j=0;j<4;j++){
							for(int k=-2;k<2;k++){
								if(world.getBlockId(centerX+i, centerY+j, centerZ+k)==0){
									world.setBlock(centerX+i, centerY+j, centerZ+k, materialId);
								}

							}
						}
					}
					
					
					
    				
    			}else{
    				player.sendChatToPlayer(EnumColor.AQUA+"You don't have the materials");
    				return itemStack;
    			}
    			
    		}
    	}	
    		}
    		return itemStack;
    }
    
    	
    public int countAir(int centerX,int centerY,int centerZ, World world){
		int air=0;
		for(int i=-2;i<2;i++){
			for(int j=0;j<4;j++){
				for(int k=-2;k<2;k++){
					if(world.getBlockId(centerX+i, centerY+j, centerZ+k)==0){
						air++;
					}

				}
			}
		}
		return air;
    	
    }	
    	

    
    @Override
    public double getMaxJoules(ItemStack itemStack)
    {
        return 20000000;
    }
    
    @Override
    public double getVoltage(ItemStack itemStack)
    {
        return 240;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("ComplexMachines:CubeFormer");
    }
}