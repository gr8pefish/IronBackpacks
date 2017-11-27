package gr8pefish.ironbackpacks.core;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.*;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSize;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.upgrade.IUpgrade;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import gr8pefish.ironbackpacks.item.ItemBackpack;
import gr8pefish.ironbackpacks.item.ItemUpgrade;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(IronBackpacks.MODID)
public class RegistrarIronBackpacks {

    // Items
    public static final Item BACKPACK = Items.AIR;
    public static final Item UPGRADE = Items.AIR;

    // Sounds
    @GameRegistry.ObjectHolder("open_backpack")
    public static final SoundEvent BACKPACK_OPEN = null;
    @GameRegistry.ObjectHolder("close_backpack")
    public static final SoundEvent BACKPACK_CLOSE = null;

    // Backpack types
    @GameRegistry.ObjectHolder("basic")
    public static final BackpackType PACK_BASIC = null;
    @GameRegistry.ObjectHolder("iron")
    public static final BackpackType PACK_IRON = null;
    @GameRegistry.ObjectHolder("gold")
    public static final BackpackType PACK_GOLD = null;
    @GameRegistry.ObjectHolder("diamond")
    public static final BackpackType PACK_DIAMOND = null;

    // Backpack upgrade
    @GameRegistry.ObjectHolder("damage_bar")
    public static final BackpackUpgrade UPGRADE_DAMAGE_BAR = null;
    @GameRegistry.ObjectHolder("lock")
    public static final BackpackUpgrade UPGRADE_LOCK = null;
    @GameRegistry.ObjectHolder("extra_upgrade")
    public static final BackpackUpgrade UPGRADE_EXTRA_UPGRADE = null;
    @GameRegistry.ObjectHolder("everlasting")
    public static final BackpackUpgrade UPGRADE_EVERLASTING = null;

    // Registries
    private static IForgeRegistry<BackpackType> REGISTRY_TYPES = null;
    private static IForgeRegistry<BackpackUpgrade> REGISTRY_UPGRADES = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBackpack().setRegistryName("backpack"));
        event.getRegistry().register(new ItemUpgrade().setRegistryName("upgrade"));
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().register(new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "open_backpack")).setRegistryName("open_backpack"));
        event.getRegistry().register(new SoundEvent(new ResourceLocation(IronBackpacks.MODID, "close_backpack")).setRegistryName("close_backpack"));
    }

    @SubscribeEvent
    public static void registerBackpacks(RegistryEvent.Register<BackpackType> event) {
        event.getRegistry().register(new BackpackType(IronBackpacksAPI.NULL, 0, 0, false, BackpackSize.MIN));

        event.getRegistry().register(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "basic"), 0, 4, false, 9, 2));
        event.getRegistry().register(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "iron"), 1, 7, true, 9, 3));
        event.getRegistry().register(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "gold"), 2, 12, true, 9, 5));
        event.getRegistry().register(new BackpackType(new ResourceLocation(IronBackpacks.MODID, "diamond"), 3, 18, true, 9, 7));
    }

    @SubscribeEvent
    public static void registerUpgrades(RegistryEvent.Register<BackpackUpgrade> event) {
        event.getRegistry().register(new BackpackUpgrade(IronBackpacksAPI.NULL, 0, 0));

        if (ConfigHandler.upgrades.enableDamageBar)
            event.getRegistry().register(new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "damage_bar"), 1, 0));
        if (ConfigHandler.upgrades.enablePackLatch)
            event.getRegistry().register(new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "lock"), 1, 0));
        if (ConfigHandler.upgrades.enableExtraUpgrade)
            event.getRegistry().register(new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "extra_upgrade"), -1, 0));
        if (ConfigHandler.upgrades.enableEverlasting)
            event.getRegistry().register(new BackpackUpgrade(new ResourceLocation(IronBackpacks.MODID, "everlasting"), 4, 1));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Backpack
        ModelLoader.setCustomMeshDefinition(BACKPACK, stack -> {
            if (stack.getItem() instanceof IBackpack) {
                IBackpack backpack = (IBackpack) stack.getItem();
                BackpackInfo backpackInfo = backpack.getBackpackInfo(stack);
                ResourceLocation location = new ResourceLocation(backpackInfo.getVariant().getBackpackType().getIdentifier().getResourceDomain(), "backpack/" + backpackInfo.getVariant().getBackpackType().getIdentifier().getResourcePath());
                return new ModelResourceLocation(location, "inventory");
            }
            return new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "backpack/null"), "inventory");
        });

        for (BackpackType backpackType : IronBackpacksAPI.getBackpackTypes()) {
            if (!backpackType.isNull()) {
                ResourceLocation location = new ResourceLocation(backpackType.getIdentifier().getResourceDomain(), "backpack/" + backpackType.getIdentifier().getResourcePath());
                ModelLoader.registerItemVariants(BACKPACK, new ModelResourceLocation(location, "inventory"));
            }
        }

        ModelLoader.registerItemVariants(BACKPACK, new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "backpack/null"), "inventory"));

        // Upgrades
        ModelLoader.setCustomMeshDefinition(UPGRADE, stack -> {
            if (stack.getItem() instanceof IUpgrade) {
                IUpgrade upgrade = (IUpgrade) stack.getItem();
                BackpackUpgrade backpackUpgrade = upgrade.getUpgrade(stack);
                ResourceLocation location = new ResourceLocation(backpackUpgrade.getIdentifier().getResourceDomain(), "upgrade/" + backpackUpgrade.getIdentifier().getResourcePath());
                if (backpackUpgrade.isNull())
                    location = new ResourceLocation(IronBackpacks.MODID, "upgrade/null");
                return new ModelResourceLocation(location, "inventory");
            }
            return new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory");
        });

        for (BackpackUpgrade backpackUpgrade : IronBackpacksAPI.getUpgrades()) {
            if (!backpackUpgrade.isNull()) {
                ResourceLocation location = new ResourceLocation(backpackUpgrade.getIdentifier().getResourceDomain(), "upgrade/" + backpackUpgrade.getIdentifier().getResourcePath());
                ModelLoader.registerItemVariants(UPGRADE, new ModelResourceLocation(location, "inventory"));
            }
        }

        ModelLoader.registerItemVariants(UPGRADE, new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory"));
    }

    @SubscribeEvent
    public static void createRegistries(RegistryEvent.NewRegistry event) {
        REGISTRY_TYPES = new RegistryBuilder<BackpackType>()
                .setName(new ResourceLocation(IronBackpacks.MODID, "types"))
                .setDefaultKey(IronBackpacksAPI.NULL)
                .setType(BackpackType.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .addCallback((IForgeRegistry.AddCallback<BackpackType>) (o, s, id, obj, oldObj) -> Preconditions.checkNotNull(obj, "Attempted to register null Backpack type"))
                .create();

        REGISTRY_UPGRADES = new RegistryBuilder<BackpackUpgrade>()
                .setName(new ResourceLocation(IronBackpacks.MODID, "upgrade"))
                .setDefaultKey(IronBackpacksAPI.NULL)
                .setType(BackpackUpgrade.class)
                .setIDRange(0, Integer.MAX_VALUE - 1)
                .addCallback((IForgeRegistry.AddCallback<BackpackUpgrade>) (o, s, id, obj, oldObj) -> Preconditions.checkNotNull(obj, "Attempted to register null Backpack upgrade"))
                .create();
    }
}
