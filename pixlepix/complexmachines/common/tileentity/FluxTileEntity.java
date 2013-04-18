package pixlepix.complexmachines.common.tileentity;

import java.util.List;

import pixlepix.complexmachines.common.ComplexMachines;

import net.minecraft.tileentity.TileEntity;

public class FluxTileEntity extends TileEntity {
	public int count;
	public boolean established=false;
	public int ticks;
	TileEntity entityInCharge;
	private boolean initialized=false;;
	public void count(){
		count++;
	}
	public void initiate() {
		this.initialized = true;
	}

	public void updateEntity(){
		super.updateEntity();
		
		if(worldObj.getTotalWorldTime()%100==0){
			established=true;
			count=0;
		}
		
		if(worldObj.getTotalWorldTime()%100==1&&established){
			int x=xCoord;
			int y=yCoord;
			int z=zCoord;
			int[][] blocks={{x+1,z+1},{x,z+1},{x-1,z+1},{x+1,z},{x-1,z},{x+1,z-1},{x,z-1},{x-1,z-1}};
			
			for(int i=0;i<8;i++){
				
				if(worldObj.getBlockId(blocks[i][0], y, blocks[i][1])==0){
					worldObj.setBlock(blocks[i][0], y, blocks[i][1], ComplexMachines.blockStartingID+18);
					TileEntity targetTileEntity=worldObj.getBlockTileEntity(blocks[i][0], y, blocks[i][1]);
				}
			}
		}
		if(worldObj.getTotalWorldTime()%100==2&&established){
			int x=xCoord;
			int y=yCoord;
			int z=zCoord;
			int[][] blocks={{x+1,z+1},{x,z+1},{x-1,z+1},{x+1,z},{x-1,z},{x+1,z-1},{x,z-1},{x-1,z-1}};
			
			for(int i=0;i<8;i++){
				if(worldObj.blockHasTileEntity(blocks[i][0], y, blocks[i][1])){
					TileEntity targetTileEntity=worldObj.getBlockTileEntity(blocks[i][0], y, blocks[i][1]);
					if(targetTileEntity instanceof FluxTileEntity){
						((FluxTileEntity)targetTileEntity).count();
					}
					
				}
				
			}
			
		}	
			
			
			
			
		
		if(worldObj.getTotalWorldTime()%100==3){
			boolean survivor=false;
			//System.out.println("Value at: "+xCoord+" , "+zCoord+" is "+count);
			if(established){
				if(count==2||count==3){
					survivor=true;
				}
			}else{
				if(count==3){
					survivor=true;
				}
			}
			if(!survivor){
				worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
			
	}
}

   