package gr8pefish.ironbackpacks.api;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IronBackpacksAPI {

    static public final String ITEM_BACKPACK_BASE_NAME = "backpack";
    static public final String ITEM_UPGRADE_BASE_NAME = "upgrade";
    static public final String ITEM_CRAFTING_BASE_NAME = "crafting";

    /**
     * Used to obtain Items from IronBackpacks. Use the constants above for common
     * items in case internal names change.
     *
     * @param name - The registered name of the items. Check the lang file, usually lowercase singular denominations.
     * @return - The requested Item
     */
    public static Item getItem(String name) {
        return GameRegistry.findItem(Constants.MODID, name);
    }
}
