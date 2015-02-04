package main.ironbackpacks.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import main.ironbackpacks.ModInformation;

public class NetworkingHandler {

    //Place to register all my messages and networkWrapper

    public static SimpleNetworkWrapper network;

    public static void initPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);
        registerMessage(ButtonUpgradeMessage.Handler.class, ButtonUpgradeMessage.class, Side.SERVER);
        registerMessage(RenameMessage.Handler.class, RenameMessage.class, Side.SERVER);
        registerMessage(AdvancedFilterMessage.Handler.class, AdvancedFilterMessage.class, Side.SERVER);
    }

    private static int nextPacketId = 0;

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}
