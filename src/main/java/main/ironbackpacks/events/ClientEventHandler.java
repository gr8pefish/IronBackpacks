package main.ironbackpacks.events;

import main.ironbackpacks.client.KeyHandler;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.SingleByteMessage;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (KeyHandler.EQUIP_PACK.isPressed()) {
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.EQUIP_BACKPACK));
            return;
        }

        if (KeyHandler.OPEN_PACK.isPressed())
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.OPEN_BACKPACK));
    }
}
