package pixlepix.particlephysics.common.entity;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.api.BaseParticle;

public class BlazepowderParticle extends BaseParticle {

	public BlazepowderParticle(World par1World) {
		super(par1World);
	}

	@Override
	public float getStartingPotential() {
		return 1000;
	}

	@Override
	public String getName() {
		return "Blazepowder";
	}

	@Override
	public void onCollideWithParticle(BaseParticle particle) {
		if((!(particle instanceof BlazepowderParticle))&&(particle.effect!=1)){
			particle.potential*=2;
			particle.effect=1;
		}
	}

}
