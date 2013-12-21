package pixlepix.particlephysics.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.particlephysics.common.helper.BasicComplexBlock;
import pixlepix.particlephysics.common.helper.BetterLoader;
import pixlepix.particlephysics.common.helper.ParticleRegistry;
import pixlepix.particlephysics.common.tile.EmitterTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class InfiniteEmitter extends BasicComplexBlock {



	public InfiniteEmitter() {
		super(1182);
	}
	public InfiniteEmitter(int i) {
		super(i);
	}
	@Override
	public String getFront() {
		// TODO Auto-generated method stub
		return "InfiniteEmitter";
	}
	@Override
	public boolean hasModel(){
		return true;
	}
	@Override
	public String getTop() {
		// TODO Auto-generated method stub
		return "InfiniteEmitterTop";
	}


	@Override
	public Class getTileEntityClass() {
		return EmitterTileEntity.class;
	}

	@Override
	public void addRecipe() {
		GameRegistry.addRecipe(new ItemStack(this),"XYX","YZY","XYX",'X',new ItemStack(Block.blockLapis),'Y',new ItemStack(Item.diamond),'Z',new ItemStack(BetterLoader.getBlock(Emitter.class)));

	}

	@Override
	public String getName() {
		return "InfiniteEmitter";
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
	public boolean topSidedTextures(){
		return true;
	}


	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ){

		if(world.getBlockTileEntity(x,y,z) instanceof EmitterTileEntity){
			if(!world.isRemote&&entityPlayer.inventory.getCurrentItem()!=null&&((EmitterTileEntity) world.getBlockTileEntity(x,y,z)).isValidFuel(entityPlayer.inventory.getCurrentItem().itemID)){

				EmitterTileEntity emitter=(EmitterTileEntity) world.getBlockTileEntity(x,y,z);
				if(entityPlayer.getHeldItem().stackSize==64){
					if(emitter.inventory!=null){
						world.spawnEntityInWorld(new EntityItem(world,x+0.5,y+0.5,z+0.5,emitter.inventory));
					}
					emitter.inventory=new ItemStack(entityPlayer.getHeldItem().getItem(),1,entityPlayer.getHeldItem().getItemDamage());

					entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem,null);
					return true;
				}
			}
		}

		return false;

	}



}
