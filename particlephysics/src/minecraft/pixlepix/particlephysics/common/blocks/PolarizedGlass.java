package pixlepix.particlephysics.common.blocks;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.entity.BaseParticle;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;

public class PolarizedGlass extends BasicComplexBlock {

	
	
	public PolarizedGlass() {
		super(1179);
	}
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
		if (par7Entity instanceof BaseParticle){
			return;
		}
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
        {
            par6List.add(axisalignedbb1);
        }
    }

	public PolarizedGlass(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "PolarizedGlass";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "PolarizedGlass";
	}

	
	@Override
	public Class getTileEntityClass() {
		return null;
	}

	@Override
	public void addRecipe() {

		
	}

	@Override
	public String getName() {
		return "Polarized Glass";
	}

	@Override
	public boolean hasItemBlock() {
		return false;
	}

	@Override
	public Class getItemBlock() {
		return null;
		
	}
	@Override
	public boolean threeSidedTextures(){
		return false;
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	

}
