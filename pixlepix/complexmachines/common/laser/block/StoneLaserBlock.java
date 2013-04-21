package pixlepix.complexmachines.common.laser.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class StoneLaserBlock extends LaserBlock {

	public StoneLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Bridge Beam");
		this.setLightValue(1.0F);
		
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderBlockPass() {
		return 0;

		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:StoneBrick");
	}
	
	
}
