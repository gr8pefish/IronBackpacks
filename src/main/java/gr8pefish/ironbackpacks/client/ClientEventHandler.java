package gr8pefish.ironbackpacks.client;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.network.MessageRequestAction;
import gr8pefish.ironbackpacks.network.RequestAction;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler {

    public static final KeyBinding KEY_EQUIP = new KeyBinding("key." + IronBackpacks.MODID + ".equip", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_H, IronBackpacks.NAME);
    public static final KeyBinding KEY_OPEN = new KeyBinding("key." + IronBackpacks.MODID + ".open", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_I, IronBackpacks.NAME);

    @SubscribeEvent
    public static void onKey(InputEvent.KeyInputEvent event) {
        if (true) return; // TODO - Remove once backpack equipping has been fully implemented.
        if (KEY_EQUIP.isPressed())
            IronBackpacks.NETWORK.sendToServer(new MessageRequestAction(RequestAction.EQUIP_BACKPACK));
        else if (KEY_OPEN.isPressed())
            IronBackpacks.NETWORK.sendToServer(new MessageRequestAction(RequestAction.OPEN_BACKPACK));
    }
}
