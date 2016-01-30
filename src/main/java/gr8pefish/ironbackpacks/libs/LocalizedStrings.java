package gr8pefish.ironbackpacks.libs;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.TextUtils;

public class LocalizedStrings {

    public static final class Upgrades {

        //================Upgrade Tooltips===================
        public static final String[] BUTTON_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.button.desc"));
        public static final String[] NESTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.nesting.desc"));
        public static final String[] DAMAGE_BAR_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.bar.desc"));
        public static final String[] ETERNITY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.soulbound.desc"));
        public static final String[] QUICK_DEPOSIT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.quickDeposit.desc"));
        public static final String[] QUICK_DEPOSIT_PRECISE_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.quickDepositPrecise.desc"));
        public static final String[] NESTING_ADVANCED_DESRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advNesting.desc"));
        public static final String[] DEPTH_UPGRADE_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.depth.desc"));

        //additional points has numerically dependent formatting b/c english (i.e. singular vs plural -s)
        private static String formatting = ConfigHandler.additionalUpgradePointsIncrease == 1 ? TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.single") : TextUtils.localize("tooltip.ironbackpacks.upgrade.addUpgrades.amount.mult", ConfigHandler.additionalUpgradePointsIncrease);
        public static final String[] ADDITIONAL_UPGRADE_POINTS_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.addUpgrades.desc", formatting));

        //Alternate Gui
        public static final String[] RENAMING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.naming.desc"));
        public static final String[] FILTER_BASIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.basicFilter.desc"));
        public static final String[] FILTER_MOD_SPECIFIC_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.modSpecificFilter.desc"));
        public static final String[] FILTER_FUZZY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.fuzzyFilter.desc"));
        public static final String[] FILTER_OREDICT_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.oreDictFilter.desc"));
        public static final String[] FILTER_MINING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.miningFilter.desc"));
        public static final String[] FILTER_VOID_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.voidFilter.desc"));
        public static final String[] FILTER_ADVANCED_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.advFilter.desc"));
        public static final String[] RESTOCKING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.hopperFilter.desc"));
        public static final String[] CRAFTING_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.desc"));
        public static final String[] CRAFTING_SMALL_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.small.desc"));
        public static final String[] CRAFTING_TINY_DESCRIPTION = TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.condenser.tiny.desc"));
    }

}
