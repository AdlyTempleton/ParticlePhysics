package pixlepix.complexmachines.common;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MiningLaserBlock extends LaserBlock {

	

	public MiningLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Mining Beam");
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:MiningLaser");
	}
	
}
