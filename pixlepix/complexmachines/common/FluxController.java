package pixlepix.complexmachines.common;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import pixlepix.complexmachines.common.block.FluxTileEntity;
import pixlepix.complexmachines.common.block.IntermediateFluxTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeSubscribe;

public class FluxController implements ITickHandler {
	public World[] worlds;
	public int ticks;
	
	
	public void firstRound(){
		worlds = DimensionManager.getWorlds();
		for(World world:worlds){
			List<TileEntity> entities=world.loadedTileEntityList;
			for(int i=0;i<entities.size();i++){
				TileEntity tileEntity=entities.get(i);
				int x=tileEntity.xCoord;
				int y=tileEntity.yCoord;
				int z=tileEntity.zCoord;
				
				x+=1;
				TileEntity targetTileEntity=world.getBlockTileEntity(x, y, z);
				if(targetTileEntity instanceof FluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(targetTileEntity instanceof IntermediateFluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(world.getBlockId(x, y, z)==0){
					world.setBlock(x, y, z, ComplexMachines.blockStartingID+19);
				}
				x-=1;
				
				
				
				x-=1;
				targetTileEntity=world.getBlockTileEntity(x, y, z);
				if(targetTileEntity instanceof FluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(targetTileEntity instanceof IntermediateFluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(world.getBlockId(x, y, z)==0){
					world.setBlock(x, y, z, ComplexMachines.blockStartingID+19);
				}
				x+=1;
				
				
				
				
				z+=1;
				targetTileEntity=world.getBlockTileEntity(x, y, z);
				if(targetTileEntity instanceof FluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(targetTileEntity instanceof IntermediateFluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(world.getBlockId(x, y, z)==0){
					world.setBlock(x, y, z, ComplexMachines.blockStartingID+19);
				}
				z-=1;
				
				
				
				
				z-=1;
				targetTileEntity=world.getBlockTileEntity(x, y, z);
				if(targetTileEntity instanceof FluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(targetTileEntity instanceof IntermediateFluxTileEntity){
					((FluxTileEntity)targetTileEntity).count();
				}
				if(world.getBlockId(x, y, z)==0){
					world.setBlock(x, y, z, ComplexMachines.blockStartingID+19);
				}
				z+=1;
				
				
				
				
				
				
				
			}
		}
	}
	
	@ForgeSubscribe
	public void onTick(){
		ticks++;
		if(ticks%20==0){
			firstRound();
		}
		if(ticks%20==1){
			secondRound();
		}
		
	}

	public void secondRound() {
		
		
		
		for(World world:worlds){
			List<TileEntity> entities=world.loadedTileEntityList;
			for(int i=0;i<entities.size();i++){
				TileEntity tileEntity=entities.get(i);
				int x=tileEntity.xCoord;
				int y=tileEntity.yCoord;
				int z=tileEntity.zCoord;
				

				TileEntity targetTileEntity=world.getBlockTileEntity(x, y, z);
				if(targetTileEntity instanceof FluxTileEntity){
					FluxTileEntity flux=(FluxTileEntity)targetTileEntity;
					if(!(flux.count==2||flux.count==3)){
						world.setBlockToAir(x, y, z);
					}
				}
				if(targetTileEntity instanceof IntermediateFluxTileEntity){
					FluxTileEntity flux=(FluxTileEntity)targetTileEntity;
					if(flux.count==3){
						world.setBlock(x, y, z, ComplexMachines.blockStartingID+18);
					}else{
						world.setBlockToAir(x, y, z);
					}
				}
				
				
				
				
				
				
				
				
				
				
			}
		}
		
		
	}

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		onTick();
		System.out.println("Tick recieved");
		
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
