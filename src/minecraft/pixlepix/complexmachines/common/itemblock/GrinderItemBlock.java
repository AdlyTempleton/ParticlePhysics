package pixlepix.complexmachines.common.itemblock;

import java.util.List;

import org.lwjgl.input.Keyboard;

import pixlepix.complexmachines.common.EnumColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GrinderItemBlock extends ItemBlock {

	public GrinderItemBlock(int par1) {
		super(par1);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4){
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			list.add("Hold " + EnumColor.AQUA + "shift" + EnumColor.GREY + " for more details.");
		}
		else {
			
			list.add(EnumColor.AQUA + "This machine will grind blocks up to produce power");
			list.add(EnumColor.RED + "500J per ore block grinded");
		}
	}

}
