package pixlepix.particlephysics.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import pixlepix.particlephysics.common.helper.IParticleReceptor;
import pixlepix.particlephysics.common.render.BlockRenderInfo;

public abstract class BaseParticle extends EntityLiving {
	
	public int ticks=0;
	
	public int iSize=1;
	public int jSize=1;
	public int kSize=1;
	public int rotationX=0;
	public int rotationY=0;
	public int rotationZ=0;
	public float potential;
	public ForgeDirection movementDirection;
	
	public BaseParticle(World par1World) {
		super(par1World);
		this.setSize(0.25F, 0.25F);
		this.potential=getStartingPotential();
		
		
	}
	public abstract float getStartingPotential();
	
	public abstract BlockRenderInfo getRenderIcon();
	public void bounce(int targetX, int targetY, int targetZ, ForgeDirection forward){
		int x=MathHelper.floor_double(posX);
		int y=MathHelper.floor_double(posY);
		int z=MathHelper.floor_double(posZ);
		ArrayList<ForgeDirection> freeDirections=new ArrayList<ForgeDirection>();
		for (ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
			if(dir.getOpposite()!=forward&&worldObj.getBlockId(x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ)==0){
				freeDirections.add(dir);
			}
		}
		if(freeDirections.size()!=0){
			ForgeDirection targetDirection=freeDirections.get((new Random().nextInt(freeDirections.size())));
			this.setPosition(x+0.5,y+0.5,z+0.5);
			this.potential*=0.9;
			double f=(this.potential/getStartingPotential());
			this.setVelocity(targetDirection.offsetX*f,targetDirection.offsetY*f,targetDirection.offsetZ*f);
			this.hookOnBounce();
		}else{
			this.setDead();
		}
	}
	
	public void hookOnBounce() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEntityUpdate(){
		ticks++;
		super.onEntityUpdate();
		if(ticks>600){
			this.setDead();
		}
		if(worldObj.isRemote){
			return;
		}
		ForgeDirection dir=this.getForward();
        if(dir!=ForgeDirection.UNKNOWN){
        	this.movementDirection=dir;
        }
		if(!worldObj.isRemote&&Math.abs(motionX)<0.3&&Math.abs(motionY)<0.3&&Math.abs(motionZ)<0.3){
			ForgeDirection forward=movementDirection;
			if(forward==null){
				return;

			}
			int targetX=MathHelper.floor_double(posX+(0.5*forward.offsetX));

			int targetY=MathHelper.floor_double(posY+(0.5*forward.offsetY));

			int targetZ=MathHelper.floor_double(posZ+(0.5*forward.offsetZ));
			int id=worldObj.getBlockId(targetX,targetY,targetZ);
			
			if(id==Block.glass.blockID){
				this.bounce(targetX,targetY,targetZ,forward);
			}else{
				//Polarized glass
				//Magic numbers are fun!
				TileEntity entity=worldObj.getBlockTileEntity(targetX, targetY, targetZ);
				if(entity instanceof IParticleReceptor){
					((IParticleReceptor)entity).onContact(this);
				}else{
					this.setDead();
				}
			}
		}
		this.checkForParticleCollision();
	}
	public void checkForParticleCollision() {
		
		List<BaseParticle> nearbyParticles=this.worldObj.getEntitiesWithinAABB(BaseParticle.class, AxisAlignedBB.getBoundingBox(posX-0.6, posY-0.6, posZ-0.6, posX+0.6, posY+0.6, posZ+0.6));
		
		for(int i=0;i<nearbyParticles.size();i++){
			BaseParticle particle=nearbyParticles.get(i);
			if(this!=particle){
				this.onCollideWithParticle(particle);
			}
		}
	}
		
	
	public abstract void onCollideWithParticle(BaseParticle particle);
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		nbt.setInteger("direction", movementDirection.ordinal());
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		this.movementDirection=ForgeDirection.VALID_DIRECTIONS[nbt.getInteger("direction")];
	}
	
	public ForgeDirection getForward(){
		
		if(motionX>0.1){
				return ForgeDirection.EAST;
		}
		if(motionX<-0.1){
			return ForgeDirection.WEST;
		}
		
		if(motionY>0.1){
			return ForgeDirection.UP;
		}
		if(motionY<-0.1){
			return ForgeDirection.DOWN;
		}
	
		if(motionZ>0.1){
			return ForgeDirection.SOUTH;
		}
		if(motionZ<-0.1){
			return ForgeDirection.NORTH;
		}
		return ForgeDirection.UNKNOWN;
		
	}
	@Override
    public void moveEntityWithHeading(float par1, float par2)
    {
        double d0;

        
        
        {
            float f2 = 0.91F;

              

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.onGround)
            {
                f4 = this.getAIMoveSpeed() * f3;
            }
            else
            {
                f4 = this.jumpMovementFactor;
            }

            this.moveFlying(par1, par2, f4);
            f2 = 0.91F;

            if (this.onGround)
            {
                f2 = 0.54600006F;
                int j = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (j > 0)
                {
                    f2 = Block.blocksList[j].slipperiness * 0.91F;
                }
            }

           

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

            
        }

        this.prevLimbYaw = this.limbYaw;
        d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.limbYaw += (f6 - this.limbYaw) * 0.4F;
        this.limbSwing += this.limbYaw;
    }

		
		
		
	 public boolean isAIEnabled() { return false; }
	 protected void updateFallState(double par1, boolean par3) {}
	 public boolean canBePushed()
	 {
	     return false;
	 }
	
		
	
	
	

}
