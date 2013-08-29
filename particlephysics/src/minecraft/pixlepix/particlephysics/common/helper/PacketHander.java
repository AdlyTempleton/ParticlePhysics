package pixlepix.particlephysics.common.helper;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import pixlepix.particlephysics.common.api.BaseParticle;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHander implements IPacketHandler {
	@Override
    public void onPacketData(INetworkManager manager,
                    Packet250CustomPayload packet, Player playerEntity) {

		if(packet.channel.equals("Particle")){
			this.handleParticleUpdatePacket(packet);
		}
    }
	private void handleParticleUpdatePacket(Packet250CustomPayload packet) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        

        
        try {

        	int entityId=inputStream.readInt();
        	Entity toMove=Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
        	if(toMove!=null){
        		toMove.setPosition(inputStream.readDouble(), inputStream.readDouble(),inputStream.readDouble());
        		toMove.setVelocity(inputStream.readDouble(),inputStream.readDouble(),inputStream.readDouble());
        		if(toMove instanceof BaseParticle){
        			((BaseParticle) toMove).effect=inputStream.readInt();
        		}
        	}
        } catch (IOException e) {
                e.printStackTrace();
                return;
        }
}

}
