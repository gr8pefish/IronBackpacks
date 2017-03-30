package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.BackpackType;
import gr8pefish.ironbackpacks.api.IronBackpacksHelper;
import gr8pefish.ironbackpacks.item.ItemBackpack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class ModObjects {

    public static final Item BACKPACK = new ItemBackpack();

    public static final SoundEvent OPEN_BACKPACK = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "open_backpack"));
    public static final SoundEvent CLOSE_BACKPACK = new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "close_backpack"));

    public static void preInit() {
        register(OPEN_BACKPACK, "open_backpack");
        register(CLOSE_BACKPACK, "close_backpack");

        register(BACKPACK, "backpack");

        IronBackpacksHelper.registerBackpackType(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_basic"), 0, 4, false));
        IronBackpacksHelper.registerBackpackType(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_iron"), 1, 7, true));
        IronBackpacksHelper.registerBackpackType(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_gold"), 2, 12, true));
        IronBackpacksHelper.registerBackpackType(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "backpack_diamond"), 3, 18, true));
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
