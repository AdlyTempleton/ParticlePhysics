package pixlepix.complexmachines.common.laser.block;

import pixlepix.complexmachines.common.laser.tileentity.SuctionLaserBeamTileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SuctionLaserBlock extends LaserBlock {


	public SuctionLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Suction Beam");
		
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((SuctionLaserBeamTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();

	}
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new SuctionLaserBeamTileEntity();

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:SuctionLaser");
	}
	
}
