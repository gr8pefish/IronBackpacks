package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemAltGuiUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemConflictingUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemCraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.craftingItems.*;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgrade;
import gr8pefish.ironbackpacks.util.InventoryRenderHelper;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Registers all the items in this mod.
 */
public class ItemRegistry {

    //backpacks
    public static ItemBackpack basicBackpack;
    public static ItemBackpack ironBackpack;
    public static ItemBackpack goldBackpack;
    public static ItemBackpack diamondBackpack;

    //upgrades
    public static ItemUpgrade upgradeItem;
    //normal
    public static ItemPackUpgrade additionalUpgradePointsUpgrade;
    public static ItemPackUpgrade buttonUpgrade;
    public static ItemPackUpgrade damageBarUpgrade;
    public static ItemPackUpgrade depthUpgrade;
    public static ItemPackUpgrade eternityUpgrade;
    public static ItemPackUpgrade renamingUpgrade;
    //conflicting
    public static ItemConflictingUpgrade nestingUpgrade;
    public static ItemConflictingUpgrade nestingAdvancedUpgrade;
    public static ItemConflictingUpgrade quickDepositUpgrade;
    public static ItemConflictingUpgrade quickDepositPreciseUpgrade;
    //alt gui
    public static ItemAltGuiUpgrade craftingUpgrade;
    public static ItemAltGuiUpgrade craftingSmallUpgrade;
    public static ItemAltGuiUpgrade craftingTinyUpgrade;
    public static ItemAltGuiUpgrade filterBasicUpgrade;
    public static ItemAltGuiUpgrade filterFuzzyUpgrade;
    public static ItemAltGuiUpgrade filterOreDictUpgrade;
    public static ItemAltGuiUpgrade filterModSpecificUpgrade;
    public static ItemAltGuiUpgrade filterAdvancedUpgrade;
    public static ItemAltGuiUpgrade filterMiningUpgrade;
    public static ItemAltGuiUpgrade filterVoidUpgrade;
    public static ItemAltGuiUpgrade restockingUpgrade;

    //misc
    public static ItemCrafting craftingItem;
    public static ItemAPICrafting jeweledFeather;
    public static ItemAPICrafting nest;
    public static ItemAPICrafting treatedLeather;
    public static ItemAPICrafting upgradeCore;

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
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        //Backpack Items (Tiered order)
        basicBackpack = (ItemBackpack) //typecast the returned item
                registerItem(new ItemBackpack( //register the new ItemBackpack
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME, //name
                ConfigHandler.enumBasicBackpack.sizeX.getValue(), //rowLength
                ConfigHandler.enumBasicBackpack.sizeY.getValue(), //rowCount
                ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), //upgrade points, (next line) resource location of gui
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200: 236), //gui width
                (114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue())), //gui height
                ConfigHandler.basicBackpackRecipe, //item recipe (to get the backpack directly)
                null), //upgrade recipe (to get this item from upgrading another backpack)
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME); //registry name

        ironBackpack = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME,
                ConfigHandler.enumIronBackpack.sizeX.getValue(),
                ConfigHandler.enumIronBackpack.sizeY.getValue(),
                ConfigHandler.enumIronBackpack.upgradePoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumIronBackpack.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumIronBackpack.sizeY.getValue())),
                null,
                Collections.singletonList(ConfigHandler.ironBackpackToUpgradeRecipe)),
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME);

        goldBackpack = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME,
                ConfigHandler.enumGoldBackpack.sizeX.getValue(),
                ConfigHandler.enumGoldBackpack.sizeY.getValue(),
                ConfigHandler.enumGoldBackpack.upgradePoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumGoldBackpack.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumGoldBackpack.sizeY.getValue())),
                null,
                Collections.singletonList(ConfigHandler.goldBackpackToUpgradeRecipe)),
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME);

        diamondBackpack = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME,
                ConfigHandler.enumDiamondBackpack.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpack.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpack.upgradePoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumDiamondBackpack.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumDiamondBackpack.sizeY.getValue())),
                null,
                Collections.singletonList(ConfigHandler.diamondBackpackToUpgradeRecipe)),
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME);

        //Sets the tiers and links between all the backpacks.
        //Has to be called after the item initialization because it uses the backpack items themselves.
        setTieringOfBackpacks();

        //Upgrade Items (alphabetical order, except the adv. filter filters)
        upgradeItem = (ItemUpgrade) registerItem(new ItemUpgrade(), IronBackpacksAPI.ITEM_UPGRADE_BASE);
        //basic upgrades
        additionalUpgradePointsUpgrade = new ItemPackUpgrade(
                "additionalUpgradePoints", //name
                0, //cost of upgrade points to apply the upgrade
                0, //tier of backpack necessary to apply the upgrade (TODO: add as config option)
                IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_POINTS_DESCRIPTION, //description/tooltip
                ConfigHandler.additionalUpgradePointsUpgradeRecipe); //recipe
        ItemUpgradeRegistry.registerItemPackUpgrade(additionalUpgradePointsUpgrade);
        buttonUpgrade = new ItemPackUpgrade("button", ConfigHandler.buttonUpgradeCost, 0, IronBackpacksConstants.Upgrades.BUTTON_DESCRIPTION, ConfigHandler.buttonUpgradeRecipe);
        ItemUpgradeRegistry.registerItemPackUpgrade(buttonUpgrade);
        damageBarUpgrade = new ItemPackUpgrade("damageBar", ConfigHandler.damageBarUpgradeCost, 0, IronBackpacksConstants.Upgrades.DAMAGE_BAR_DESCRIPTION, ConfigHandler.damageBarUpgradeRecipe);
        ItemUpgradeRegistry.registerItemPackUpgrade(damageBarUpgrade);
        depthUpgrade = new ItemPackUpgrade("depth", ConfigHandler.depthUpgradeCost, 0, IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_DESCRIPTION, ConfigHandler.depthUpgradeRecipe);
        ItemUpgradeRegistry.registerItemPackUpgrade(depthUpgrade);
        eternityUpgrade = new ItemPackUpgrade("eternity", ConfigHandler.eternityUpgradeCost, 0, IronBackpacksConstants.Upgrades.ETERNITY_DESCRIPTION, ConfigHandler.eternityUpgradeRecipe);
        ItemUpgradeRegistry.registerItemPackUpgrade(eternityUpgrade);
        if (ConfigHandler.renamingUpgradeRequired){
            renamingUpgrade = new ItemPackUpgrade("renaming", ConfigHandler.renamingUpgradeCost, 0, IronBackpacksConstants.Upgrades.RENAMING_DESCRIPTION, ConfigHandler.renamingUpgradeRecipe);
            ItemUpgradeRegistry.registerItemPackUpgrade(renamingUpgrade);
        }

        //conflicting upgrades
        nestingUpgrade = new ItemConflictingUpgrade("nesting", ConfigHandler.nestingUpgradeCost, 0, IronBackpacksConstants.Upgrades.NESTING_DESCRIPTION, ConfigHandler.nestingUpgradeRecipe, Collections.singletonList(nestingAdvancedUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(nestingUpgrade);
        nestingAdvancedUpgrade = new ItemConflictingUpgrade("nestingAdvanced", ConfigHandler.nestingAdvancedUpgradeCost, 0, IronBackpacksConstants.Upgrades.NESTING_ADVANCED_DESRIPTION, ConfigHandler.nestingAdvancedUpgradeRecipe, Collections.singletonList(nestingUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(nestingAdvancedUpgrade);
        quickDepositUpgrade = new ItemConflictingUpgrade("quickDeposit", ConfigHandler.quickDepositUpgradeCost, 0, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_DESCRIPTION, ConfigHandler.quickDepositUpgradeRecipe, Collections.singletonList(quickDepositPreciseUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(quickDepositUpgrade);
        quickDepositPreciseUpgrade = new ItemConflictingUpgrade("quickDepositPrecise", ConfigHandler.quickDepositPreciseUpgradeCost, 0, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION, ConfigHandler.quickDepositPreciseUpgradeRecipe, Collections.singletonList(quickDepositUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(quickDepositPreciseUpgrade);

        //alt gui upgrades
        craftingUpgrade = new ItemAltGuiUpgrade("crafting", ConfigHandler.craftingUpgradeCost, 0, IronBackpacksConstants.Upgrades.CRAFTING_DESCRIPTION, ConfigHandler.craftingUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingUpgrade);
        craftingSmallUpgrade = new ItemAltGuiUpgrade("craftingSmall", ConfigHandler.craftingSmallUpgradeCost, 0, IronBackpacksConstants.Upgrades.CRAFTING_SMALL_DESCRIPTION, ConfigHandler.craftingSmallUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingSmallUpgrade);
        craftingTinyUpgrade = new ItemAltGuiUpgrade("craftingTiny", ConfigHandler.craftingTinyUpgradeCost, 0, IronBackpacksConstants.Upgrades.CRAFTING_TINY_DESCRIPTION, ConfigHandler.craftingTinyUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingTinyUpgrade);
        filterBasicUpgrade = new ItemAltGuiUpgrade("filterBasic", ConfigHandler.filterBasicUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_BASIC_DESCRIPTION, ConfigHandler.filterBasicUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterBasicUpgrade);
        filterFuzzyUpgrade = new ItemAltGuiUpgrade("filterFuzzy", ConfigHandler.filterFuzzyUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_FUZZY_DESCRIPTION, ConfigHandler.filterFuzzyUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterFuzzyUpgrade);
        filterOreDictUpgrade = new ItemAltGuiUpgrade("filterOreDict", ConfigHandler.filterOreDictUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_OREDICT_DESCRIPTION, ConfigHandler.filterOreDictUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterOreDictUpgrade);
        filterModSpecificUpgrade = new ItemAltGuiUpgrade("filterModSpecific", ConfigHandler.filterModSpecificUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION, ConfigHandler.filterModSpecificUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterModSpecificUpgrade);
        filterAdvancedUpgrade = new ItemAltGuiUpgrade("filterAdvanced", ConfigHandler.filterAdvancedUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_ADVANCED_DESCRIPTION, ConfigHandler.filterAdvancedUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterAdvancedUpgrade);
        filterMiningUpgrade = new ItemAltGuiUpgrade("filterMining", ConfigHandler.filterMiningUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_MINING_DESCRIPTION, ConfigHandler.filterMiningUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterMiningUpgrade);
        filterVoidUpgrade = new ItemAltGuiUpgrade("filterVoid", ConfigHandler.filterVoidUpgradeCost, 0, IronBackpacksConstants.Upgrades.FILTER_VOID_DESCRIPTION, ConfigHandler.filterVoidUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterVoidUpgrade);
        restockingUpgrade = new ItemAltGuiUpgrade("restocking", ConfigHandler.restockingUpgradeCost, 0, IronBackpacksConstants.Upgrades.RESTOCKING_DESCRIPTION, ConfigHandler.restockingUpgradeRecipe);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(restockingUpgrade);


        //Crafting Items (alphabetical order)
        craftingItem = (ItemCrafting) registerItem(new ItemCrafting(), IronBackpacksAPI.ITEM_CRAFTING_BASE);
        //sub items
        jeweledFeather = new ItemAPICrafting("jeweledFeather");
        ItemCraftingRegistry.registerItemCrafting(jeweledFeather);
        nest = new ItemAPICrafting("nest");
        ItemCraftingRegistry.registerItemCrafting(nest);
        treatedLeather = new ItemAPICrafting("treatedLeather");
        ItemCraftingRegistry.registerItemCrafting(treatedLeather);
        upgradeCore = new ItemAPICrafting("upgradeCore");
        ItemCraftingRegistry.registerItemCrafting(upgradeCore);

    }

    public static void registerItemRenders() {

        //init the render helper
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(Constants.DOMAIN);

        //render the backpack items (tiered orded)
        renderHelper.itemRender(basicBackpack, "ItemBackpackBasic");
        renderHelper.itemRender(ironBackpack, "ItemBackpackIron");
        renderHelper.itemRender(goldBackpack, "ItemBackpackGold");
        renderHelper.itemRender(diamondBackpack, "ItemBackpackDiamond");


        //render the upgrade items (alphabetical order, except adv. filter filters)
        renderHelper.itemRenderAll(upgradeItem);
        //normal upgrades
        ItemUpgradeRegistry.registerItemPackUpgradeTexture(additionalUpgradePointsUpgrade, "ItemUpgradeAdditionalUpgradePoints");
        ItemUpgradeRegistry.registerItemPackUpgradeTexture(buttonUpgrade, "ItemUpgradeButton");
        ItemUpgradeRegistry.registerItemPackUpgradeTexture(damageBarUpgrade, "ItemUpgradeDamageBar");
        ItemUpgradeRegistry.registerItemPackUpgradeTexture(depthUpgrade, "ItemUpgradeDepth");
        ItemUpgradeRegistry.registerItemPackUpgradeTexture(eternityUpgrade, "ItemUpgradeEternity");
        if (ConfigHandler.renamingUpgradeRequired)
            ItemUpgradeRegistry.registerItemPackUpgradeTexture(renamingUpgrade, "ItemUpgradeRenaming");
        //conflicting upgrades
        ItemUpgradeRegistry.registerItemConflictingUpgradeTexture(nestingUpgrade, "ItemUpgradeNesting");
        ItemUpgradeRegistry.registerItemConflictingUpgradeTexture(nestingAdvancedUpgrade, "ItemUpgradeNestingAdvanced");
        ItemUpgradeRegistry.registerItemConflictingUpgradeTexture(quickDepositUpgrade, "ItemUpgradeQuickDeposit");
        ItemUpgradeRegistry.registerItemConflictingUpgradeTexture(quickDepositPreciseUpgrade, "ItemUpgradeQuickDepositPrecise");
        //alt gui upgrades
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(craftingUpgrade, "ItemUpgradeCrafting");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(craftingSmallUpgrade, "ItemUpgradeCraftingSmall");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(craftingTinyUpgrade, "ItemUpgradeCraftingTiny");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterBasicUpgrade, "ItemUpgradeFilterBasic");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterFuzzyUpgrade, "ItemUpgradeFilterFuzzy");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterOreDictUpgrade, "ItemUpgradeFilterOreDict");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterModSpecificUpgrade, "ItemUpgradeFilterModSpecific");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterAdvancedUpgrade, "ItemUpgradeFilterAdvanced");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterMiningUpgrade, "ItemUpgradeFilterMining");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterVoidUpgrade, "ItemUpgradeFilterVoid");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(restockingUpgrade, "ItemUpgradeRestocking");


        //render the crafting items (alphabetical order)
        renderHelper.itemRenderAll(craftingItem);
        ItemCraftingRegistry.registerItemCraftingTexture(jeweledFeather, "ItemCraftingJeweledFeather");
        ItemCraftingRegistry.registerItemCraftingTexture(nest, "ItemCraftingNest");
        ItemCraftingRegistry.registerItemCraftingTexture(treatedLeather, "ItemCraftingTreatedLeather");
        ItemCraftingRegistry.registerItemCraftingTexture(upgradeCore, "ItemCraftingUpgradeCore");
    }


    //Helper methods for registering items

    /**
     * Helper method to register the items. Will only register if not on the config's blacklist.
     * @param item - the item to register
     * @param name - the name of the file to register the texture with (in assets/MOD_ID/models/item/FILE_HERE)
     * @return - the item
     */
    private static Item registerItem(Item item, String name) {
        if (!ConfigHandler.itemBlacklist.contains(name)) {
            if (item instanceof IBackpack){
                ItemBackpackRegistry.registerItemBackpack((IBackpack)item);
            }
            GameRegistry.registerItem(item, name);
        }

        return item;
    }

    private static void setTieringOfBackpacks(){
        basicBackpack.setBackpacksAbove(null, Collections.singletonList(ironBackpack)); //first parameter is (in my case, unused) itemstack, so I use null
        basicBackpack.setTier(null, 0);
        ironBackpack.setBackpacksAbove(null, Collections.singletonList(goldBackpack));
        ironBackpack.setTier(null, 1);
        goldBackpack.setBackpacksAbove(null, Collections.singletonList(diamondBackpack));
        goldBackpack.setTier(null, 2);
        diamondBackpack.setTier(null, 3);
    }
}
