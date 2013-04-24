package pixlepix.complexmachines.common.item;


import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import pixlepix.complexmachines.common.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import universalelectricity.core.item.ItemElectric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class CubeFormerItem extends ItemElectric
{
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
    	if(getJoules(itemStack)>=1000000){
    		if(player.rayTrace(30, 1.0F)!=null){
    			int centerX=player.rayTrace(30, 1.0F).blockX;
    			int centerY=player.rayTrace(30, 1.0F).blockY;
    			int centerZ=player.rayTrace(30, 1.0F).blockZ;
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