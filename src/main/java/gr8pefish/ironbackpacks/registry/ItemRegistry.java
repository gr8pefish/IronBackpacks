package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.ITieredBackpack;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemAltGuiUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemConflictingUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemBackpackRegistry;
import gr8pefish.ironbackpacks.api.register.ItemCraftingRegistry;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.craftingItems.ItemCrafting;
import gr8pefish.ironbackpacks.items.upgrades.ItemUpgrade;
import gr8pefish.ironbackpacks.libs.recipes.BackpackTierRecipes;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.helpers.InventoryRenderHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;

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
     * Registers all the items with the GameRegistry
     */
    public static void registerItems() {

        //Backpack Items (Tiered order)
        basicBackpack = (ItemBackpack) //typecast the returned item
                registerItem(new ItemBackpack( //register the new ItemBackpack
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME, //name
                ConfigHandler.enumBasicBackpack.sizeX.getValue(), //rowLength
                ConfigHandler.enumBasicBackpack.sizeY.getValue(), //rowCount
                ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), //upgrade points
                0, //additional upgrade points (hardcoded to 0 for the basic backpack), (next line) resource location of gui
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue())+".png"),
                (ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200: 236), //gui width
                (114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue())), //gui height
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackBasic.png"), //resource location of the texture of the model
                null), //unlocalized string of the emphasis of the backpack
                IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME); //registry name

        ironBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_STORAGE,
                ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumIronBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumIronBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumIronBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackIron.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_STORAGE);

        ironBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_POINTS,
                ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumIronBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumIronBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackIron.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME_POINTS);

        goldBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_STORAGE,
                ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumGoldBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumGoldBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumGoldBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackGold.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_STORAGE);

        goldBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_POINTS,
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumGoldBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumGoldBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackGold.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME_POINTS);

        diamondBackpackStorageEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_STORAGE,
                ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumDiamondBackpackStorageEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumDiamondBackpackStorageEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackDiamond.png"),
                "tooltip.ironbackpacks.backpack.emphasis.storage"),
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_STORAGE);

        diamondBackpackUpgradeEmphasis = (ItemBackpack) registerItem(new ItemBackpack(
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_POINTS,
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.upgradePoints.getValue(),
                ConfigHandler.enumDiamondBackpackUpgradeEmphasis.additionalPoints.getValue(),
                new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue())+".png"),
                (ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeX.getValue() == 9 ? 200: 236),
                (114 + (18 * ConfigHandler.enumDiamondBackpackUpgradeEmphasis.sizeY.getValue())),
                new ResourceLocation(Constants.MODID, "textures/models/ModelBackpackDiamond.png"),
                "tooltip.ironbackpacks.backpack.emphasis.upgrade"),
                IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME_POINTS);


        //Upgrade Items (alphabetical order, except the adv. filter filters)
        upgradeItem = (ItemUpgrade) registerItem(new ItemUpgrade(), IronBackpacksAPI.ITEM_UPGRADE_BASE);
        //basic upgrades
        additionalUpgradePointsUpgrade = new ItemPackUpgrade(
                "additionalUpgradePoints", //name
                0, //cost of upgrade points to apply the upgrade
                0, //tier of backpack necessary to apply the upgrade (TODO: special handling)
                IronBackpacksConstants.Upgrades.ADDITIONAL_UPGRADE_POINTS_DESCRIPTION); //description/tooltip
        ItemUpgradeRegistry.registerItemPackUpgrade(additionalUpgradePointsUpgrade);
        buttonUpgrade = new ItemPackUpgrade("button", ConfigHandler.buttonUpgradeCost, ConfigHandler.buttonUpgradeTier, IronBackpacksConstants.Upgrades.BUTTON_DESCRIPTION);
        ItemUpgradeRegistry.registerItemPackUpgrade(buttonUpgrade);
        damageBarUpgrade = new ItemPackUpgrade("damageBar", ConfigHandler.damageBarUpgradeCost, ConfigHandler.damageBarUpgradeTier, IronBackpacksConstants.Upgrades.DAMAGE_BAR_DESCRIPTION);
        ItemUpgradeRegistry.registerItemPackUpgrade(damageBarUpgrade);
        depthUpgrade = new ItemPackUpgrade("depth", ConfigHandler.depthUpgradeCost, ConfigHandler.depthUpgradeTier, IronBackpacksConstants.Upgrades.DEPTH_UPGRADE_DESCRIPTION);
        ItemUpgradeRegistry.registerItemPackUpgrade(depthUpgrade);
        eternityUpgrade = new ItemPackUpgrade("eternity", ConfigHandler.eternityUpgradeCost, ConfigHandler.eternityUpgradeTier, IronBackpacksConstants.Upgrades.ETERNITY_DESCRIPTION);
        ItemUpgradeRegistry.registerItemPackUpgrade(eternityUpgrade);
        if (ConfigHandler.renamingUpgradeRequired){
            renamingUpgrade = new ItemPackUpgrade("renaming", ConfigHandler.renamingUpgradeCost, ConfigHandler.renamingUpgradeTier, IronBackpacksConstants.Upgrades.RENAMING_DESCRIPTION);
            ItemUpgradeRegistry.registerItemPackUpgrade(renamingUpgrade);
        }

        //conflicting upgrades
        nestingUpgrade = new ItemConflictingUpgrade("nesting", ConfigHandler.nestingUpgradeCost, ConfigHandler.nestingUpgradeTier, IronBackpacksConstants.Upgrades.NESTING_DESCRIPTION, Collections.singletonList(nestingAdvancedUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(nestingUpgrade);
        nestingAdvancedUpgrade = new ItemConflictingUpgrade("nestingAdvanced", ConfigHandler.nestingAdvancedUpgradeCost, ConfigHandler.nestingAdvancedUpgradeTier, IronBackpacksConstants.Upgrades.NESTING_ADVANCED_DESRIPTION, Collections.singletonList(nestingUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(nestingAdvancedUpgrade);
        quickDepositUpgrade = new ItemConflictingUpgrade("quickDeposit", ConfigHandler.quickDepositUpgradeCost, ConfigHandler.quickDepositUpgradeTier, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_DESCRIPTION, Collections.singletonList(quickDepositPreciseUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(quickDepositUpgrade);
        quickDepositPreciseUpgrade = new ItemConflictingUpgrade("quickDepositPrecise", ConfigHandler.quickDepositPreciseUpgradeCost, ConfigHandler.quickDepositPreciseUpgradeTier, IronBackpacksConstants.Upgrades.QUICK_DEPOSIT_PRECISE_DESCRIPTION, Collections.singletonList(quickDepositUpgrade));
        ItemUpgradeRegistry.registerItemConflictingUpgrade(quickDepositPreciseUpgrade);

        //alt gui upgrades
        craftingUpgrade = new ItemAltGuiUpgrade("crafting", ConfigHandler.craftingUpgradeCost, ConfigHandler.craftingUpgradeTier, IronBackpacksConstants.Upgrades.CRAFTING_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingUpgrade);
        craftingSmallUpgrade = new ItemAltGuiUpgrade("craftingSmall", ConfigHandler.craftingSmallUpgradeCost, ConfigHandler.craftingSmallUpgradeTier, IronBackpacksConstants.Upgrades.CRAFTING_SMALL_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingSmallUpgrade);
        craftingTinyUpgrade = new ItemAltGuiUpgrade("craftingTiny", ConfigHandler.craftingTinyUpgradeCost, ConfigHandler.craftingTinyUpgradeTier, IronBackpacksConstants.Upgrades.CRAFTING_TINY_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(craftingTinyUpgrade);
        filterBasicUpgrade = new ItemAltGuiUpgrade("filterBasic", ConfigHandler.filterBasicUpgradeCost, ConfigHandler.filterBasicUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_BASIC_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterBasicUpgrade);
        filterFuzzyUpgrade = new ItemAltGuiUpgrade("filterFuzzy", ConfigHandler.filterFuzzyUpgradeCost, ConfigHandler.filterFuzzyUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_FUZZY_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterFuzzyUpgrade);
        filterOreDictUpgrade = new ItemAltGuiUpgrade("filterOreDict", ConfigHandler.filterOreDictUpgradeCost, ConfigHandler.filterOreDictUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_OREDICT_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterOreDictUpgrade);
        filterModSpecificUpgrade = new ItemAltGuiUpgrade("filterModSpecific", ConfigHandler.filterModSpecificUpgradeCost, ConfigHandler.filterModSpecificUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_MOD_SPECIFIC_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterModSpecificUpgrade);
        filterVoidUpgrade = new ItemAltGuiUpgrade("filterVoid", ConfigHandler.filterVoidUpgradeCost, ConfigHandler.filterVoidUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_VOID_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterVoidUpgrade);
        filterAdvancedUpgrade = new ItemAltGuiUpgrade("filterAdvanced", ConfigHandler.filterAdvancedUpgradeCost, ConfigHandler.filterAdvancedUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_ADVANCED_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterAdvancedUpgrade);
        filterMiningUpgrade = new ItemAltGuiUpgrade("filterMining", ConfigHandler.filterMiningUpgradeCost, ConfigHandler.filterMiningUpgradeTier, IronBackpacksConstants.Upgrades.FILTER_MINING_DESCRIPTION);
        ItemUpgradeRegistry.registerItemAltGuiUpgrade(filterMiningUpgrade);
        restockingUpgrade = new ItemAltGuiUpgrade("restocking", ConfigHandler.restockingUpgradeCost, ConfigHandler.restockingUpgradeTier, IronBackpacksConstants.Upgrades.RESTOCKING_DESCRIPTION);
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



        //Sets the tiers and links between all the backpacks.
        //Has to be called after the item initialization because it uses the backpack items themselves.
        setTieringAndTierRecipesOfBackpacks(); //TODO(So it's yellow): DON'T FORGET TO UPDATE THIS WHEN ADDING BACKPACKS
        RecipeRegistry.setAllNonTierRecipes(); //Update all the other recipes too!

    }

    public static void registerItemRenders() {

        //init the render helper
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(Constants.DOMAIN);

        //render the backpack items (tiered orderd)
        renderHelper.itemRender(basicBackpack, "ItemBackpackBasic");
        renderHelper.itemRender(ironBackpackStorageEmphasis, "ItemBackpackIron");
        renderHelper.itemRender(ironBackpackUpgradeEmphasis, "ItemBackpackIron");
        renderHelper.itemRender(goldBackpackStorageEmphasis, "ItemBackpackGold");
        renderHelper.itemRender(goldBackpackUpgradeEmphasis, "ItemBackpackGold");
        renderHelper.itemRender(diamondBackpackStorageEmphasis, "ItemBackpackDiamond");
        renderHelper.itemRender(diamondBackpackUpgradeEmphasis, "ItemBackpackDiamond");


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
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterVoidUpgrade, "ItemUpgradeFilterVoid");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterAdvancedUpgrade, "ItemUpgradeFilterAdvanced");
        ItemUpgradeRegistry.registerItemAltGuiUpgradeTexture(filterMiningUpgrade, "ItemUpgradeFilterMining");
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

    private static void setTieringAndTierRecipesOfBackpacks(){
        ArrayList basicBackpacksAbove = new ArrayList<ITieredBackpack>();
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

}
