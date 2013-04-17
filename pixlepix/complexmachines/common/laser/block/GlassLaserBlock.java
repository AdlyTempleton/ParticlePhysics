package pixlepix.complexmachines.common.laser.block;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GlassLaserBlock extends HarmingLaserBlock {
	public GlassLaserBlock(int id) {
		super(id);
		this.setUnlocalizedName("Glass");
		this.setBlockUnbreakable();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {

		blockIcon = par1IconRegister.registerIcon("ComplexMachines:GlassLaser");
	}

}
