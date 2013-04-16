package pixlepix.complexmachines.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.core.UniversalElectricity;

public class DebuffLaserBlock extends LaserBlock {

	
	public DebuffLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Debuff Beam");
		this.setBlockUnbreakable();
	}

	public DebuffLaserBlock() {
		super(ComplexMachines.blockStartingID + 13);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("Debuff Beam");
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((DebuffLaserBeamTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();

	}
	
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new DebuffLaserBeamTileEntity();

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:DebuffLaser");
	}
}
