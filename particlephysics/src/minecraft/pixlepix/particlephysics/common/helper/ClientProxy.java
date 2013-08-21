package pixlepix.particlephysics.common.helper;

import pixlepix.particlephysics.common.entity.CoalParticle;
import pixlepix.particlephysics.common.entity.ConcentratedParticle;
import pixlepix.particlephysics.common.entity.LapisParticle;
import pixlepix.particlephysics.common.entity.SplitParticle;
import pixlepix.particlephysics.common.render.RenderParticle;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static int RENDER_ID;
    
    public void registerRenderers() {
    	RenderingRegistry.registerEntityRenderingHandler(CoalParticle.class, new RenderParticle());

    	RenderingRegistry.registerEntityRenderingHandler(LapisParticle.class, new RenderParticle());

    	RenderingRegistry.registerEntityRenderingHandler(SplitParticle.class, new RenderParticle());

    	RenderingRegistry.registerEntityRenderingHandler(ConcentratedParticle.class, new RenderParticle());
	}
}
