package pixlepix.particlephysics.common.blocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;
import pixlepix.particlephysics.common.tile.EmitterTileEntity;

public class Emitter extends BasicComplexBlock {

	
	
	public Emitter() {
		super(1178);
	}
	public Emitter(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "Emitter";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "EmitterTop";
	}

	
	@Override
	public Class getTileEntityClass() {
		return EmitterTileEntity.class;
	}

	@Override
	public void addRecipe() {

		
	}

	@Override
	public String getName() {
		return "Emitter";
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
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote&&entityPlayer.inventory.getCurrentItem()!=null&&EmitterTileEntity.isValidFuel(entityPlayer.inventory.getCurrentItem())){
			
			if(world.getBlockTileEntity(x,y,z) instanceof EmitterTileEntity){
				EmitterTileEntity emitter=(EmitterTileEntity) world.getBlockTileEntity(x,y,z);
				if(emitter.inventory==null||(entityPlayer.getHeldItem()!=null&&emitter.inventory.getItem()!=entityPlayer.getHeldItem().getItem())){
					if(emitter.inventory!=null){
						world.spawnEntityInWorld(new EntityItem(world,x+0.5,y+0.5,z+0.5,emitter.inventory));
					}
					emitter.inventory=new ItemStack(entityPlayer.getHeldItem().getItem());
				}else{
					emitter.inventory.stackSize++;
				}
				entityPlayer.inventory.consumeInventoryItem(entityPlayer.getHeldItem().itemID);
				return true;
			}
		}
		return false;
	}
	
	

	

}
