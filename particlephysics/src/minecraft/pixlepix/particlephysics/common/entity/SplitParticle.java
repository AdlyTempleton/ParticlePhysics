package pixlepix.particlephysics.common.entity;

import pixlepix.particlephysics.common.render.BlockRenderInfo;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class SplitParticle extends BaseParticle{
	boolean derivedFromCoal;
	SplitParticle partner;
	long birthTime;
	
	public SplitParticle(World par1World) {
		super(par1World);
	}
	public SplitParticle(World par1World, BaseParticle particle) {
		super(par1World);
		this.setPosition(particle.posX, particle.posY, particle.posZ);
		this.setVelocity(particle.motionX, particle.motionY, particle.motionZ);
		this.potential=particle.potential;
		this.derivedFromCoal=particle instanceof CoalParticle; 
		this.birthTime=worldObj.getTotalWorldTime();
	}
	public void setPartner(SplitParticle particle){
		this.partner=particle;
	}
	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return this.derivedFromCoal?8000:4000;
	}

	@Override
	public BlockRenderInfo getRenderIcon() {
		// TODO Auto-generated method stub
		return new BlockRenderInfo(Block.bedrock);
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		
		if(particle instanceof SplitParticle&&particle==this.partner){
			if(this.derivedFromCoal&&this.birthTime-this.worldObj.getTotalWorldTime()>20){
				particle.setDead();
				this.setDead();
				ConcentratedParticle produce=new ConcentratedParticle(this.worldObj);
				produce.setVelocity(this.movementDirection.offsetX, this.movementDirection.offsetY,this.movementDirection.offsetZ);
				worldObj.spawnEntityInWorld(produce);
			}
		}
		
	}
}
