package pixlepix.particlephysics.common;

import pixlepix.particlephysics.common.entity.ClayParticle;
import pixlepix.particlephysics.common.entity.CoalParticle;
import pixlepix.particlephysics.common.entity.ConcentratedParticle;
import pixlepix.particlephysics.common.entity.SeedParticle;
import pixlepix.particlephysics.common.entity.SplitParticle;
import pixlepix.particlephysics.common.helper.BetterLoader;
import pixlepix.particlephysics.common.helper.CommonProxy;
import pixlepix.particlephysics.common.helper.ParticlePhysicsTab;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import pixlepix.particlephysics.common.helper.*;
import universalelectricity.compatibility.Compatibility;

@Mod(modid = "particlephysics", name = "Particle Physics", version = "0.3.3")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,  channels={"Particle"}, packetHandler = PacketHander.class)
public class ParticlePhysics {

	
	public static BetterLoader loader;
	
	public static ParticlePhysicsTab creativeTab = new ParticlePhysicsTab();
	
	
	public void loadBlocks(){
	

	
	}
	
	// The instance of your mod that Forge uses.
	@Instance("particlephysics")
	public static ParticlePhysics instance;
	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "pixlepix.particlephysics.common.helper.ClientProxy", serverSide = "pixlepix.particlephysics.common.helper.CommonProxy")
	public static CommonProxy proxy;



	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		loader=new BetterLoader();
		loader.loadBlocks();
		loadBlocks();
	}

	
	
	
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		Compatibility.initiate();
		/*
		for(ForgeDirection dir:ForgeDirection.VALID_DIRECTIONS){
			System.out.println(dir.toString()+dir.ordinal());
		}
		*/

		
		proxy.registerRenderers();
		
		loader.mainload();
		proxy.init();
		
		NetworkRegistry networkRegistry = NetworkRegistry.instance();
		


		LanguageRegistry.instance().addStringLocalization("itemGroup.tabParticlePhysics", "ParticlePhysics");
		EntityRegistry.registerModEntity(CoalParticle.class, "Coal Particle", 0, this, 80, 1, true);

		EntityRegistry.registerModEntity(ClayParticle.class, "Clay Particle", 1, this, 80, 1, true);

		EntityRegistry.registerModEntity(SplitParticle.class, "Split Particle", 2, this, 80, 1, true);

		EntityRegistry.registerModEntity(ConcentratedParticle.class, "Concentrated Particle", 3, this, 80, 1, true);

		EntityRegistry.registerModEntity(SeedParticle.class, "Seed Particle", 4, this, 80, 1, true);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		// Stub Method
	}
	
	
}
