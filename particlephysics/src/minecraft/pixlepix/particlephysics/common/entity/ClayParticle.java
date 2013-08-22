package pixlepix.particlephysics.common.entity;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public class ClayParticle extends BaseParticle {
	
	public int ticks=0;
	
	public int iSize=1;
	public int jSize=1;
	public int kSize=1;
	public int rotationX=0;
	public int rotationY=0;
	public int rotationZ=0;
	public float potential;
	public ForgeDirection movementDirection;
	
	public ClayParticle(World par1World) {
		super(par1World);
		
	}
	public float getStartingPotential(){
		return 1500;
		
	}
	@Override
	public BlockRenderInfo getRenderIcon(){
		BlockRenderInfo info=new BlockRenderInfo(ClientProxy.clay);
		return info;
	}
	
	@Override
	public void onCollideWithParticle(BaseParticle particle){
		if(particle instanceof CoalParticle){
			if(!worldObj.isRemote){
				System.out.println("Creating Split Particles");
				SplitParticle coalDerived=new SplitParticle(worldObj,particle);
				SplitParticle lapisDerived=new SplitParticle(worldObj,this);
				coalDerived.setPartner(lapisDerived);
				lapisDerived.setPartner(coalDerived);
				worldObj.spawnEntityInWorld(coalDerived);
				worldObj.spawnEntityInWorld(lapisDerived);
			}
			this.setVelocity(0, 0,0);
			particle.setVelocity(0,0,0);
			particle.setDead();
			this.setDead();
			
		}
	}
	
 

	
	
	
	

}
