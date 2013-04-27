package pixlepix.complexmachines.common.laser.block;

import pixlepix.complexmachines.common.laser.tileentity.HarmingLaserBeamTileEntity;
import pixlepix.complexmachines.common.laser.tileentity.TripwireLaserBeamTileEntity;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TripwireLaserBlock extends LaserBlock {
	public TripwireLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Tripwire Laser");
		this.setBlockUnbreakable();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:GlassLaser");
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((TripwireLaserBeamTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();

	}
	
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new TripwireLaserBeamTileEntity();

	}

}
