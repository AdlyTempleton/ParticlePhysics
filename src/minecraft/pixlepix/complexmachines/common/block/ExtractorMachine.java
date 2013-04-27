package pixlepix.complexmachines.common.block;

import pixlepix.complexmachines.client.ClientProxy;
import pixlepix.complexmachines.common.ComplexMachines;
import pixlepix.complexmachines.common.tileentity.ExtractorMachineTileEntity;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockAdvanced;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtractorMachine extends BlockAdvanced {
	private Icon connectorIcon;
	private Icon topIcon;

	public ExtractorMachine(int id) {
		super(id, UniversalElectricity.machine);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("extractor");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public ExtractorMachine() {
		super(ComplexMachines.blockStartingID + 2, UniversalElectricity.machine);
		this.setStepSound(soundMetalFootstep);
		this.setUnlocalizedName("extractor");
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	/**
	 * Called when the block is placed in the world.
	 */

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z,
			EntityLiving par5EntityLiving, ItemStack itemStack) {

		((ExtractorMachineTileEntity) par1World.getBlockTileEntity(x, y, z))
				.initiate();
		int angle = MathHelper.floor_double((par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int change = 0;

		switch (angle)
		{
			case 0:
				change = 1;
				break;
			case 1:
				change = 2;
				break;
			case 2:
				change = 0;
				break;
			case 3:
				change = 3;
				break;
		}
		par1World.setBlockMetadataWithNotify(x, y, z, change, 2);
		par1World.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
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
	public boolean onMachineActivated(World par1World, int x, int y, int z,
			EntityPlayer par5EntityPlayer, int side, float hitX, float hitY,
			float hitZ) {
		if (!par1World.isRemote) {
			par5EntityPlayer.openGui(ComplexMachines.instance, 2, par1World, x,
					y, z);
			return true;
		}

		return true;
	}
	@Override
    public boolean onUseWrench(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int side,
            float hitX, float hitY, float hitZ)
    {
        int metadata = par1World.getBlockMetadata(x, y, z);
        
        int change = 0;
        
        // Re-orient the block
        switch (metadata)
        {
            case 0:
                change = 3;
                break;
            case 3:
                change = 1;
                break;
            case 1:
                change = 2;
                break;
            case 2:
                change = 0;
                break;
        }
        
        par1World.setBlock(x, y, z, this.blockID, change, 0);
        par1World.markBlockForRenderUpdate(x, y, z);
        
        ((ExtractorMachineTileEntity) par1World.getBlockTileEntity(x, y, z)).initiate();
        
        return true;
    }

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World var1, int metadata) {
		return new ExtractorMachineTileEntity();

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

		blockIcon = par1IconRegister
				.registerIcon("ComplexMachines:ExtractorFront");
		connectorIcon = par1IconRegister
				.registerIcon("ComplexMachines:ExtractorInput");
		topIcon = par1IconRegister.registerIcon("ComplexMachines:ExtractorTop");
	}

	@Override
	public Icon getIcon(int side, int meta) {

		if (side == meta + 2) {
			return connectorIcon;
		} else {
			if (side == 1 || side == 0) {
				return topIcon;
			}
			return blockIcon;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

}