package pixlepix.complexmachines.common.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import pixlepix.complexmachines.common.ComplexMachines;
import universalelectricity.core.UniversalElectricity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class IntermediateFlux extends Block {

	public IntermediateFlux(int id) {
		super(id, UniversalElectricity.machine);
		this.setCreativeTab(CreativeTabs.tabMisc);

		this.setUnlocalizedName("Intermediate Flux");
	}

	public IntermediateFlux() {
		super(ComplexMachines.blockStartingID + 18, UniversalElectricity.machine);
		this.setUnlocalizedName("Intermediate Flux");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		((IntermediateFluxTileEntity)par1World.getBlockTileEntity(x, y,z)).initiate();
		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
	}
	public TileEntity createTileEntity(World var1, int metadata) {
		// return new FocalPointControledTileEntity();
		return new IntermediateFluxTileEntity();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:Flux");
	}
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	 
	
	
}
