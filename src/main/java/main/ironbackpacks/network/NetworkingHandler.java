package main.ironbackpacks.network;

import main.ironbackpacks.api.Constants;
import main.ironbackpacks.network.client.ClientCurrentPackMessage;
import main.ironbackpacks.network.client.ClientEquippedPackMessage;
import main.ironbackpacks.network.server.AdvFilterTypesMessage;
import main.ironbackpacks.network.server.RenameMessage;
import main.ironbackpacks.network.server.SingleByteMessage;
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
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.CHANNEL);
        registerMessage(RenameMessage.Handler.class, RenameMessage.class, Side.SERVER);
        registerMessage(AdvFilterTypesMessage.Handler.class, AdvFilterTypesMessage.class, Side.SERVER);
        registerMessage(SingleByteMessage.Handler.class, SingleByteMessage.class, Side.SERVER);
        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.CLIENT);
        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.CLIENT);
//        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.SERVER); //has to be registered on servers so the servers know it exists so they can send it to the client?
//        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.SERVER); //TODO: remove?
    }

    private static int nextPacketId = 0;

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}
