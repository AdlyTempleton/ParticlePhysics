package pixlepix.complexmachines.common.itemblock;

import java.util.List;

import org.lwjgl.input.Keyboard;

import pixlepix.complexmachines.common.EnumColor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LaserItemBlock extends ItemBlock {

	public LaserItemBlock(int par1) {
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
			
			list.add(EnumColor.AQUA + "This machine will create lasers");
			list.add(EnumColor.RED + "4KW while firing a laser");
			list.add(EnumColor.GREY+"Right click with different items");
			list.add(EnumColor.GREY+"To change the type of beam");
			list.add(EnumColor.ORANGE+"Diamond sword: "+EnumColor.PURPLE+"Damaging laser");
			list.add(EnumColor.ORANGE+"Nether star: "+EnumColor.PURPLE+"Debuff laser");
			list.add(EnumColor.ORANGE+"Glass: "+EnumColor.PURPLE+"Hidden laser");
			list.add(EnumColor.ORANGE+"Redstone: "+EnumColor.PURPLE+"Redstone laser");
			list.add(EnumColor.ORANGE+"Water Bucket: "+EnumColor.PURPLE+"Electrifying laser");
			list.add(EnumColor.ORANGE+"Seeds: "+EnumColor.PURPLE+"Flux");
			list.add(EnumColor.ORANGE+"Minecart: "+EnumColor.PURPLE+"Suction laser");
			list.add(EnumColor.ORANGE+"Cobblestone: "+EnumColor.PURPLE+"Solid laser");
			list.add(EnumColor.ORANGE+"Diamond pickaxe: "+EnumColor.PURPLE+"Mining laser");
			

		}
	}

}
