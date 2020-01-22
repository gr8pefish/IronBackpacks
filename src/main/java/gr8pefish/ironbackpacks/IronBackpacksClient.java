package gr8pefish.ironbackpacks;

import gr8pefish.ironbackpacks.item.BackpackItem;
import gr8pefish.ironbackpacks.item.DyeableBackpackItem;
import gr8pefish.ironbackpacks.util.registry.BackpackRegistry;
import gr8pefish.ironbackpacks.client.ItemColorsMap;
import gr8pefish.ironbackpacks.gui.BackpackController;
import gr8pefish.ironbackpacks.gui.BackpackScreen;
import gr8pefish.ironbackpacks.gui.UpgradeController;
import gr8pefish.ironbackpacks.gui.UpgradeScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static gr8pefish.ironbackpacks.IronBackpacks.*;

public class IronBackpacksClient implements ClientModInitializer {
    public static final SoundEvent BACKPACK_OPEN = new SoundEvent(new Identifier(MODID, "backpack_open"));
    public static final SoundEvent BACKPACK_CLOSE = new SoundEvent(new Identifier(MODID, "backpack_close"));
    private static final ItemColorProvider COLORER = (stack, tintIndex) ->
            tintIndex > 0 ? -1 : ((DyeableBackpackItem) stack.getItem()).getColor(stack);

    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(GUI_BACKPACK,
                (syncId, identifier, player, buf) -> new BackpackScreen(
                        new BackpackController(syncId, player.inventory, buf.readInt()), player));
        ScreenProviderRegistry.INSTANCE.registerFactory(GUI_UPGRADE,
                (syncId, identifier, player, buf) -> new UpgradeScreen(
                        new UpgradeController(syncId, player.inventory, buf.readInt()), player));
        for (BackpackItem item : BackpackRegistry.all()) {
            if (item instanceof DyeableBackpackItem) {
                ItemColorsMap.INSTANCE.register(COLORER, item);
            }
        }
    }
}
