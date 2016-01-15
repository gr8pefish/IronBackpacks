package gr8pefish.ironbackpacks.client;

import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyHandler {
    public static final KeyBinding OPEN_PACK = new KeyBinding("key.ironbackpacks.open", Keyboard.KEY_C, Constants.MOD_NAME);
    public static final KeyBinding EQUIP_PACK = new KeyBinding("key.ironbackpacks.equip", Keyboard.KEY_V, Constants.MOD_NAME); //TODO: localize

    public static void init() {
        ClientRegistry.registerKeyBinding(OPEN_PACK);
        ClientRegistry.registerKeyBinding(EQUIP_PACK);
    }
}