package gr8pefish.ironbackpacks.network;

import gr8pefish.ironbackpacks.network.client.ClientCurrentPackMessage;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackPlayerSensitiveMessage;
import gr8pefish.ironbackpacks.network.server.AdvFilterTypesMessage;
import gr8pefish.ironbackpacks.network.server.PlayerSlotNumberMessage;
import gr8pefish.ironbackpacks.network.server.RenameMessage;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
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
        registerMessage(PlayerSlotNumberMessage.Handler.class, PlayerSlotNumberMessage.class, Side.SERVER);
        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.CLIENT);
        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.CLIENT); //java.lang.RuntimeException: Attempted to load class net/minecraft/client/entity/EntityPlayerSP for invalid side SERVER
        registerMessage(ClientEquippedPackPlayerSensitiveMessage.Handler.class, ClientEquippedPackPlayerSensitiveMessage.class, Side.CLIENT);
//        registerMessage(ClientCurrentPackMessage.Handler.class, ClientCurrentPackMessage.class, Side.SERVER); //has to be registered on servers so the servers know it exists so they can send it to the client?
//        registerMessage(ClientEquippedPackMessage.Handler.class, ClientEquippedPackMessage.class, Side.SERVER); //TODO: remove?
    }

    private static int nextPacketId = 0;

    private static <M extends IMessage, MH extends IMessageHandler<M, IMessage>> void registerMessage(Class<MH> packet, Class<M> message, Side side) {
        network.registerMessage(packet, message, nextPacketId++, side);
    }
}
