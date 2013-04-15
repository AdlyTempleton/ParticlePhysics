
// This is my package declaration, do not mess with the standard (package net.minecraft.src;) like I did,
// Because I know what Im doing in this part, If you don't know what your doing keep it the normal (package net.minecraft.src;)
package pixlepix.complexmachines.client;

// These are all the imports you will need
import pixlepix.complexmachines.common.ContainerExtractor;
import pixlepix.complexmachines.common.ContainerGrinder;
import pixlepix.complexmachines.common.ExtractorMachineTileEntity;
import pixlepix.complexmachines.common.GrinderTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

// Create a class and implement IGuiHandler
public class GuiHandler implements IGuiHandler{
// This is a required method to open you Gui and has 6 params
// @param int id, this is the Gui Id
// @param EntityPlayer, this is the player declaration
// @param World, this is the world declaration
// @param int x, y, z this is the players current x, y, z coords
@Override
public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
// This gets the TileEntity the player is currently activating
TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
// This checks if the TileEntity is the TileTutorial
if(tile_entity instanceof GrinderTileEntity){
// If it is it returns a new ContainerTutorial instance
return new ContainerGrinder(player.inventory,(GrinderTileEntity) tile_entity);
}

if(tile_entity instanceof ExtractorMachineTileEntity){
	// If it is it returns a new ContainerTutorial instance
	return new ContainerExtractor(player.inventory,(ExtractorMachineTileEntity) tile_entity);
	}

// Returns null if not
return null;
}

// This is another required method to open the Gui and has 6 params
// @param int id, this is the Gui Id
// @param EntityPlayer, this is the player declaration
// @param World, this is the world declaration,
// @param int x, y, z this is the players current x, y, z coords
@Override
public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
// This gets the TIleEntity the player is currently activating
TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

// This checks if the TileEntity is the TileTutorial
if(tile_entity instanceof GrinderTileEntity){
// If it is it returns a new GuiTutorial instance
return new GuiGrinder(player.inventory, (GrinderTileEntity) tile_entity);
}

if(tile_entity instanceof ExtractorMachineTileEntity){
	// If it is it returns a new GuiTutorial instance
	return new GuiExtractor(player.inventory, (ExtractorMachineTileEntity) tile_entity);
}

// Returns null if not
return null;
}



}