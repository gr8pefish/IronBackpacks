package main.ironbackpacks.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import main.ironbackpacks.ModInformation;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInformation.CHANNEL);

    public static void init() {
        INSTANCE.registerMessage(IronBackpacksMessage.Handler.class, IronBackpacksMessage.class, 0, Side.SERVER);
    }
}
