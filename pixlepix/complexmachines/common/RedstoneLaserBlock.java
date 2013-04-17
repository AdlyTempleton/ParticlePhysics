package pixlepix.complexmachines.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;

public class RedstoneLaserBlock extends LaserBlock{

	
	public RedstoneLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Redstone Beam");
		
	}
	@Override
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return 15;
    }
	@Override
	public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir) 
    { 
        return true; 
    }
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:RedstoneLaser");
	}
	
	
	
}
