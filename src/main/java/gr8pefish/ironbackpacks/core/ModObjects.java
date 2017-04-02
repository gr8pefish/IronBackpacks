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

    public static final Item BACKPACK = new ItemBackpack();
    public static final Item UPGRADE = new ItemUpgrade();

    public static final SoundEvent OPEN_BACKPACK = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "open_backpack"));
    public static final SoundEvent CLOSE_BACKPACK = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "close_backpack"));

    public static final BackpackType BASIC_PACK = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_basic"), 0, 4, false);
    public static final BackpackType IRON_PACK = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_iron"), 1, 7, true);
    public static final BackpackType GOLD_PACK = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_gold"), 2, 12, true);
    public static final BackpackType DIAMOND_PACK = new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_diamond"), 3, 18, true);

    public static final BackpackUpgrade DAMAGE_BAR_UPGADE = new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "upgrade_damage_bar"), 1, 0);

    public static void preInit() {
        register(OPEN_BACKPACK, "open_backpack");
        register(CLOSE_BACKPACK, "close_backpack");

        register(BACKPACK, "backpack");
        register(UPGRADE, "upgrade");

        IronBackpacksHelper.registerBackpackType(BASIC_PACK);
        IronBackpacksHelper.registerBackpackType(IRON_PACK);
        IronBackpacksHelper.registerBackpackType(GOLD_PACK);
        IronBackpacksHelper.registerBackpackType(DIAMOND_PACK);

        IronBackpacksHelper.registerUpgrade(DAMAGE_BAR_UPGADE);
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
