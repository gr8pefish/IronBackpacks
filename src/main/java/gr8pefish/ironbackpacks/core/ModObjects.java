package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.BackpackType;
import gr8pefish.ironbackpacks.api.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.IronBackpacksHelper;
import gr8pefish.ironbackpacks.item.ItemBackpack;
import gr8pefish.ironbackpacks.item.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModObjects {

    public static final ItemBackpack BACKPACK = new ItemBackpack();
    public static final ItemUpgrade UPGRADE = new ItemUpgrade();

    public static final SoundEvent BACKPACK_OPEN = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "open_backpack"));
    public static final SoundEvent BACKPACK_CLOSE = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "close_backpack"));

    public static final BackpackType PACK_BASIC = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "basic"), 0, 4, false);
    public static final BackpackType PACK_IRON = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "iron"), 1, 7, true);
    public static final BackpackType PACK_GOLD = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "gold"), 2, 12, true);
    public static final BackpackType PACK_DIAMOND = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "diamond"), 3, 18, true);

    public static final BackpackUpgrade UPGRADE_DAMAGE_BAR = new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "damage_bar"), 1, 0);
    public static final BackpackUpgrade UPGRADE_LOCK = new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "lock"), 1, 0); // TODO - Add texture

    public static void preInit() {
        register(BACKPACK_OPEN, "open_backpack");
        register(BACKPACK_CLOSE, "close_backpack");

        register(BACKPACK, "backpack");
        register(UPGRADE, "upgrade");

        IronBackpacksHelper.registerBackpackType(PACK_BASIC);
        IronBackpacksHelper.registerBackpackType(PACK_IRON);
        IronBackpacksHelper.registerBackpackType(PACK_GOLD);
        IronBackpacksHelper.registerBackpackType(PACK_DIAMOND);

        IronBackpacksHelper.registerUpgrade(UPGRADE_DAMAGE_BAR);
        IronBackpacksHelper.registerUpgrade(UPGRADE_LOCK);
    }

    public static <T extends IForgeRegistryEntry<T>> T register(T type, String name) {
        type.setRegistryName(new ResourceLocation(IronBackpacks.MODID, name));
        GameRegistry.register(type);

        if (type instanceof Item)
            IronBackpacks.PROXY.handleInventoryModel((Item) type);

        if (type instanceof Block)
            IronBackpacks.PROXY.handleInventoryModel((Block) type);

        return type;
    }
}
