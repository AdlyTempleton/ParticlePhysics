package pixlepix.complexmachines.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import pixlepix.complexmachines.common.ComplexMachines;
import pixlepix.complexmachines.common.tileentity.GrinderTileEntity;
import universalelectricity.core.UniversalElectricity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PolarizedGlass extends Block{

	private Icon connectorIcon;
	private Icon topIcon;
	private Icon activatedIcon;

	public PolarizedGlass(int id) {
		super(id, UniversalElectricity.machine);
		this.setUnlocalizedName("Polarized Glass");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	
	
	public PolarizedGlass() {
		super(ComplexMachines.blockStartingID + 7, UniversalElectricity.machine);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("Polarized Glass");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		
		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
	}
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderBlockPass(){
		return 1;
		
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister
				.registerIcon("ComplexMachines:UnpolarizedGlass");
		activatedIcon = par1IconRegister.registerIcon("ComplexMachines:PolarizedGlass");
	}
	
	
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		return blockIcon;
	}

}
