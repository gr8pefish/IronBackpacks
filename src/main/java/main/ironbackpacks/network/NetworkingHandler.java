package main.ironbackpacks.network;

import main.ironbackpacks.ModInformation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Class to register all the messages and the networkWrapper
 */
public class NetworkingHandler {

    public static SimpleNetworkWrapper network;

    //initializes the wrapper and then the messages
    public static void initPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);
        registerMessage(RenameMessage.Handler.class, RenameMessage.class, Side.SERVER);
        registerMessage(AdvFilterTypesMessage.Handler.class, AdvFilterTypesMessage.class, Side.SERVER);
        registerMessage(SingleByteMessage.Handler.class, SingleByteMessage.class, Side.SERVER);
        registerMessage(ClientPackMessage.Handler.class, ClientPackMessage.class, Side.CLIENT);
        registerMessage(ClientPackMessage.Handler.class, ClientPackMessage.class, Side.SERVER); //has to be registered on servers so the servers know it exists so they can send it to the client?
    }

    private static int nextPacketId = 0;

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}
