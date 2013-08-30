package pixlepix.particlephysics.common.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.api.BaseParticle;
import pixlepix.particlephysics.common.helper.ClientProxy;
import pixlepix.particlephysics.common.helper.CoordTuple;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public class SandParticle extends BaseParticle {

	public SandParticle(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<CoordTuple> previous=new ArrayList<CoordTuple>();
	@Override
	public float getStartingPotential() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public String getName(){
		return "Sand";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		
	}
	
	
	@Override
	public void onBounceHook(int x,int y, int z){
		CoordTuple newTuple=new CoordTuple(x,y,z);
		if(previous.contains(newTuple)){
			return;
		}
		previous.add(newTuple);
		SandParticle reflectedParticle=new SandParticle(this.worldObj);
		reflectedParticle.previous=this.previous;
		ForgeDirection dir=this.movementDirection.getOpposite();
		reflectedParticle.setVelocity(dir.offsetX,dir.offsetY,dir.offsetZ);
		reflectedParticle.setPosition(this.posX,this.posY,this.posZ);
		worldObj.spawnEntityInWorld(reflectedParticle);
	}

}
