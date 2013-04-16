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

public class HarmingLaserBlock extends LaserBlock {

	
	public HarmingLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Harming Beam");
		this.setBlockUnbreakable();
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public HarmingLaserBlock() {
		super(ComplexMachines.blockStartingID + 11);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("Harming Beam");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((HarmingLaserBeamTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();

	}
	
	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new LaserBeamTileEntity();

	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:HarmingLaser");
	}
}
