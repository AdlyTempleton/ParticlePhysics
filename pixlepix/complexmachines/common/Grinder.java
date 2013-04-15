package pixlepix.complexmachines.common;

import pixlepix.complexmachines.client.ClientProxy;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockAdvanced;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Grinder extends BlockAdvanced
{
    private Icon connectorIcon;

	public Grinder(int id)
    {
        super(id, UniversalElectricity.machine);
        this.setUnlocalizedName("Grinder");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    public Grinder()
    {
        super(ComplexMachines.blockStartingID+1, UniversalElectricity.machine);
        this.setStepSound(soundMetalFootstep);
        this.setUnlocalizedName("Grinder");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
        
    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLiving par5EntityLiving, ItemStack itemStack)
    {	
        
        
        ((GrinderTileEntity) par1World.getBlockTileEntity(x, y, z)).initiate();
        par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
    }
    
    
   
    @Override
    public boolean onMachineActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side,
            float hitX, float hitY, float hitZ)
    {
        if (!par1World.isRemote)
        {
            par5EntityPlayer.openGui(ComplexMachines.instance, 2, par1World, x, y, z);
            return true;
        }
        
        return true;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public TileEntity createTileEntity(World var1, int metadata)
    {
        return new GrinderTileEntity();
        
    }
    
    
    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }
    
    
    
    
    //Imported code from EE, unsure if it is needed
    /*
    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderType()
    {
        return ClientProxy.RENDER_ID;
    }
    */
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {

        blockIcon = par1IconRegister.registerIcon("ComplexMachines:GrinderMachine"); 
        connectorIcon = par1IconRegister.registerIcon("ComplexMachines:InputSide"); 
    }
    
    @Override
	public Icon getIcon (int side, int meta)
	{
		if (side==meta+2){
			return connectorIcon;
		}else{
			return blockIcon;
		}
	}
    
    
    
}