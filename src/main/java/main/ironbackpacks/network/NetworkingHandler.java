package main.ironbackpacks.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.ButtonUpgrade;

public class NetworkingHandler {

    public static SimpleNetworkWrapper network;

    public static void initPackets() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);
        registerMessage(ButtonUpgradeMessage.Handler.class, ButtonUpgradeMessage.class, Side.SERVER);
        registerMessage(RenameMessage.Handler.class, RenameMessage.class, Side.SERVER);
    }

    private static int nextPacketId = 0;

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}
