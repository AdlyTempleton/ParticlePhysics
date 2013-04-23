package pixlepix.complexmachines.common.item;


import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import pixlepix.complexmachines.common.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import universalelectricity.core.item.ItemElectric;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class FellerItem extends ItemElectric
{
    public FellerItem(int par1)
    {
        super(par1);
        this.setUnlocalizedName("Feller");
        maxStackSize=1;
        this.setCreativeTab(ComplexMachines.creativeTab);
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	
    	if(!world.isRemote){
    		int targetId=world.getBlockId(x, y, z);
    		if(targetId==17||targetId==18){
    			ArrayList<CoordTuple> list=new ArrayList<CoordTuple>();
    			list.add(new CoordTuple(x,y,z));
    			while(list.size()>0){
    				CoordTuple curr=list.get(0);
    				
    				ArrayList<CoordTuple> nearby=new ArrayList<CoordTuple>();
    				int curX=curr.x;
    				int curY=curr.y;
    				int curZ=curr.z;
    				list.remove(0);
    				world.destroyBlock(curX, curY, curZ, true);
    				nearby.add(new CoordTuple(curX+1,curY,curZ));
    				nearby.add(new CoordTuple(curX-1,curY,curZ));
    				nearby.add(new CoordTuple(curX,curY+1,curZ));
    				nearby.add(new CoordTuple(curX,curY-1,curZ));
    				nearby.add(new CoordTuple(curX,curY,curZ+1));
    				nearby.add(new CoordTuple(curX,curY,curZ-1));
    				for(int i=0;i<nearby.size();i++){
    					if(nearby.get(i).getBlock(world)==17||nearby.get(i).getBlock(world)==18){
    						list.add(nearby.get(i));
    					}
    				}
    			}
    		}
    		
    	}
    	
    	
    	
    	
		return false;
        
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
        this.itemIcon = par1IconRegister.registerIcon("ComplexMachines:Feller");
    }
}