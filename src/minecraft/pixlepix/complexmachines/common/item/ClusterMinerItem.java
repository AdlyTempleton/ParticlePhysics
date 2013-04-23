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
public class ClusterMinerItem extends ItemElectric
{
    public ClusterMinerItem(int par1)
    {
        super(par1);
        this.setUnlocalizedName("Cluster Miner");
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
			
			list.add(EnumColor.AQUA + "Mines a whole ore deposit at once");
			list.add(EnumColor.DARK_GREEN + "100KJ per ore mined");
		}
	}
    
    public boolean isOre(int id){
    	int[] ores={14,15,16,21,56,73,129,458,688,3002,3880,3970,3989,247,254,2001,2052,3093};
    	
    	for(int i=0;i<ores.length;i++){
    		if(ores[i]==id){
    			return true;
    		}
    	}
    	return false;
    }
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    		
    		int targetId=world.getBlockId(x, y, z);
    		if(isOre(targetId)){
    			ArrayList<CoordTuple> list=new ArrayList<CoordTuple>();
    			list.add(new CoordTuple(x,y,z));
    			while(list.size()>0&&getJoules(itemStack)>100000){
    				CoordTuple curr=list.get(0);
    				setJoules(this.getJoules(itemStack)-100000, itemStack);
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
    					if(isOre(nearby.get(i).getBlock(world))){
    						list.add(nearby.get(i));
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
        this.itemIcon = par1IconRegister.registerIcon("ComplexMachines:ClusterMiner");
    }
}