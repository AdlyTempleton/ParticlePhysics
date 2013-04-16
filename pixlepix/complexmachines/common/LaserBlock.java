package pixlepix.complexmachines.common;

import java.util.Random;

import pixlepix.complexmachines.client.ClientProxy;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockAdvanced;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LaserBlock extends Block {
	private Icon connectorIcon;

	public LaserBlock(int id) {
		super(id, UniversalElectricity.machine);
		this.setUnlocalizedName("Beam");
		this.setBlockUnbreakable();
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public LaserBlock() {
		super(ComplexMachines.blockStartingID + 9, UniversalElectricity.machine);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("Beam");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
		((LaserBeamTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();

	}

	/*
	 * @Override public boolean onMachineActivated(World par1World, int x, int
	 * y, int z, EntityPlayer par5EntityPlayer, int side, float hitX, float
	 * hitY, float hitZ) { if (!par1World.isRemote) {
	 * par5EntityPlayer.openGui(ElectricExpansion.instance, 2, par1World, x, y,
	 * z); return true; }
	 * 
	 * return true; }
	 */
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderBlockPass() {
		return 1;

	}

	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new LaserBeamTileEntity();

	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	// Imported code from EE, unsure if it is needed
	/*
	 * @SideOnly(Side.CLIENT)
	 * 
	 * @Override public int getRenderType() { return ClientProxy.RENDER_ID; }
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:LaserBeam");
	}

}