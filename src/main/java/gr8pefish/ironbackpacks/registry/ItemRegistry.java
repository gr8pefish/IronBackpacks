package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.register.CraftingItemRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.craftingItems.*;
import gr8pefish.ironbackpacks.items.upgrades.upgradeItems.*;
import gr8pefish.ironbackpacks.items.upgrades.upgradeItems.filterUpgrades.*;
import gr8pefish.ironbackpacks.util.InventoryRenderHelper;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

/**
 * Registers all the items in this mod.
 */
public class ItemRegistry {

    //backpacks
    public static Item basicBackpack;
    public static Item ironBackpack;
    public static Item goldBackpack;
    public static Item diamondBackpack;

    //upgrades
    public static Item buttonUpgrade;
    public static Item nestingUpgrade;
    public static Item renamingUpgrade;
    public static Item damageBarUpgrade;
    public static Item filterBasicUpgrade;
    public static Item filterFuzzyUpgrade;
    public static Item filterOreDictUpgrade;
    public static Item filterModSpecificUpgrade;
    public static Item hopperUpgrade;
    public static Item condenserUpgrade;
    public static Item condenserSmallUpgrade;
    public static Item condenserTinyUpgrade;
    public static Item keepOnDeathUpgrade;
    public static Item additionalUpgradePointsUpgrade;
    public static Item quickDepositUpgrade;
    public static Item quickDepositPreciseUpgrade;
    public static Item filterAdvancedUpgrade;
    public static Item nestingAdvancedUpgrade;
    public static Item depthUpgrade;
    public static Item filterMiningUpgrade;
    public static Item filterVoidUpgrade;

    //misc
    public static Item craftingItem;
    public static ItemAPICrafting nest;
    public static ItemAPICrafting upgradeCore;
    public static ItemAPICrafting jeweledFeather;
    public static ItemAPICrafting treatedLeather;

    /**
     * Gets every backpack item.
     * @return - backpacks as an ArrayList of Items
     */
    public static ArrayList<Item> getBackpacks(){
        ArrayList<Item> backpacks = new ArrayList<Item>();
        backpacks.add(basicBackpack);
        backpacks.add(ironBackpack);
        backpacks.add(goldBackpack);
        backpacks.add(diamondBackpack);
        return backpacks;
    } //TODO: change this

    /**
     * Gets every upgrade item.
     * @return - upgrades as an ArrayList of Items
     */
    public static ArrayList<Item> getUpgrades(){
        ArrayList<Item> upgrades = new ArrayList<Item>();
        upgrades.add(buttonUpgrade);
        upgrades.add(nestingUpgrade);
        upgrades.add(damageBarUpgrade);
        if (ConfigHandler.renamingUpgradeRequired) upgrades.add(renamingUpgrade);
        upgrades.add(filterBasicUpgrade);
        upgrades.add(filterFuzzyUpgrade);
        upgrades.add(filterOreDictUpgrade);
        upgrades.add(filterModSpecificUpgrade);
        upgrades.add(hopperUpgrade);
        upgrades.add(condenserUpgrade);
        upgrades.add(condenserSmallUpgrade);
        upgrades.add(condenserTinyUpgrade);
        upgrades.add(keepOnDeathUpgrade);
        upgrades.add(additionalUpgradePointsUpgrade);
        upgrades.add(quickDepositUpgrade);
        upgrades.add(quickDepositPreciseUpgrade);
        upgrades.add(filterAdvancedUpgrade);
        upgrades.add(nestingAdvancedUpgrade);
        upgrades.add(depthUpgrade);
        upgrades.add(filterMiningUpgrade);
        upgrades.add(filterVoidUpgrade);
        return upgrades;
    }


    /**
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        //backpacks
        basicBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.BASIC_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME);
        ironBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.IRON_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME);
        goldBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.GOLD_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME);
        diamondBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME);

        //upgrades
        buttonUpgrade = new ItemButtonUpgrade();
        GameRegistry.registerItem(buttonUpgrade, "buttonUpgrade");
        nestingUpgrade = new ItemNestingUpgrade();
        GameRegistry.registerItem(nestingUpgrade, "nestingUpgrade");
        if (ConfigHandler.renamingUpgradeRequired){
            renamingUpgrade = new ItemRenamingUpgrade();
            GameRegistry.registerItem(renamingUpgrade, "renamingUpgrade");
        }
        damageBarUpgrade = new ItemDamageBarUpgrade();
        GameRegistry.registerItem(damageBarUpgrade, "damageBarUpgrade");
        filterBasicUpgrade = new ItemFilterBasicUpgrade();
        GameRegistry.registerItem(filterBasicUpgrade, "filterBasicUpgrade");
        hopperUpgrade = new ItemHopperUpgrade();
        GameRegistry.registerItem(hopperUpgrade, "hopperUpgrade");
        condenserUpgrade = new ItemCondenserUpgrade();
        GameRegistry.registerItem(condenserUpgrade, "condenserUpgrade");
        condenserSmallUpgrade = new ItemCondenserSmallUpgrade();
        GameRegistry.registerItem(condenserSmallUpgrade, "condenserSmallUpgrade");
        condenserTinyUpgrade = new ItemCondenserTinyUpgrade();
        GameRegistry.registerItem(condenserTinyUpgrade, "condenserTinyUpgrade");
        keepOnDeathUpgrade = new ItemKeepOnDeathUpgrade();
        GameRegistry.registerItem(keepOnDeathUpgrade, "keepOnDeathUpgrade");
        filterModSpecificUpgrade = new ItemFilterModSpecificUpgrade();
        GameRegistry.registerItem(filterModSpecificUpgrade, "filterModSpecificUpgrade");
        additionalUpgradePointsUpgrade = new ItemAdditionalUpgradePointsUpgrade();
        GameRegistry.registerItem(additionalUpgradePointsUpgrade, "additionalUpgradePointsUpgrade");
        filterFuzzyUpgrade = new ItemFilterFuzzyUpgrade();
        GameRegistry.registerItem(filterFuzzyUpgrade, "filterFuzzyUpgrade");
        filterOreDictUpgrade = new ItemFilterOreDictUpgrade();
        GameRegistry.registerItem(filterOreDictUpgrade, "filterOreDictUpgrade");
        quickDepositUpgrade = new ItemQuickDepositUpgrade();
        GameRegistry.registerItem(quickDepositUpgrade, "quickDepositUpgrade");
        quickDepositPreciseUpgrade = new ItemQuickDepositPreciseUpgrade();
        GameRegistry.registerItem(quickDepositPreciseUpgrade, "quickDepositPreciseUpgrade");
        filterAdvancedUpgrade = new ItemFilterAdvancedUpgrade();
        GameRegistry.registerItem(filterAdvancedUpgrade, "filterAdvancedUpgrade");
        nestingAdvancedUpgrade = new ItemNestingAdvancedUpgrade();
        GameRegistry.registerItem(nestingAdvancedUpgrade, "nestingAdvancedUpgrade");
        depthUpgrade = new ItemDepthUpgrade();
        GameRegistry.registerItem(depthUpgrade, "depthUpgrade");
        filterMiningUpgrade = new ItemFilterMiningUpgrade();
        GameRegistry.registerItem(filterMiningUpgrade, "filterMiningUpgrade");
        filterVoidUpgrade = new ItemFilterVoidUpgrade();
        GameRegistry.registerItem(filterVoidUpgrade, "filterVoidUpgrade");

        //misc
        craftingItem = registerItem(new ItemCrafting(), "crafting");
        nest = new ItemAPICrafting("nest");
        CraftingItemRegistry.registerItemCrafting(nest);
        upgradeCore = new ItemAPICrafting("upgradeCore");
        CraftingItemRegistry.registerItemCrafting(upgradeCore);
        jeweledFeather = new ItemAPICrafting("jeweledFeather");
        CraftingItemRegistry.registerItemCrafting(jeweledFeather);
        treatedLeather = new ItemAPICrafting("treatedLeather");
        CraftingItemRegistry.registerItemCrafting(treatedLeather);

    }

    public static void registerItemRenders() {

        InventoryRenderHelper renderHelper = new InventoryRenderHelper(Constants.DOMAIN);

        renderHelper.itemRender(basicBackpack, "ItemBackpackBasic");
        renderHelper.itemRender(ironBackpack, "ItemBackpackIron");
        renderHelper.itemRender(goldBackpack, "ItemBackpackGold");
        renderHelper.itemRender(diamondBackpack, "ItemBackpackDiamond");

        renderHelper.itemRender(buttonUpgrade, "buttonUpgrade");
        renderHelper.itemRender(nestingUpgrade, "nestingUpgrade");
        if (ConfigHandler.renamingUpgradeRequired)
            renderHelper.itemRender(renamingUpgrade, "renamingUpgrade");
        renderHelper.itemRender(damageBarUpgrade, "damageBarUpgrade");
        renderHelper.itemRender(filterBasicUpgrade, "filterBasicUpgrade");
        renderHelper.itemRender(hopperUpgrade, "hopperUpgrade");
        renderHelper.itemRender(condenserUpgrade, "condenserUpgrade");
        renderHelper.itemRender(condenserSmallUpgrade, "condenserSmallUpgrade");
        renderHelper.itemRender(condenserTinyUpgrade, "condenserTinyUpgrade");
        renderHelper.itemRender(keepOnDeathUpgrade, "keepOnDeathUpgrade");
        renderHelper.itemRender(filterModSpecificUpgrade, "filterModSpecificUpgrade");
        renderHelper.itemRender(additionalUpgradePointsUpgrade, "additionalUpgradePointsUpgrade");
        renderHelper.itemRender(filterFuzzyUpgrade, "filterFuzzyUpgrade");
        renderHelper.itemRender(filterOreDictUpgrade, "filterOreDictUpgrade");
        renderHelper.itemRender(filterMiningUpgrade, "filterMiningUpgrade");
        renderHelper.itemRender(filterVoidUpgrade, "filterVoidUpgrade");
        renderHelper.itemRender(quickDepositUpgrade, "quickDepositUpgrade");
        renderHelper.itemRender(quickDepositPreciseUpgrade, "quickDepositPreciseUpgrade");
        renderHelper.itemRender(filterAdvancedUpgrade, "filterAdvancedUpgrade");
        renderHelper.itemRender(nestingAdvancedUpgrade, "nestingAdvancedUpgrade");
        renderHelper.itemRender(depthUpgrade, "depthUpgrade");


        renderHelper.itemRenderAll(craftingItem);
        CraftingItemRegistry.registerCraftingItemTexture(nest, "ItemCraftingNest");
        CraftingItemRegistry.registerCraftingItemTexture(upgradeCore, "ItemCraftingUpgradeCore");
        CraftingItemRegistry.registerCraftingItemTexture(jeweledFeather, "ItemCraftingJeweledFeather");
        CraftingItemRegistry.registerCraftingItemTexture(treatedLeather, "ItemCraftingTreatedLeather");
    }


    //Helper methods for registering items
    private static Item registerItem(Item item, String name) {
        if (!ConfigHandler.itemBlacklist.contains(name))
            GameRegistry.registerItem(item, name);

        return item;
    }
}
