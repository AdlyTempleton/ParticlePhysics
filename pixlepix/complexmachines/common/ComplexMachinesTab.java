package pixlepix.complexmachines.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ComplexMachinesTab extends CreativeTabs {
	public ComplexMachinesTab() {
		super("tabComplexMachines");
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(ComplexMachines.extractorMachine);
	}
}