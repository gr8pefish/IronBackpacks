package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemCraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.craftingItems.*;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgrade;
import gr8pefish.ironbackpacks.util.InventoryRenderHelper;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.item.Item;
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
    public static Item upgradeItem;
    public static ItemPackUpgrade buttonUpgrade;
    public static ItemPackUpgrade nestingUpgrade;
    public static ItemPackUpgrade renamingUpgrade;
    public static ItemPackUpgrade damageBarUpgrade;
    public static ItemPackUpgrade filterBasicUpgrade;
    public static ItemPackUpgrade filterFuzzyUpgrade;
    public static ItemPackUpgrade filterOreDictUpgrade;
    public static ItemPackUpgrade filterModSpecificUpgrade;
    public static ItemPackUpgrade restockingUpgrade;
    public static ItemPackUpgrade craftingUpgrade;
    public static ItemPackUpgrade craftingSmallUpgrade;
    public static ItemPackUpgrade craftingTinyUpgrade;
    public static ItemPackUpgrade eternityUpgrade;
    public static ItemPackUpgrade additionalUpgradePointsUpgrade;
    public static ItemPackUpgrade quickDepositUpgrade;
    public static ItemPackUpgrade quickDepositPreciseUpgrade;
    public static ItemPackUpgrade filterAdvancedUpgrade;
    public static ItemPackUpgrade nestingAdvancedUpgrade;
    public static ItemPackUpgrade depthUpgrade;
    public static ItemPackUpgrade filterMiningUpgrade;
    public static ItemPackUpgrade filterVoidUpgrade;

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
        upgrades.add(buttonUpgrade.getItem());
        upgrades.add(nestingUpgrade.getItem());
        upgrades.add(damageBarUpgrade.getItem());
        if (ConfigHandler.renamingUpgradeRequired) upgrades.add(renamingUpgrade.getItem());
        upgrades.add(filterBasicUpgrade.getItem());
        upgrades.add(filterFuzzyUpgrade.getItem());
        upgrades.add(filterOreDictUpgrade.getItem());
        upgrades.add(filterModSpecificUpgrade.getItem());
        upgrades.add(restockingUpgrade.getItem());
        upgrades.add(craftingUpgrade.getItem());
        upgrades.add(craftingSmallUpgrade.getItem());
        upgrades.add(craftingTinyUpgrade.getItem());
        upgrades.add(eternityUpgrade.getItem());
        upgrades.add(additionalUpgradePointsUpgrade.getItem());
        upgrades.add(quickDepositUpgrade.getItem());
        upgrades.add(quickDepositPreciseUpgrade.getItem());
        upgrades.add(filterAdvancedUpgrade.getItem());
        upgrades.add(nestingAdvancedUpgrade.getItem());
        upgrades.add(depthUpgrade.getItem());
        upgrades.add(filterMiningUpgrade.getItem());
        upgrades.add(filterVoidUpgrade.getItem());
        return upgrades;
    } //TODO: change this


    /**
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        //Backpack Items
        basicBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.BASIC_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME);
        ironBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.IRON_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME);
        goldBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.GOLD_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME);
        diamondBackpack = registerItem(new ItemBackpack(IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_ENUM_NAME), IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME);

        //Upgrade Items
        upgradeItem = registerItem(new ItemUpgrade(), IronBackpacksAPI.ITEM_UPGRADE_BASE);
        //basic upgrades
        if (ConfigHandler.renamingUpgradeRequired){
            renamingUpgrade = new ItemPackUpgrade("renaming", ConfigHandler.renamingUpgradeCost, IronBackpacksConstants.Upgrades.RENAMING_DESCRIPTION, ConfigHandler.renamingUpgradeRecipe);
            ItemUpgradeRegistry.registerItemUpgrade(renamingUpgrade);
        }
        buttonUpgrade = new ItemPackUpgrade("button", ConfigHandler.buttonUpgradeCost, IronBackpacksConstants.Upgrades.BUTTON_DESCRIPTION, ConfigHandler.buttonUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(buttonUpgrade);
        damageBarUpgrade = new ItemPackUpgrade("damageBar", ConfigHandler.damageBarUpgradeCost, IronBackpacksConstants.Upgrades.DAMAGE_BAR_DESCRIPTION, ConfigHandler.damageBarUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(damageBarUpgrade);
        eternityUpgrade = new ItemPackUpgrade("eternity", ConfigHandler.eternityUpgradeCost, IronBackpacksConstants.Upgrades.ETERNITY_DESCRIPTION, ConfigHandler.eternityUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(eternityUpgrade);
        additionalUpgradePointsUpgrade = new ItemPackUpgrade("additionalUpgradePoints", 0, IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_POINTS_DESCRIPTION, ConfigHandler.additionalUpgradePointsUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(additionalUpgradePointsUpgrade);
        depthUpgrade = new ItemPackUpgrade("depth", ConfigHandler.depthUpgradeCost, IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_DESCRIPTION, ConfigHandler.depthUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(depthUpgrade);

        //conflicting upgrades
        nestingUpgrade = new ItemPackUpgrade("nesting", ConfigHandler.nestingUpgradeCost, IronBackpacksConstants.Upgrades.NESTING_DESCRIPTION, ConfigHandler.nestingUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(nestingUpgrade);
        nestingAdvancedUpgrade = new ItemPackUpgrade("nestingAdvanced", ConfigHandler.nestingAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.NESTING_ADVANCED_DESRIPTION, ConfigHandler.nestingAdvancedUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(nestingAdvancedUpgrade);
        quickDepositUpgrade = new ItemPackUpgrade("quickDeposit", ConfigHandler.quickDepositUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_DESCRIPTION, ConfigHandler.quickDepositUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(quickDepositUpgrade);
        quickDepositPreciseUpgrade = new ItemPackUpgrade("quickDepositPrecise", ConfigHandler.quickDepositPreciseUpgradeCost, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION, ConfigHandler.quickDepositPreciseUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(quickDepositPreciseUpgrade);

        //alt gui upgrades
        filterBasicUpgrade = new ItemPackUpgrade("filterBasic", ConfigHandler.filterBasicUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_BASIC_DESCRIPTION, ConfigHandler.filterBasicUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterBasicUpgrade);
        filterFuzzyUpgrade = new ItemPackUpgrade("filterFuzzy", ConfigHandler.filterFuzzyUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_FUZZY_DESCRIPTION, ConfigHandler.filterFuzzyUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterFuzzyUpgrade);
        filterOreDictUpgrade = new ItemPackUpgrade("filterOreDict", ConfigHandler.filterOreDictUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_OREDICT_DESCRIPTION, ConfigHandler.filterOreDictUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterOreDictUpgrade);
        filterModSpecificUpgrade = new ItemPackUpgrade("filterModSpecific", ConfigHandler.filterModSpecificUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION, ConfigHandler.filterModSpecificUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterModSpecificUpgrade);
        filterAdvancedUpgrade = new ItemPackUpgrade("filterAdvanced", ConfigHandler.filterAdvancedUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_ADVANCED_DESCRIPTION, ConfigHandler.filterAdvancedUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterAdvancedUpgrade);
        filterMiningUpgrade = new ItemPackUpgrade("filterMining", ConfigHandler.filterMiningUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_MINING_DESCRIPTION, ConfigHandler.filterMiningUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterMiningUpgrade);
        filterVoidUpgrade = new ItemPackUpgrade("filterVoid", ConfigHandler.filterVoidUpgradeCost, IronBackpacksConstants.Upgrades.FILTER_VOID_DESCRIPTION, ConfigHandler.filterVoidUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(filterVoidUpgrade);
        restockingUpgrade = new ItemPackUpgrade("restocking", ConfigHandler.restockingUpgradeCost, IronBackpacksConstants.Upgrades.RESTOCKING_DESCRIPTION, ConfigHandler.restockingUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(restockingUpgrade);
        craftingUpgrade = new ItemPackUpgrade("crafting", ConfigHandler.craftingUpgradeCost, IronBackpacksConstants.Upgrades.CRAFTING_DESCRIPTION, ConfigHandler.craftingUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(craftingUpgrade);
        craftingSmallUpgrade = new ItemPackUpgrade("craftingSmall", ConfigHandler.craftingSmallUpgradeCost, IronBackpacksConstants.Upgrades.CRAFTING_SMALL_DESCRIPTION, ConfigHandler.craftingSmallUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(craftingSmallUpgrade);
        craftingTinyUpgrade = new ItemPackUpgrade("craftingTiny", ConfigHandler.craftingTinyUpgradeCost, IronBackpacksConstants.Upgrades.CRAFTING_TINY_DESCRIPTION, ConfigHandler.craftingTinyUpgradeRecipe);
        ItemUpgradeRegistry.registerItemUpgrade(craftingTinyUpgrade);

        //Crafting Items
        craftingItem = registerItem(new ItemCrafting(), IronBackpacksAPI.ITEM_CRAFTING_BASE);
        //sub items
        nest = new ItemAPICrafting("nest");
        ItemCraftingRegistry.registerItemCrafting(nest);
        upgradeCore = new ItemAPICrafting("upgradeCore");
        ItemCraftingRegistry.registerItemCrafting(upgradeCore);
        jeweledFeather = new ItemAPICrafting("jeweledFeather");
        ItemCraftingRegistry.registerItemCrafting(jeweledFeather);
        treatedLeather = new ItemAPICrafting("treatedLeather");
        ItemCraftingRegistry.registerItemCrafting(treatedLeather);

    }

    public static void registerItemRenders() {

        //init the render helper
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(Constants.DOMAIN);

        //render the backpack items
        renderHelper.itemRender(basicBackpack, "ItemBackpackBasic");
        renderHelper.itemRender(ironBackpack, "ItemBackpackIron");
        renderHelper.itemRender(goldBackpack, "ItemBackpackGold");
        renderHelper.itemRender(diamondBackpack, "ItemBackpackDiamond");


        //render the upgrade items
        renderHelper.itemRenderAll(upgradeItem);
        //normal upgrades
        if (ConfigHandler.renamingUpgradeRequired)
            ItemUpgradeRegistry.registerItemUpgradeTexture(renamingUpgrade, "ItemUpgradeRenaming");
        ItemUpgradeRegistry.registerItemUpgradeTexture(buttonUpgrade, "ItemUpgradeButton");
        ItemUpgradeRegistry.registerItemUpgradeTexture(damageBarUpgrade, "ItemUpgradeDamageBar");
        ItemUpgradeRegistry.registerItemUpgradeTexture(eternityUpgrade, "ItemUpgradeEternity");
        ItemUpgradeRegistry.registerItemUpgradeTexture(additionalUpgradePointsUpgrade, "ItemUpgradeAdditionalUpgradePoints");
        ItemUpgradeRegistry.registerItemUpgradeTexture(depthUpgrade, "ItemUpgradeDepth");
        //conflicting upgrades
        ItemUpgradeRegistry.registerItemUpgradeTexture(nestingUpgrade, "ItemUpgradeNesting");
        ItemUpgradeRegistry.registerItemUpgradeTexture(nestingAdvancedUpgrade, "ItemUpgradeNestingAdvanced");
        ItemUpgradeRegistry.registerItemUpgradeTexture(quickDepositUpgrade, "ItemUpgradeQuickDeposit");
        ItemUpgradeRegistry.registerItemUpgradeTexture(quickDepositPreciseUpgrade, "ItemUpgradeQuickDepositPrecise");
        //alt gui upgrades
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterBasicUpgrade, "ItemUpgradeFilterBasic");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterFuzzyUpgrade, "ItemUpgradeFilterFuzzy");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterOreDictUpgrade, "ItemUpgradeFilterOreDict");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterModSpecificUpgrade, "ItemUpgradeFilterModSpecific");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterAdvancedUpgrade, "ItemUpgradeFilterAdvanced");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterMiningUpgrade, "ItemUpgradeFilterMining");
        ItemUpgradeRegistry.registerItemUpgradeTexture(filterVoidUpgrade, "ItemUpgradeFilterVoid");
        ItemUpgradeRegistry.registerItemUpgradeTexture(craftingUpgrade, "ItemUpgradeCrafting");
        ItemUpgradeRegistry.registerItemUpgradeTexture(craftingSmallUpgrade, "ItemUpgradeCraftingSmall");
        ItemUpgradeRegistry.registerItemUpgradeTexture(craftingTinyUpgrade, "ItemUpgradeCraftingTiny");
        ItemUpgradeRegistry.registerItemUpgradeTexture(restockingUpgrade, "ItemUpgradeRestocking");


        //render the crafting items
        renderHelper.itemRenderAll(craftingItem);
        ItemCraftingRegistry.registerItemCraftingTexture(nest, "ItemCraftingNest");
        ItemCraftingRegistry.registerItemCraftingTexture(upgradeCore, "ItemCraftingUpgradeCore");
        ItemCraftingRegistry.registerItemCraftingTexture(jeweledFeather, "ItemCraftingJeweledFeather");
        ItemCraftingRegistry.registerItemCraftingTexture(treatedLeather, "ItemCraftingTreatedLeather");
    }


    //Helper methods for registering items

    /**
     * Helper method to register the items. Will only register if not on the config's blacklist.
     * @param item - the item to register
     * @param name - the name of the file to register the texture with (in assets/MOD_ID/models/item/FILE_HERE)
     * @return - the item
     */
    private static Item registerItem(Item item, String name) {
        if (!ConfigHandler.itemBlacklist.contains(name))
            GameRegistry.registerItem(item, name);

        return item;
    }
}
