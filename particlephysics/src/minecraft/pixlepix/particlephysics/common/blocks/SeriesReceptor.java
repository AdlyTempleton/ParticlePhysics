package pixlepix.particlephysics.common.blocks;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.entity.BaseParticle;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;
import pixlepix.particlephysics.common.helper.IParticleReceptor;
import pixlepix.particlephysics.common.tile.EmitterTileEntity;
import pixlepix.particlephysics.common.tile.SeriesReceptorTileEntity;

public class SeriesReceptor extends BasicComplexBlock {

	
	
	public SeriesReceptor() {
		super(1180);
	}
	public SeriesReceptor(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "SeriesReceptor";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "SeriesReceptor";
	}

	
	@Override
	public Class getTileEntityClass() {
		return SeriesReceptorTileEntity.class;
	}

	@Override
	public void addRecipe() {

		
	}

	@Override
	public String getName() {
		return "Series Receptor";
	}

	@Override
	public boolean hasItemBlock() {
		return true;
	}

	@Override
	public Class getItemBlock() {
		return null;
		
	}
	@Override
	public boolean threeSidedTextures(){
		return false;
	}
	

	

}
