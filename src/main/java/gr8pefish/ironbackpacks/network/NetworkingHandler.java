package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.network.client.ClientCurrentPackMessage;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.network.server.AdvFilterTypesMessage;
import gr8pefish.ironbackpacks.network.server.RenameMessage;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
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
        network = NetworkRegistry.INSTANCE.newSimpleChannel(IronBackpacksConstants.Messages.CHANNEL);
        registerMessage(RenameMessage.Handler.class, RenameMessage.class, Side.SERVER);
        registerMessage(AdvFilterTypesMessage.Handler.class, AdvFilterTypesMessage.class, Side.SERVER);
        registerMessage(SingleByteMessage.Handler.class, SingleByteMessage.class, Side.SERVER);
        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.CLIENT);
        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.CLIENT); //java.lang.RuntimeException: Attempted to load class net/minecraft/client/entity/EntityPlayerSP for invalid side SERVER
//        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.SERVER); //has to be registered on servers so the servers know it exists so they can send it to the client?
//        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.SERVER); //TODO: remove?

//        registerMessage(BackpackCapabilityMessage.ServerHandler.class, BackpackCapabilityMessage.class, Side.SERVER);
//        registerMessage(BackpackCapabilityMessage.ClientHandler.class, BackpackCapabilityMessage.class, Side.CLIENT);
    }

    private static int nextPacketId = 0;

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}
