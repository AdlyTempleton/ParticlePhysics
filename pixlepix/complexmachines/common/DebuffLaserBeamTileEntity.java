package pixlepix.complexmachines.common;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;

public class DebuffLaserBeamTileEntity extends LaserBeamTileEntity {
	public void updateEntity(){
		
		super.updateEntity();
		List<EntityLiving> entities=worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord-.5, yCoord-.5, zCoord-.5, xCoord+.5, yCoord+.5, zCoord+.5));
		//System.out.println(entities);
		for(int i=0;i<entities.size();i++){
			
			PotionEffect slow = new PotionEffect( Potion.moveSlowdown.getId(), 2000,5 );
			PotionEffect weak = new PotionEffect( Potion.wither.getId(), 2000,5 );
			PotionEffect wither = new PotionEffect( Potion.weakness.getId(), 2000,5 );
			entities.get(i).addPotionEffect(slow);
			entities.get(i).addPotionEffect(weak);
			entities.get(i).addPotionEffect(wither);
			
		}
	}
}
