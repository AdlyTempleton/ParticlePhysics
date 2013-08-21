package pixlepix.particlephysics.common.helper;

import net.minecraft.world.World;
import pixlepix.particlephysics.common.entity.BaseParticle;

public interface IParticleReceptor {

	
	public void onContact(BaseParticle particle);
	
}
