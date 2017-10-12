package gr8pefish.ironbackpacks.registry;

import java.util.ArrayList;
import java.util.Collections;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.items.craftingItems.ItemICrafting;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIConfigurableUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIConflictingUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.interfaces.IUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemIBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.craftingItems.ItemCrafting;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgrade;
import gr8pefish.ironbackpacks.libs.LocalizedStrings;
import gr8pefish.ironbackpacks.libs.recipes.BackpackTierRecipes;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.helpers.InventoryRenderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * Registers all the items in this mod.
 */
public class ItemRegistry {

    //backpacks
    public static ItemBackpack basicBackpack;
    public static ItemBackpack ironBackpackStorageEmphasis;
    public static ItemBackpack ironBackpackUpgradeEmphasis;
    public static ItemBackpack goldBackpackStorageEmphasis;
    public static ItemBackpack goldBackpackUpgradeEmphasis;
    public static ItemBackpack diamondBackpackStorageEmphasis;
    public static ItemBackpack diamondBackpackUpgradeEmphasis;

    //upgrades
    public static ItemUpgrade upgradeItem;
    //normal
    public static ItemIUpgrade additionalUpgradePointsUpgrade;
    public static ItemIUpgrade buttonUpgrade;
    public static ItemIUpgrade damageBarUpgrade;
    public static ItemIUpgrade depthUpgrade;
    public static ItemIUpgrade eternityUpgrade;
    public static ItemIUpgrade renamingUpgrade;
    //conflicting
    public static ItemIConflictingUpgrade nestingUpgrade;
    public static ItemIConflictingUpgrade nestingAdvancedUpgrade;
    public static ItemIConflictingUpgrade quickDepositUpgrade;
    public static ItemIConflictingUpgrade quickDepositPreciseUpgrade;
    //alt gui
    public static ItemIConfigurableUpgrade craftingUpgrade;
    public static ItemIConfigurableUpgrade craftingSmallUpgrade;
    public static ItemIConfigurableUpgrade craftingTinyUpgrade;
    public static ItemIConfigurableUpgrade filterBasicUpgrade;
    public static ItemIConfigurableUpgrade filterFuzzyUpgrade;
    public static ItemIConfigurableUpgrade filterOreDictUpgrade;
    public static ItemIConfigurableUpgrade filterModSpecificUpgrade;
    public static ItemIConfigurableUpgrade filterAdvancedUpgrade;
    public static ItemIConfigurableUpgrade filterMiningUpgrade;
    public static ItemIConfigurableUpgrade filterVoidUpgrade;
    public static ItemIConfigurableUpgrade restockingUpgrade;

    //misc
    public static ItemCrafting craftingItem;
    public static ItemICrafting upgradeCore;


    /**
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        //Backpack Items (Tiered order)
        basicBackpack = (ItemBackpack) //typecast the returned items
                registerItem(new ItemBackpack( //register the new ItemBackpack
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME, //name
                ConfigHandler.enumBasicBackpack.sizeX.getValue(), //rowLength
                ConfigHandler.enumBasicBackpack.sizeY.getValue(), //rowCount
                ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), //upgrade points
                ConfigHandler.enumBasicBackpack.additionalPoints.getValue(), //additional upgrade points; (next line) resource location of gui
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200: 236), //gui width
                (114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue())), //gui height
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackBasic.png"), //resource location of the texture of the model
                null), //unlocalized string of the emphasis of the backpack
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME); //registry name

        ironBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_EMPHASIS_STORAGE,
                ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackIron.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_EMPHASIS_STORAGE);

        ironBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_EMPHASIS_UPGRADES,
                ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackIron.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_EMPHASIS_UPGRADES);

        goldBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_EMPHASIS_STORAGE,
                ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackGold.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_EMPHASIS_STORAGE);

        goldBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_EMPHASIS_UPGRADES,
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackGold.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_EMPHASIS_UPGRADES);

        diamondBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_EMPHASIS_STORAGE,
                ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackDiamond.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_EMPHASIS_STORAGE);

        diamondBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_EMPHASIS_UPGRADES,
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackDiamond.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_EMPHASIS_UPGRADES);


        //Upgrade Items (alphabetical order, except the adv. filter filters)
        upgradeItem = (ItemUpgrade) registerItem(new ItemUpgrade(), IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME);
        //basic upgrades
        additionalUpgradePointsUpgrade = new ItemIUpgrade(
                "additionalUpgradePoints", //name
                0, //cost of upgrade points to apply the upgrade
                0, //tier of backpack necessary to apply the upgrade (TODO: special handling for this whole upgrade)
                ConfigHandler.additionalUpgradePointsRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.ADDITIONAL_UPGRADE_POINTS_DESCRIPTION); //description/tooltip
        ItemIUpgradeRegistry.registerItemPackUpgrade(additionalUpgradePointsUpgrade);
        buttonUpgrade = new ItemIUpgrade("button", ConfigHandler.buttonUpgradeCost, ConfigHandler.buttonUpgradeTier, ConfigHandler.buttonUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.BUTTON_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemPackUpgrade(buttonUpgrade);

        damageBarUpgrade = new ItemIUpgrade("damageBar", ConfigHandler.damageBarUpgradeCost, ConfigHandler.damageBarUpgradeTier, ConfigHandler.damageBarUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.DAMAGE_BAR_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemPackUpgrade(damageBarUpgrade);

        depthUpgrade = new ItemIUpgrade("depth", ConfigHandler.depthUpgradeCost, ConfigHandler.depthUpgradeTier, ConfigHandler.depthUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.DEPTH_UPGRADE_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemPackUpgrade(depthUpgrade);

        eternityUpgrade = new ItemIUpgrade("eternity", ConfigHandler.eternityUpgradeCost, ConfigHandler.eternityUpgradeTier, ConfigHandler.eternityUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.ETERNITY_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemPackUpgrade(eternityUpgrade);

        if (ConfigHandler.renamingUpgradeRequired){
            renamingUpgrade = new ItemIUpgrade("renaming", ConfigHandler.renamingUpgradeCost, ConfigHandler.renamingUpgradeTier, ConfigHandler.renamingUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.RENAMING_DESCRIPTION);
            ItemIUpgradeRegistry.registerItemPackUpgrade(renamingUpgrade);
        }

        //conflicting upgrades
        nestingUpgrade = new ItemIConflictingUpgrade("nesting", ConfigHandler.nestingUpgradeCost, ConfigHandler.nestingUpgradeTier, ConfigHandler.nestingUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.NESTING_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConflictingUpgrade(nestingUpgrade);

        nestingAdvancedUpgrade = new ItemIConflictingUpgrade("nestingAdvanced", ConfigHandler.nestingAdvancedUpgradeCost, ConfigHandler.nestingAdvancedUpgradeTier, ConfigHandler.nestingAdvancedUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.NESTING_ADVANCED_DESRIPTION);
        ItemIUpgradeRegistry.registerItemConflictingUpgrade(nestingAdvancedUpgrade);

        quickDepositUpgrade = new ItemIConflictingUpgrade("quickDeposit", ConfigHandler.quickDepositUpgradeCost, ConfigHandler.quickDepositUpgradeTier, ConfigHandler.quickDepositUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.QUICK_DEPOSIT_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConflictingUpgrade(quickDepositUpgrade);

        quickDepositPreciseUpgrade = new ItemIConflictingUpgrade("quickDepositPrecise", ConfigHandler.quickDepositPreciseUpgradeCost, ConfigHandler.quickDepositPreciseUpgradeTier, ConfigHandler.quickDepositPreciseUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConflictingUpgrade(quickDepositPreciseUpgrade);

        //have to set the conflicting upgrades after they are all initialized
        setConflictingUpgrades();

        //alt gui upgrades
        craftingUpgrade = new ItemIConfigurableUpgrade("crafting", ConfigHandler.craftingUpgradeCost, ConfigHandler.craftingUpgradeTier, ConfigHandler.craftingUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.CRAFTING_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(craftingUpgrade);

        craftingSmallUpgrade = new ItemIConfigurableUpgrade("craftingSmall", ConfigHandler.craftingSmallUpgradeCost, ConfigHandler.craftingSmallUpgradeTier, ConfigHandler.craftingSmallUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.CRAFTING_SMALL_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(craftingSmallUpgrade);

        craftingTinyUpgrade = new ItemIConfigurableUpgrade("craftingTiny", ConfigHandler.craftingTinyUpgradeCost, ConfigHandler.craftingTinyUpgradeTier, ConfigHandler.craftingTinyUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.CRAFTING_TINY_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(craftingTinyUpgrade);

        filterBasicUpgrade = new ItemIConfigurableUpgrade("filterBasic", ConfigHandler.filterBasicUpgradeCost, ConfigHandler.filterBasicUpgradeTier, ConfigHandler.filterBasicUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_BASIC_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterBasicUpgrade);

        filterFuzzyUpgrade = new ItemIConfigurableUpgrade("filterFuzzy", ConfigHandler.filterFuzzyUpgradeCost, ConfigHandler.filterFuzzyUpgradeTier, ConfigHandler.filterFuzzyUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_FUZZY_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterFuzzyUpgrade);

        filterOreDictUpgrade = new ItemIConfigurableUpgrade("filterOreDict", ConfigHandler.filterOreDictUpgradeCost, ConfigHandler.filterOreDictUpgradeTier, ConfigHandler.filterOreDictUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_OREDICT_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterOreDictUpgrade);

        filterModSpecificUpgrade = new ItemIConfigurableUpgrade("filterModSpecific", ConfigHandler.filterModSpecificUpgradeCost, ConfigHandler.filterModSpecificUpgradeTier, ConfigHandler.filterModSpecificUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterModSpecificUpgrade);

        filterVoidUpgrade = new ItemIConfigurableUpgrade("filterVoid", ConfigHandler.filterVoidUpgradeCost, ConfigHandler.filterVoidUpgradeTier, ConfigHandler.filterVoidUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_VOID_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterVoidUpgrade);

        filterAdvancedUpgrade = new ItemIConfigurableUpgrade("filterAdvanced", ConfigHandler.filterAdvancedUpgradeCost, ConfigHandler.filterAdvancedUpgradeTier, ConfigHandler.filterAdvancedUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_ADVANCED_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterAdvancedUpgrade);

        filterMiningUpgrade = new ItemIConfigurableUpgrade("filterMining", ConfigHandler.filterMiningUpgradeCost, ConfigHandler.filterMiningUpgradeTier, ConfigHandler.filterMiningUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.FILTER_MINING_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(filterMiningUpgrade);

        restockingUpgrade = new ItemIConfigurableUpgrade("restocking", ConfigHandler.restockingUpgradeCost, ConfigHandler.restockingUpgradeTier, ConfigHandler.restockingUpgradeRecipeDisabled ? LocalizedStrings.Upgrades.UPGRADE_RECIPE_DISABLED : LocalizedStrings.Upgrades.RESTOCKING_DESCRIPTION);
        ItemIUpgradeRegistry.registerItemConfigurableUpgrade(restockingUpgrade);


        //Crafting Items (alphabetical order)
        craftingItem = (ItemCrafting) registerItem(new ItemCrafting(), IronBackpacksAPI.ITEM_CRAFTING_BASE_NAME);
        //sub items
        upgradeCore = new ItemICrafting("upgradeCore");
        ItemICraftingRegistry.registerItemCrafting(upgradeCore);


        //Sets the tiers and links between all the backpacks.
        //Has to be called after the items initialization because it uses the backpack items themselves.
        setTieringAndTierRecipesOfBackpacks(); //TODO: DON'T FORGET TO UPDATE THIS WHEN ADDING BACKPACKS
        RecipeRegistry.setAllNonTierRecipes(); //Update all the other recipes too!

    }

    public static void registerItemRenders() {

        //init the render helper
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(Constants.DOMAIN);

        //render the backpack items (tiered ordered)
        renderHelper.itemRender(basicBackpack, "ItemBackpackBasic");
        renderHelper.itemRender(ironBackpackStorageEmphasis, "ItemBackpackIronStorage");
        renderHelper.itemRender(ironBackpackUpgradeEmphasis, "ItemBackpackIronUpgrade");
        renderHelper.itemRender(goldBackpackStorageEmphasis, "ItemBackpackGoldStorage");
        renderHelper.itemRender(goldBackpackUpgradeEmphasis, "ItemBackpackGoldUpgrade");
        renderHelper.itemRender(diamondBackpackStorageEmphasis, "ItemBackpackDiamondStorage");
        renderHelper.itemRender(diamondBackpackUpgradeEmphasis, "ItemBackpackDiamondUpgrade");


        //render the upgrade items (alphabetical order, except adv. filter filters)
        renderHelper.itemRenderAll(upgradeItem);
        //normal upgrades
        ItemIUpgradeRegistry.registerItemIUpgradeTexture(additionalUpgradePointsUpgrade, "ItemUpgradeAdditionalUpgradePoints");
        ItemIUpgradeRegistry.registerItemIUpgradeTexture(buttonUpgrade, "ItemUpgradeButton");
        ItemIUpgradeRegistry.registerItemIUpgradeTexture(damageBarUpgrade, "ItemUpgradeDamageBar");
        ItemIUpgradeRegistry.registerItemIUpgradeTexture(depthUpgrade, "ItemUpgradeDepth");
        ItemIUpgradeRegistry.registerItemIUpgradeTexture(eternityUpgrade, "ItemUpgradeEternity");
        if (ConfigHandler.renamingUpgradeRequired)
            ItemIUpgradeRegistry.registerItemIUpgradeTexture(renamingUpgrade, "ItemUpgradeRenaming");
        //conflicting upgrades
        ItemIUpgradeRegistry.registerItemIConflictingUpgradeTexture(nestingUpgrade, "ItemUpgradeNesting");
        ItemIUpgradeRegistry.registerItemIConflictingUpgradeTexture(nestingAdvancedUpgrade, "ItemUpgradeNestingAdvanced");
        ItemIUpgradeRegistry.registerItemIConflictingUpgradeTexture(quickDepositUpgrade, "ItemUpgradeQuickDeposit");
        ItemIUpgradeRegistry.registerItemIConflictingUpgradeTexture(quickDepositPreciseUpgrade, "ItemUpgradeQuickDepositPrecise");
        //alt gui upgrades
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(craftingUpgrade, "ItemUpgradeCrafting");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(craftingSmallUpgrade, "ItemUpgradeCraftingSmall");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(craftingTinyUpgrade, "ItemUpgradeCraftingTiny");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterBasicUpgrade, "ItemUpgradeFilterBasic");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterFuzzyUpgrade, "ItemUpgradeFilterFuzzy");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterOreDictUpgrade, "ItemUpgradeFilterOreDict");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterModSpecificUpgrade, "ItemUpgradeFilterModSpecific");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterVoidUpgrade, "ItemUpgradeFilterVoid");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterAdvancedUpgrade, "ItemUpgradeFilterAdvanced");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(filterMiningUpgrade, "ItemUpgradeFilterMining");
        ItemIUpgradeRegistry.registerItemIConfigurableUpgradeTexture(restockingUpgrade, "ItemUpgradeRestocking");


        //render the recipes items (alphabetical order)
        renderHelper.itemRenderAll(craftingItem);
        ItemICraftingRegistry.registerItemCraftingTexture(upgradeCore, "ItemCraftingUpgradeCore");
    }


    //Helper methods for registering items

    /**
     * Helper method to register the items. Will only register if not on the config's blacklist.
     * @param item - the items to register
     * @param name - the name of the file to register the texture with (in assets/MOD_ID/models/items/FILE_HERE)
     * @return - the items
     */
    private static Item registerItem(Item item, String name) {
        if (item instanceof IBackpack){
            ItemIBackpackRegistry.registerItemBackpack((IBackpack)item);
        }
        item.setRegistryName(name);
        RegistryEvents.ITEMS.add(item);

        return item;
    }

    private static void setTieringAndTierRecipesOfBackpacks(){
        ArrayList<ITieredBackpack> basicBackpacksAbove = new ArrayList<>();
        basicBackpacksAbove.add(ironBackpackStorageEmphasis);
        basicBackpacksAbove.add(ironBackpackUpgradeEmphasis);
        basicBackpack.setBackpacksAbove(null, basicBackpacksAbove); //first parameter is (in my case, unused) itemstack, so I use null
        basicBackpack.setTier(null, 0);
        basicBackpack.setTierRecipes(null, BackpackTierRecipes.getBasicBackpackTierRecipes());

        ironBackpackStorageEmphasis.setBackpacksAbove(null, Collections.singletonList(goldBackpackStorageEmphasis));
        ironBackpackStorageEmphasis.setTier(null, 1);
        ironBackpackStorageEmphasis.setTierRecipes(null, BackpackTierRecipes.getIronBackpackStorageEmphasisTierRecipes());

        ironBackpackUpgradeEmphasis.setBackpacksAbove(null, Collections.singletonList(goldBackpackUpgradeEmphasis));
        ironBackpackUpgradeEmphasis.setTier(null, 1);
        ironBackpackUpgradeEmphasis.setTierRecipes(null, BackpackTierRecipes.getIronBackpackUpgradeEmphasisTierRecipes());

        goldBackpackStorageEmphasis.setBackpacksAbove(null, Collections.singletonList(diamondBackpackStorageEmphasis));
        goldBackpackStorageEmphasis.setTier(null, 2);
        goldBackpackStorageEmphasis.setTierRecipes(null, BackpackTierRecipes.getGoldBackpackStorageEmphasisTierRecipes());

        goldBackpackUpgradeEmphasis.setBackpacksAbove(null, Collections.singletonList(diamondBackpackUpgradeEmphasis));
        goldBackpackUpgradeEmphasis.setTier(null, 2);
        goldBackpackUpgradeEmphasis.setTierRecipes(null, BackpackTierRecipes.getGoldBackpackUpgradeEmphasisTierRecipes());

        diamondBackpackStorageEmphasis.setTier(null, 3);

        diamondBackpackUpgradeEmphasis.setTier(null, 3);
    }

    private static void setConflictingUpgrades(){
        nestingUpgrade.setConflictingUpgrades(null, Collections.singletonList(nestingAdvancedUpgrade));
        nestingAdvancedUpgrade.setConflictingUpgrades(null, Collections.singletonList(nestingUpgrade));
        quickDepositUpgrade.setConflictingUpgrades(null, Collections.singletonList(quickDepositPreciseUpgrade));
        quickDepositPreciseUpgrade.setConflictingUpgrades(null, Collections.singletonList(quickDepositUpgrade));
    }

    public static ArrayList<IUpgrade> getAllUpgrades() {
        ArrayList<IUpgrade> upgrades = new ArrayList<>();
        //normal
        upgrades.add(additionalUpgradePointsUpgrade);
        upgrades.add(buttonUpgrade);
        upgrades.add(damageBarUpgrade);
        upgrades.add(depthUpgrade);
        upgrades.add(eternityUpgrade);
        upgrades.add(renamingUpgrade);
        //conflicting
        upgrades.add(nestingUpgrade);
        upgrades.add(nestingAdvancedUpgrade);
        upgrades.add(quickDepositUpgrade);
        upgrades.add(quickDepositPreciseUpgrade);
        //alt gui
        upgrades.add(craftingUpgrade);
        upgrades.add(craftingSmallUpgrade);
        upgrades.add(craftingTinyUpgrade);
        upgrades.add(filterBasicUpgrade);
        upgrades.add(filterFuzzyUpgrade);
        upgrades.add(filterOreDictUpgrade);
        upgrades.add(filterModSpecificUpgrade);
        upgrades.add(filterAdvancedUpgrade);
        upgrades.add(filterMiningUpgrade);
        upgrades.add(filterVoidUpgrade);
        upgrades.add(restockingUpgrade);
        return upgrades;
    }

}
